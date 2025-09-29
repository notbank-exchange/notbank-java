package exchange.notbank.trading;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import exchange.notbank.CredentialsLoader.UserCredentials;
import exchange.notbank.TestHelper;
import exchange.notbank.instrument.responses.Instrument;
import exchange.notbank.trading.constants.OrderSide;
import exchange.notbank.trading.constants.OrderType;
import exchange.notbank.trading.constants.SendOrderResponseStatus;
import exchange.notbank.trading.constants.TimeInForce;
import exchange.notbank.trading.paramBuilders.CancelAllOrdersParamBuilder;
import exchange.notbank.trading.paramBuilders.CancelReplaceOrderParamBuilder;
import exchange.notbank.trading.paramBuilders.GetLastTradesParamBuilder;
import exchange.notbank.trading.paramBuilders.GetLevel1ParamBuilder;
import exchange.notbank.trading.paramBuilders.GetLevel1SummaryParamBuilder;
import exchange.notbank.trading.paramBuilders.GetLevel2SnapshotParamBuilder;
import exchange.notbank.trading.paramBuilders.GetLevelSummaryParamBuilder;
import exchange.notbank.trading.paramBuilders.GetOpenOrdersParamBuilder;
import exchange.notbank.trading.paramBuilders.GetOpenTradeReportParamBuilder;
import exchange.notbank.trading.paramBuilders.GetOrderStatusParamBuilder;
import exchange.notbank.trading.paramBuilders.GetOrdersParamBuilder;
import exchange.notbank.trading.paramBuilders.GetTickerHistoryParamBuilder;
import exchange.notbank.trading.paramBuilders.GetTradesHistoryParamBuilder;
import exchange.notbank.trading.paramBuilders.GetUserAccountsParamBuilder;
import exchange.notbank.trading.paramBuilders.ModifyOrderParamBuilder;
import exchange.notbank.trading.paramBuilders.SendCancelListParamBuilder;
import exchange.notbank.trading.paramBuilders.SendCancelParamBuilder;
import exchange.notbank.trading.paramBuilders.SendCancelReplaceListParamBuilder;
import exchange.notbank.trading.paramBuilders.SendCancelReplaceParamBuilder;
import exchange.notbank.trading.paramBuilders.SendOrderListParamBuilder;
import exchange.notbank.trading.paramBuilders.SendOrderParamBuilder;
import exchange.notbank.trading.paramBuilders.TradesParamBuilder;

public class TradingServiceTest {
  private static TradingService service;
  private static Integer accountId;
  private static UserCredentials credentials;
  private static Instrument anInstrument;

  @BeforeAll
  public static void beforeAll() throws InterruptedException, ExecutionException {
    var notbankClient = TestHelper.newRestClient();
    service = notbankClient.getTradingService();
    credentials = TestHelper.getUserCredentials();
    accountId = credentials.accountId;
    anInstrument = notbankClient.getInstrumentService().getInstrument("BTCUSDT").get();
  }

  @Test
  public void getTradesHistory() {
    var futureResponse = service.getTradesHistory(new GetTradesHistoryParamBuilder());
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getOpenOrders() {
    var futureResponse = service.getOpenOrders(new GetOpenOrdersParamBuilder(accountId));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getOrders() {
    var futureResponse = service.getOrders(new GetOrdersParamBuilder(accountId));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getOpenTradeReports() {
    var futureResponse = service.getOpenTradeReports(new GetOpenTradeReportParamBuilder(accountId));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getLevel2Snapshot() {
    var params = new GetLevel2SnapshotParamBuilder(153, 2);
    var futureResponse = service.getLevel2Snapshot(params);
    TestHelper.checkNoError(futureResponse);
  }

  public void getLevel1SummaryMin() {
    var instruments = Arrays.asList("1", "2", "3");
    var futureResponse = service
        .getLevel1SummaryMin(new GetLevelSummaryParamBuilder().instrumentIds(instruments));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getUserAccounts() {
    var params = new GetUserAccountsParamBuilder(43);
    var futureResponse = service.getUserAccounts(params);
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getLevel1Summary() {
    var params = new GetLevel1SummaryParamBuilder();
    var futureResponse = service.getLevel1Summary(params);
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void trades() {
    var params = new TradesParamBuilder("BTCUSDT");
    var futureResponse = service.trades(params);
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void summary() throws InterruptedException, ExecutionException {
    var futureResponse = service.summary();
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getTickerHistory() {
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    var params = new GetTickerHistoryParamBuilder(154, 60,
        LocalDateTime.now().format(dateFormat), LocalDateTime.now().plus(1, ChronoUnit.DAYS).format(dateFormat));
    var futureResponse = service.getTickerHistory(params);
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getLastTrades() {
    var params = new GetLastTradesParamBuilder(75).count(500);
    var futureResponse = service.getLastTrades(params);
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getTicker() {
    var futureResponse = service.getTicker();
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getLevel1() {
    var params = new GetLevel1ParamBuilder(154);
    var futureResponse = service.getLevel1(params);
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void sendOrderIsRejectedIfNotAuthorized() throws InterruptedException {
    var responseFuture = service.sendOrder(
        new SendOrderParamBuilder(anInstrument, 1, TimeInForce.GTC, OrderSide.BUY, OrderType.MARKET,
            new BigDecimal("0.001")));
    assertThrows(ExecutionException.class, () -> responseFuture.get());
  }

  @Test
  public void sendOrderIsRejectedIfNoFunds() throws InterruptedException, ExecutionException {
    var responseFuture = service.sendOrder(
        new SendOrderParamBuilder(anInstrument, credentials.accountId, TimeInForce.FOK, OrderSide.BUY,
            OrderType.MARKET,
            new BigDecimal("10")));
    var response = responseFuture.get();
    assertEquals(response.status, SendOrderResponseStatus.REJECTED);
    assertEquals("Not_Enough_Funds", response.errorMessage);
  }

  @Test
  public void sendOrderWithPostOnly() throws InterruptedException, ExecutionException {
    var sentOrderResponse = service.sendOrder(new SendOrderParamBuilder(
        anInstrument,
        credentials.accountId,
        TimeInForce.GTC,
        OrderSide.BUY,
        OrderType.LIMIT,
        new BigDecimal("1"))
        .limitPrice(new BigDecimal("0.1"))).get();
    System.out.println(sentOrderResponse);
    var updatedOrder = service.cancelReplaceOrder(new CancelReplaceOrderParamBuilder(
        credentials.accountId,
        anInstrument.instrumentId,
        new BigDecimal("2"),
        sentOrderResponse.orderId,
        OrderType.LIMIT,
        OrderSide.BUY,
        TimeInForce.GTC)
        .limitPrice(new BigDecimal("0.1"))
        .postOnly(true)).get();
    System.out.println(updatedOrder);
    var order = service.getOrderStatus(new GetOrderStatusParamBuilder()
        .accountId(credentials.accountId)
        .orderId(updatedOrder.replacementOrderId)).get();
    System.out.println(order);
  }

  @Test
  public void sendOrderThrowsIfInvalidTickSize() throws InterruptedException, ExecutionException {
    var responseFuture = service.sendOrder(
        new SendOrderParamBuilder(anInstrument, credentials.accountId, TimeInForce.FOK, OrderSide.BUY,
            OrderType.MARKET,
            new BigDecimal("0.1")));
    var response = responseFuture.get();
    assertEquals(response.status, SendOrderResponseStatus.REJECTED);
    assertEquals("Invalid_Order_TickSize", response.errorMessage);
  }

  @Test
  public void getOrderThrowsIfNoOrder() throws InterruptedException {
    var orderFuture = service.getOrderStatus(new GetOrderStatusParamBuilder()
        .accountId(credentials.accountId)
        .orderId(0L));
    assertThrows(ExecutionException.class, () -> orderFuture.get());
  }

  @Test
  public void getOrderStatus() throws InterruptedException, ExecutionException {
    var orderFuture = service.getOrderStatus(new GetOrderStatusParamBuilder()
        .accountId(credentials.accountId)
        .orderId(50443359L));
    var response = orderFuture.get();
    System.out.println(response);

  }

  @Test
  public void sendOrderList() {
    var order1 = new SendOrderParamBuilder(anInstrument, 1, TimeInForce.FOK, OrderSide.BUY, OrderType.MARKET,
        new BigDecimal("10"));
    var order2 = new SendOrderParamBuilder(anInstrument, 1, TimeInForce.FOK, OrderSide.SELL, OrderType.MARKET,
        new BigDecimal("15"));
    List<SendOrderParamBuilder> params = List.of(order1, order2);
    var futureResponse = service.sendOrderList(new SendOrderListParamBuilder(params));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void sendCancelList() {
    var cancelOrder1 = new SendCancelParamBuilder(6714, 9);
    var cancelOrder2 = new SendCancelParamBuilder(6507, 9);
    List<SendCancelParamBuilder> params = List.of(cancelOrder1, cancelOrder2);
    var futureResponse = service.sendCancelList(new SendCancelListParamBuilder(params));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void sendCancelReplaceList() {
    var cancelReplaceOrder1 = new SendCancelReplaceParamBuilder(Long.parseLong("6696"), OrderType.LIMIT,
        OrderSide.BUY,
        7, 1, TimeInForce.GTC, new BigDecimal("0.004")).clientOrderId(Long.parseLong("0"))
        .limitPrice(new BigDecimal("29500"));
    var cancelReplaceOrder2 = new SendCancelReplaceParamBuilder(Long.parseLong("6698"), OrderType.LIMIT,
        OrderSide.BUY,
        7, 1, TimeInForce.GTC, new BigDecimal("0.004")).clientOrderId(Long.parseLong("0"))
        .limitPrice(new BigDecimal("29900"));
    List<SendCancelReplaceParamBuilder> params = List.of(cancelReplaceOrder1, cancelReplaceOrder2);
    var futureResponse = service.sendCancelReplaceList(new SendCancelReplaceListParamBuilder(params));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void modifyOrder() {
    var params = new ModifyOrderParamBuilder(Long.parseLong("6507"), 9, new BigDecimal(0.1), credentials.accountId);
    var futureResponse = service.modifyOrder(params);
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void cancelAllOrders() throws Exception {
    var params = new CancelAllOrdersParamBuilder(9, credentials.accountId);
    var futureResponse = service.cancelAllOrders(params);
    TestHelper.checkNoError(futureResponse);
  }
}
