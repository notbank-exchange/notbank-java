package exchange.notbank.subscription;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import exchange.notbank.CredentialsLoader.UserCredentials;
import exchange.notbank.NotbankClient;
import exchange.notbank.TestHelper;
import exchange.notbank.WebSocketChecker;
import exchange.notbank.WebSocketFeedChecker;
import exchange.notbank.subscription.paramBuilders.SubscribeAccountEventsParamBuilder;
import exchange.notbank.subscription.paramBuilders.SubscribeLevel1ParamBuilder;
import exchange.notbank.subscription.paramBuilders.SubscribeLevel2ParamBuilder;
import exchange.notbank.subscription.paramBuilders.SubscribeOrderStateEventsParamBuilder;
import exchange.notbank.subscription.paramBuilders.SubscribeTickerParamBuilder;
import exchange.notbank.subscription.paramBuilders.SubscribeTradesParamBuilder;
import exchange.notbank.subscription.paramBuilders.UnsubscribeAccountEventsParamBuilder;
import exchange.notbank.subscription.paramBuilders.UnsubscribeLevel1ParamBuilder;
import exchange.notbank.subscription.paramBuilders.UnsubscribeLevel2ParamBuilder;
import exchange.notbank.subscription.paramBuilders.UnsubscribeOrderStateEventsParamBuilder;
import exchange.notbank.subscription.paramBuilders.UnsubscribeTickerParamBuilder;
import exchange.notbank.subscription.paramBuilders.UnsubscribeTradesParamBuilder;
import exchange.notbank.subscription.responses.Level2;
import exchange.notbank.subscription.responses.SocketTrade;
import exchange.notbank.trading.constants.OrderSide;
import exchange.notbank.trading.constants.OrderType;
import exchange.notbank.trading.constants.TimeInForce;
import exchange.notbank.trading.paramBuilders.CancelAllOrdersParamBuilder;
import exchange.notbank.trading.paramBuilders.SendOrderParamBuilder;
import exchange.notbank.trading.responses.Level1;
import exchange.notbank.trading.responses.Ticker;

public class SubscriptionServiceTest {
  private static WebSocketChecker webSocketChecker;
  private static NotbankClient notbankClient;
  private static NotbankClient client;
  private static UserCredentials credentials;

  @BeforeAll
  public static void beforeAll() throws InterruptedException, ExecutionException {
    notbankClient = NotbankClient.Factory.createRestClient(TestHelper.HOST);
    webSocketChecker = new WebSocketChecker();
    client = TestHelper.newWebsocketClient(
        webSocketChecker::fromThrowable);
    credentials = TestHelper.getUserCredentials();
    client.authenticate(
        credentials.userId,
        credentials.apiPublicKey,
        credentials.apiSecretKey).get();
  }

  @AfterAll
  public static void afterAll() throws Exception {
    notbankClient.getNotbankConnection().close();
    client.getNotbankConnection().close();
  }

  @Test
  public void subscribeLevel1() throws InterruptedException {
    var feedChecker = new WebSocketFeedChecker();
    var hasInstrumentId = feedChecker.<Level1>getHasInstrumentIdChecker(t -> t.instrumentId);
    int instrumentId1 = 1;
    var futureResponse = client.getSubscriptionService().subscribeLevel1(new SubscribeLevel1ParamBuilder(instrumentId1),
        hasInstrumentId.apply(instrumentId1),
        hasInstrumentId.apply(instrumentId1));
    TestHelper.checkNoError(futureResponse);
    int instrumentId2 = 2;
    var futureResponse2 = client.getSubscriptionService().subscribeLevel1(
        new SubscribeLevel1ParamBuilder(instrumentId2),
        hasInstrumentId.apply(instrumentId2),
        hasInstrumentId.apply(instrumentId2));
    TestHelper.checkNoError(futureResponse2);
    TimeUnit.MINUTES.sleep(2);
    client.getSubscriptionService().unsubscribeLevel1(new UnsubscribeLevel1ParamBuilder(2));
    webSocketChecker.assertNoError();
    feedChecker.assertNoError();
  }

  @Test
  public void subscribeLevel2() throws InterruptedException {
    var feedChecker = new WebSocketFeedChecker();
    var hasInstrumentId = feedChecker.<Level2>getHasInstrumentIdCheckerFromList(t -> t.productPairCode);
    int instrumentId1 = 66;
    var futureResponse = client.getSubscriptionService().subscribeLevel2(
        new SubscribeLevel2ParamBuilder(instrumentId1, 2),
        hasInstrumentId.apply(instrumentId1),
        hasInstrumentId.apply(instrumentId1));
    TestHelper.checkNoError(futureResponse);
    var instrumentId2 = 154;
    var futureResponse2 = client.getSubscriptionService().subscribeLevel2(
        new SubscribeLevel2ParamBuilder(instrumentId2, 2),
        hasInstrumentId.apply(instrumentId2),
        hasInstrumentId.apply(instrumentId2));
    TestHelper.checkNoError(futureResponse2);
    TimeUnit.SECONDS.sleep(10);
    client.getSubscriptionService().unsubscribeLevel2(new UnsubscribeLevel2ParamBuilder(instrumentId1));
    client.getSubscriptionService().unsubscribeLevel2(new UnsubscribeLevel2ParamBuilder(instrumentId2));
    TimeUnit.SECONDS.sleep(10);
    webSocketChecker.assertNoError();
    feedChecker.assertNoError();
  }

  @Test
  public void subscribeTicker() throws InterruptedException {
    var feedChecker = new WebSocketFeedChecker();
    var hasInstrumentId = feedChecker.<Ticker>getHasInstrumentIdCheckerFromList(ticker -> ticker.instrumentId);
    int instrumentId1 = 66;
    var futureResponse = client.getSubscriptionService().subscribeTicker(
        new SubscribeTickerParamBuilder(instrumentId1, 60, 3),
        hasInstrumentId.apply(instrumentId1),
        hasInstrumentId.apply(instrumentId1));
    TestHelper.checkNoError(futureResponse);
    var instrumentId2 = 154;
    var futureResponse2 = client.getSubscriptionService().subscribeTicker(
        new SubscribeTickerParamBuilder(instrumentId2, 60, 3),
        hasInstrumentId.apply(instrumentId2),
        hasInstrumentId.apply(instrumentId2));
    TestHelper.checkNoError(futureResponse2);
    TimeUnit.MINUTES.sleep(1);
    var unsubscribeFuture = client.getSubscriptionService()
        .unsubscribeTicker(new UnsubscribeTickerParamBuilder(instrumentId1));
    TestHelper.checkNoError(unsubscribeFuture);
    TimeUnit.MINUTES.sleep(2);
    webSocketChecker.assertNoError();
    feedChecker.assertNoError();
  }

  @Test
  public void subscribeSocketTrades() throws InterruptedException {
    var feedChecker = new WebSocketFeedChecker();
    var hasInstrumentId = feedChecker.<SocketTrade>getHasInstrumentIdCheckerFromList(trade -> trade.instrumentId);
    int instrumentId1 = 66;
    var futureResponse = client.getSubscriptionService().subscribeTrades(
        new SubscribeTradesParamBuilder(instrumentId1, 3),
        hasInstrumentId.apply(instrumentId1),
        hasInstrumentId.apply(instrumentId1));
    TestHelper.checkNoError(futureResponse);
    var instrumentId2 = 154;

    var futureResponse2 = client.getSubscriptionService().subscribeTrades(
        new SubscribeTradesParamBuilder(instrumentId2, 3),
        hasInstrumentId.apply(instrumentId2),
        hasInstrumentId.apply(instrumentId2));
    TestHelper.checkNoError(futureResponse2);
    TimeUnit.MINUTES.sleep(2);
    webSocketChecker.assertNoError();
    feedChecker.assertNoError();
    client.getSubscriptionService().unsubscribeTrades(new UnsubscribeTradesParamBuilder(instrumentId2));
  }

  @Test
  public void subscribeOrderState() throws InterruptedException, ExecutionException {
    var futureResponse = client.getSubscriptionService().subscribeOrderStateEvents(
        new SubscribeOrderStateEventsParamBuilder(credentials.accountId).instrumentId(66),
        order -> System.out.println(order));
    TestHelper.checkNoError(futureResponse);
    TimeUnit.SECONDS.sleep(2);
    var anInstrument = client.getInstrumentService().getInstrument("USDTCLP").get();
    client.getTradingService().sendOrder(
        new SendOrderParamBuilder(
            anInstrument,
            credentials.accountId,
            TimeInForce.FOK,
            OrderSide.BUY,
            OrderType.MARKET)
            .quantity(new BigDecimal("1")));
    TimeUnit.SECONDS.sleep(65);
    client.getTradingService()
        .cancelAllOrders(new CancelAllOrdersParamBuilder(anInstrument.instrumentId, credentials.accountId)).get();
    TimeUnit.SECONDS.sleep(67);
    client.getSubscriptionService()
        .unsubscribeOrderStateEvents(new UnsubscribeOrderStateEventsParamBuilder(credentials.accountId));
    webSocketChecker.assertNoError();
  }

  @Test
  public void subscribeAccountEvents() throws InterruptedException {
    var futureResponse = client.getSubscriptionService().subscribeAccountEvents(
        new SubscribeAccountEventsParamBuilder(credentials.accountId)
            .withdrawTicketHandler(withdrawTicket -> System.out.print("withdraw ticket: " + withdrawTicket))
            .transactionHandler(transaction -> System.out.println("transaction: " + transaction))
            .tradeHandler(trade -> System.out.println("trade: " + trade))
            .orderHandler(order -> System.out.println("Order: " + order))
            .depositTicketHandler(depositTicket -> System.out.println("Deposit Ticket: " + depositTicket))
            .accountInfoHandler(accountInfo -> System.out.println("Account Info: " + accountInfo))
            .depositHandler(deposit -> System.out.println("Deposit: " + deposit))
            .cancelOrderReject(cancelOrderReject -> System.out.println("Cancel Order Reject: " + cancelOrderReject))
            .balanceHandler(balance -> System.out.println("Balance: " + balance)));
    TestHelper.checkNoError(futureResponse);
    TimeUnit.MINUTES.sleep(4);
    client.getSubscriptionService()
        .unsubscribeAccountEvents(new UnsubscribeAccountEventsParamBuilder(credentials.accountId));
    webSocketChecker.assertNoError();
  }
}
