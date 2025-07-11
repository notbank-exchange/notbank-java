package exchange.notbank;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;

import exchange.notbank.account.paramBuilders.GetAccountPositionsParamBuilder;
import exchange.notbank.trading.constants.OrderSide;
import exchange.notbank.trading.constants.OrderType;
import exchange.notbank.trading.constants.SendOrderResponseStatus;
import exchange.notbank.trading.constants.TimeInForce;
import exchange.notbank.trading.paramBuilders.CancelOrderParamBuilder;
import exchange.notbank.trading.paramBuilders.GetOrderBookParamBuilder;
import exchange.notbank.trading.paramBuilders.SendOrderParamBuilder;

public class ExampleTest {
  @Test
  public void runExample() {
    putOrderAtTopOfOrderbook();
  }

  Optional<Long> putOrderAtTopOfOrderbook() {
    try {
      var credentials = TestHelper.getUserCredentials();
      var accountId = credentials.accountId;

      // instantiate a client
      var client = NotbankClient.Factory.createRestClient(TestHelper.HOST);

      // authentication
      client.authenticate(
          credentials.userId,
          credentials.apiPublicKey,
          credentials.apiSecretKey).get();

      // get USDT user balance (also known as position)
      var positions = client.getAccountService().getAccountPositions(new GetAccountPositionsParamBuilder(accountId))
          .get();
      var productSymbol = "USDT";
      var usdtPosition = positions.stream()
          .filter(position -> position.productSymbol.equals(productSymbol))
          .findAny();
      if (usdtPosition.isEmpty()) {
        return Optional.empty();
      }

      // define quantityToSpend (between all usdt_balance and half usdt_balance)
      var myUsdtBalance = usdtPosition.get().amount;
      var randomFraction = new BigDecimal(new Random().nextDouble());
      var halfMyBalance = myUsdtBalance.divide(new BigDecimal("2"));
      var atLeastHalfMyBalance = myUsdtBalance.subtract(randomFraction.multiply(halfMyBalance));
      var quantityToSpend = atLeastHalfMyBalance;

      var marketPair = "BTCUSDT";

      // define orderPrice (around market top)
      var btcUsdtOrderbook = client.getTradingService()
          .getOrderbook(new GetOrderBookParamBuilder(marketPair)
              .depth(5)
              .level(2))
          .get();
      var randomSmallFraction = new BigDecimal(new Random().nextDouble())
          .multiply(new BigDecimal("90"))
          .add(BigDecimal.TEN)
          .divide(new BigDecimal("1000"));
      var topBid = btcUsdtOrderbook.bids.get(0);
      var orderPrice = topBid.price.add(randomSmallFraction);
      var orderQuantity = quantityToSpend.divide(orderPrice, RoundingMode.DOWN);

      // send order
      var btcUsdtInstrument = client.getInstrumentService().getInstrument(marketPair).get();
      var orderParamBuilder = new SendOrderParamBuilder(
          btcUsdtInstrument,
          accountId,
          TimeInForce.GTC,
          OrderSide.BUY,
          OrderType.LIMIT,
          orderQuantity)
          .limitPrice(orderPrice);
      var orderResult = client.getTradingService().sendOrder(orderParamBuilder).get();

      // handle order result
      if (orderResult.status.equals(SendOrderResponseStatus.REJECTED)) {
        // order was rejected
        System.out.println("order rejected");
        System.out.println(orderResult.errorMessage);
        return Optional.empty();
      }
      // order was accepted
      System.out.println(orderResult.orderId);
      // cancel order
      client.getTradingService()
          .cancelOrder(new CancelOrderParamBuilder()
              .accountId(accountId)
              .orderId(orderResult.orderId))
          .get();
      return Optional.of(orderResult.orderId);

    } catch (InterruptedException | ExecutionException e) {
      // handle exceptions
      System.out.println("unable to make order");
      e.printStackTrace();
      return Optional.empty();
    }

  }
}
