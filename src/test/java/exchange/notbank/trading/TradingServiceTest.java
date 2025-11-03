package exchange.notbank.trading;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import exchange.notbank.CredentialsLoader.UserCredentials;
import exchange.notbank.NotbankClient;
import exchange.notbank.TestHelper;
import exchange.notbank.instrument.responses.Instrument;
import exchange.notbank.trading.constants.OrderSide;
import exchange.notbank.trading.constants.OrderType;
import exchange.notbank.trading.constants.PegPriceType;
import exchange.notbank.trading.constants.SendOrderResponseStatus;
import exchange.notbank.trading.constants.TimeInForce;
import exchange.notbank.trading.paramBuilders.CancelAllOrdersParamBuilder;
import exchange.notbank.trading.paramBuilders.CancelOrderParamBuilder;
import exchange.notbank.trading.paramBuilders.CancelReplaceOrderParamBuilder;
import exchange.notbank.trading.paramBuilders.GetAccountTradesParamBuilder;
import exchange.notbank.trading.paramBuilders.GetEnumsParamBuilder;
import exchange.notbank.trading.paramBuilders.GetLastTradesParamBuilder;
import exchange.notbank.trading.paramBuilders.GetLevel1ParamBuilder;
import exchange.notbank.trading.paramBuilders.GetLevel1SummaryParamBuilder;
import exchange.notbank.trading.paramBuilders.GetLevel2SnapshotParamBuilder;
import exchange.notbank.trading.paramBuilders.GetLevelSummaryParamBuilder;
import exchange.notbank.trading.paramBuilders.GetOpenOrdersParamBuilder;
import exchange.notbank.trading.paramBuilders.GetOpenTradeReportParamBuilder;
import exchange.notbank.trading.paramBuilders.GetOrderBookParamBuilder;
import exchange.notbank.trading.paramBuilders.GetOrderHistoryParamBuilder;
import exchange.notbank.trading.paramBuilders.GetOrderStatusParamBuilder;
import exchange.notbank.trading.paramBuilders.GetOrdersHistoryByOrderIdParamBuilder;
import exchange.notbank.trading.paramBuilders.GetOrdersParamBuilder;
import exchange.notbank.trading.paramBuilders.GetSummaryParamBuilder;
import exchange.notbank.trading.paramBuilders.GetTickerHistoryParamBuilder;
import exchange.notbank.trading.paramBuilders.GetTickerParamBuilder;
import exchange.notbank.trading.paramBuilders.GetTradesHistoryParamBuilder;
import exchange.notbank.trading.paramBuilders.ModifyOrderParamBuilder;
import exchange.notbank.trading.paramBuilders.SendCancelListParamBuilder;
import exchange.notbank.trading.paramBuilders.SendCancelParamBuilder;
import exchange.notbank.trading.paramBuilders.SendCancelReplaceListParamBuilder;
import exchange.notbank.trading.paramBuilders.SendCancelReplaceParamBuilder;
import exchange.notbank.trading.paramBuilders.SendOrderListParamBuilder;
import exchange.notbank.trading.paramBuilders.SendOrderParamBuilder;
import exchange.notbank.users.paramBuilders.GetUserAccountsParamBuilder;
import exchange.notbank.trading.paramBuilders.GetTradesParamBuilder;

public class TradingServiceTest {
  private static Integer accountId;
  private static UserCredentials credentials;
  private static Instrument anInstrument;
  private static NotbankClient client;

  @BeforeAll
  public static void beforeAll() throws InterruptedException, ExecutionException {
    client = TestHelper.newRestClient();
    credentials = TestHelper.getUserCredentials();
    accountId = credentials.accountId;
    anInstrument = client.getInstrumentService().getInstrument("BTCUSDT").get();
  }

  @Test
  public void getTradesHistory() {
    var futureResponse = client.getTradingService()
        .getTradesHistory(new GetTradesHistoryParamBuilder().accountId(accountId));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getOpenOrders() {
    var futureResponse = client.getTradingService().getOpenOrders(new GetOpenOrdersParamBuilder(accountId));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getOrders() {
    var futureResponse = client.getTradingService().getOrders(new GetOrdersParamBuilder(accountId));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getOpenTradeReports() {
    var futureResponse = client.getTradingService().getOpenTradeReports(new GetOpenTradeReportParamBuilder(accountId));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getLevel2Snapshot() {
    var futureResponse = client.getTradingService().getLevel2Snapshot(new GetLevel2SnapshotParamBuilder(153, 2));
    TestHelper.checkNoError(futureResponse);
  }

  public void getLevel1SummaryMin() {
    var futureResponse = client.getTradingService()
        .getLevel1SummaryMin(new GetLevelSummaryParamBuilder().instrumentIds(List.of("1", "2", "3")));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getUserAccounts() {
    var futureResponse = client.getTradingService().getUserAccounts(new GetUserAccountsParamBuilder(43));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getLevel1Summary() {
    var futureResponse = client.getTradingService().getLevel1Summary(new GetLevel1SummaryParamBuilder());
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void trades() {
    var futureResponse = client.getTradingService().getTrades(new GetTradesParamBuilder("BTCUSDT"));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getSummary() throws InterruptedException, ExecutionException {
    var futureResponse = client.getTradingService().getSummary(new GetSummaryParamBuilder());
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getTickerHistory() {
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    var futureResponse = client.getTradingService().getTickerHistory(new GetTickerHistoryParamBuilder(
        154,
        60,
        LocalDateTime.now().format(dateFormat),
        LocalDateTime.now().plus(1, ChronoUnit.DAYS).format(dateFormat)));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getLastTrades() {
    var futureResponse = client.getTradingService().getLastTrades(new GetLastTradesParamBuilder(75).count(500));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getTicker() {
    var futureResponse = client.getTradingService().getTicker(new GetTickerParamBuilder());
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getLevel1() {
    var futureResponse = client.getTradingService().getLevel1(new GetLevel1ParamBuilder(154));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void sendOrderIsRejectedIfNotAuthorized() throws InterruptedException {
    var responseFuture = client.getTradingService().sendOrder(
        new SendOrderParamBuilder(
            anInstrument,
            1,
            TimeInForce.FOK,
            OrderSide.BUY,
            OrderType.MARKET)
            .clientOrderId(123L)
            .orderIdOCO(123L)
            .useDisplayQuantity(false)
            .pegPriceType(PegPriceType.ASK)
            .value(new BigDecimal(10)));
    assertThrows(ExecutionException.class, () -> responseFuture.get());
  }

  @Test
  public void sendOrderIsRejectedIfNoFunds() throws InterruptedException, ExecutionException {
    var responseFuture = client.getTradingService().sendOrder(
        new SendOrderParamBuilder(anInstrument, credentials.accountId, TimeInForce.FOK, OrderSide.BUY,
            OrderType.MARKET).quantity(new BigDecimal("10")));
    var response = responseFuture.get();
    assertEquals(response.status, SendOrderResponseStatus.REJECTED);
    assertEquals("Not_Enough_Funds", response.errorMessage);
  }

  @Test
  public void sendOrderWithPostOnly() throws InterruptedException, ExecutionException {
    var sentOrderResponse = client.getTradingService().sendOrder(new SendOrderParamBuilder(
        anInstrument,
        credentials.accountId,
        TimeInForce.GTC,
        OrderSide.BUY,
        OrderType.LIMIT)
        .quantity(new BigDecimal("1"))
        .limitPrice(new BigDecimal("0.1"))).get();
    System.out.println(sentOrderResponse);
    var updatedOrder = client.getTradingService().cancelReplaceOrder(new CancelReplaceOrderParamBuilder(
        credentials.accountId,
        anInstrument.instrumentId,
        new BigDecimal("2"),
        sentOrderResponse.orderId,
        OrderType.STOP_MARKET,
        OrderSide.BUY,
        TimeInForce.GTC)
        .orderIdOCO(123L)
        .useDisplayQuantity(false)
        .pegPriceType(PegPriceType.ASK)
        .value(new BigDecimal("123"))
        .limitPrice(new BigDecimal("0.1"))
        .clientOrderId(123L)
        .postOnly(true)).get();
    System.out.println(updatedOrder);
    var order = client.getTradingService().getOrderStatus(new GetOrderStatusParamBuilder()
        .accountId(credentials.accountId)
        .orderId(updatedOrder.replacementOrderId)).get();
    System.out.println(order);
  }

  @Test
  public void sendOrderThrowsIfInvalidTickSize() throws InterruptedException, ExecutionException {
    var responseFuture = client.getTradingService().sendOrder(
        new SendOrderParamBuilder(anInstrument, credentials.accountId, TimeInForce.FOK, OrderSide.BUY,
            OrderType.MARKET)
            .quantity(new BigDecimal("0.1")));
    var response = responseFuture.get();
    assertEquals(response.status, SendOrderResponseStatus.REJECTED);
    assertEquals("Invalid_Order_TickSize", response.errorMessage);
  }

  @Test
  public void getOrderThrowsIfNoOrder() throws InterruptedException {
    var orderFuture = client.getTradingService().getOrderStatus(new GetOrderStatusParamBuilder()
        .accountId(credentials.accountId)
        .orderId(0L));
    assertThrows(ExecutionException.class, () -> orderFuture.get());
  }

  @Test
  public void getOrderStatus() throws InterruptedException, ExecutionException {
    var orderFuture = client.getTradingService().getOrderStatus(new GetOrderStatusParamBuilder()
        .accountId(credentials.accountId)
        .orderId(50443359L));
    var response = orderFuture.get();
    System.out.println(response);

  }

  @Test
  public void sendOrderList() {
    var order1 = new SendOrderParamBuilder(anInstrument, credentials.accountId, TimeInForce.FOK, OrderSide.BUY,
        OrderType.MARKET).quantity(new BigDecimal("10"));
    var order2 = new SendOrderParamBuilder(anInstrument, credentials.accountId, TimeInForce.FOK, OrderSide.SELL,
        OrderType.MARKET).quantity(new BigDecimal("15"));
    List<SendOrderParamBuilder> params = List.of(order1, order2);
    var futureResponse = client.getTradingService().sendOrderList(new SendOrderListParamBuilder(params));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void sendCancelList() {
    var futureResponse = client.getTradingService().sendCancelList(new SendCancelListParamBuilder(List.of(
        new SendCancelParamBuilder(6714, 9),
        new SendCancelParamBuilder(6507, 9))));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void sendCancelReplaceList() {
    var futureResponse = client.getTradingService().sendCancelReplaceList(new SendCancelReplaceListParamBuilder(List.of(
        new SendCancelReplaceParamBuilder(Long.parseLong("6696"), OrderType.LIMIT,
            OrderSide.BUY, 7, 1, TimeInForce.GTC, new BigDecimal("0.004"))
            .clientOrderId(Long.parseLong("0"))
            .limitPrice(new BigDecimal("29500")),
        new SendCancelReplaceParamBuilder(Long.parseLong("6698"), OrderType.LIMIT,
            OrderSide.BUY, 7, 1, TimeInForce.GTC, new BigDecimal("0.004"))
            .clientOrderId(Long.parseLong("0"))
            .limitPrice(new BigDecimal("29900")))));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void modifyOrder() {
    var futureResponse = client.getTradingService().modifyOrder(
        new ModifyOrderParamBuilder(Long.parseLong("6507"), 9, new BigDecimal(0.1), credentials.accountId));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void cancelAllOrders() throws Exception {
    var futureResponse = client.getTradingService().cancelAllOrders(
        new CancelAllOrdersParamBuilder(9, credentials.accountId));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getEnums() throws Exception {
    var futureResponse = client.getTradingService().getEnums(new GetEnumsParamBuilder());
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getOrderHistory() throws Exception {
    var futureResponse = client.getTradingService()
        .getOrderHistory(new GetOrderHistoryParamBuilder(credentials.accountId));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getOrderHistoryByOrderId() throws Exception {
    var futureResponse = client.getTradingService()
        .getOrderHistoryByOrderId(new GetOrdersHistoryByOrderIdParamBuilder(6459L));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void cancelOrder() throws Exception {
    var futureResponse = client.getTradingService()
        .cancelOrder(new CancelOrderParamBuilder(credentials.accountId, 10L));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getAccountTrades() throws Exception {
    var futureResponse = client.getTradingService()
        .getAccountTrades(new GetAccountTradesParamBuilder().accountId(credentials.accountId).depth(2));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getOrderbook() throws Exception {
    var futureResponse = client.getTradingService().getOrderbook(new GetOrderBookParamBuilder("BTCUSD").level(1));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getTrades() throws Exception {
    var futureResponse = client.getTradingService().getTrades(new GetTradesParamBuilder("BTCUSD"));
    TestHelper.checkNoError(futureResponse);
  }
}
