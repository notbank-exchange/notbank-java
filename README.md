# Notbank Java SDK

[main page](https://notbank.exchange)

[sign up in Notbank](https://www.cryptomkt.com/account/register).

## Installation

Add the Maven dependency

```xml
<dependency>
    <groupId>exchange.notbank.api</groupId>
    <artifactId>notbank</artifactId>
    <version>X.X.X</version>
</dependency>
```

## Documentation

This sdk makes use of the [api](https://apidoc.notbank.exchange) of Notbank.

All sdk service methods return a completable future, so remember to handle exceptions.
Authentication and log out from the service factory also return completable futures

## Client creation

```java
// create a rest client
var client = NotbankClient.Factory.createclient();
```

## Services

The notbank client is separated by services, reflecting the separation of endpoints in the documentation

```java
// ...
var tradingService = client.getTradingService();

var feeService = client.getFeeService();
// ...
```

## Put order at the top of book example

```java
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import exchange.notbank.account.paramBuilders.GetAccountPositionsParamBuilder;
import exchange.notbank.trading.constants.OrderSide;
import exchange.notbank.trading.constants.OrderType;
import exchange.notbank.trading.constants.SendOrderResponseStatus;
import exchange.notbank.trading.constants.TimeInForce;
import exchange.notbank.trading.paramBuilders.CancelOrderParamBuilder;
import exchange.notbank.trading.paramBuilders.GetOrderBookParamBuilder;
import exchange.notbank.trading.paramBuilders.SendOrderParamBuilder;

// ...
public class ExampleClass {
  void exampleMethod() {
    try {
      var accountId = 123;

      // instantiate a client
      var client = NotbankClient.Factory.createRestClient();

      // authentication
      var userId = 112233
      client.authenticate(
          userId,
          "apiPublicKey",
          "apiSecretKey");

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
```

## Using Either

There is a class, CompletableFutureAdapter, that waits for the future completion as a Right, and in case of exception returns an exception class NotbankException as Left.

```java
var productId = 23;
var transferService = serviceFactory.newTransferService();
// make the request
var completableFutureResponse = transferService.requestTransfer(
    new RequestTransferParamBuilder(productId, "david.bond@email.com", new BigDecimal("13")));
// use the adapter to wait for the response and convert the result to an either
var responseAsEither = CompletableFutureAdapter.getToEither(completableFutureResponse);
if (responseAsEither.isLeft()) {
    // handle exception
    var exception = responseAsEither.getLeft();
    System.out.println(exception);
} else {
    // handle the result
    var transferRequestResponse = responseAsEither.get();
    System.out.println(transferRequestResponse.result);
}

```
