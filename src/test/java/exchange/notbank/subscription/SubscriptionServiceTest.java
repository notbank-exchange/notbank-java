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
  private static SubscriptionService subscriptionService;
  private static WebSocketChecker webSocketChecker;
  private static NotbankClient notbankClient;
  private static NotbankClient notbankWebsocketClient;
  private static UserCredentials credentials;

  @BeforeAll
  public static void beforeAll() throws InterruptedException, ExecutionException {
    notbankClient = NotbankClient.Factory.createRestClient(TestHelper.HOST);
    webSocketChecker = new WebSocketChecker();
    notbankWebsocketClient = TestHelper.newWebsocketClient(
        webSocketChecker::fromThrowable);
    subscriptionService = notbankWebsocketClient
        .getSubscriptionService();
    credentials = TestHelper.getUserCredentials();
    notbankWebsocketClient.authenticate(
        credentials.userId,
        credentials.apiPublicKey,
        credentials.apiSecretKey).get();
  }

  @AfterAll
  public static void afterAll() throws Exception {
    notbankClient.getNotbankConnection().close();
    notbankWebsocketClient.getNotbankConnection().close();
  }

  @Test
  public void subscribeLevel1() throws InterruptedException {
    var feedChecker = new WebSocketFeedChecker();
    var hasInstrumentId = feedChecker.<Level1>getHasInstrumentIdChecker(t -> t.instrumentId);
    int instrumentId1 = 1;
    var futureResponse = subscriptionService.subscribeLevel1(new SubscribeLevel1ParamBuilder(instrumentId1),
        hasInstrumentId.apply(instrumentId1),
        hasInstrumentId.apply(instrumentId1));
    TestHelper.checkNoError(futureResponse);
    int instrumentId2 = 2;
    var futureResponse2 = subscriptionService.subscribeLevel1(new SubscribeLevel1ParamBuilder(instrumentId2),
        hasInstrumentId.apply(instrumentId2),
        hasInstrumentId.apply(instrumentId2));
    TestHelper.checkNoError(futureResponse2);
    TimeUnit.MINUTES.sleep(2);
    webSocketChecker.assertNoError();
    feedChecker.assertNoError();
  }

  @Test
  public void subscribeLevel2() throws InterruptedException {
    var feedChecker = new WebSocketFeedChecker();
    var hasInstrumentId = feedChecker.<Level2>getHasInstrumentIdCheckerFromList(t -> t.productPairCode);
    int instrumentId1 = 66;
    var futureResponse = subscriptionService.subscribeLevel2(
        new SubscribeLevel2ParamBuilder(instrumentId1, 2),
        hasInstrumentId.apply(instrumentId1),
        hasInstrumentId.apply(instrumentId1));
    TestHelper.checkNoError(futureResponse);
    var instrumentId2 = 154;
    var futureResponse2 = subscriptionService.subscribeLevel2(
        new SubscribeLevel2ParamBuilder(instrumentId2, 2),
        hasInstrumentId.apply(instrumentId2),
        hasInstrumentId.apply(instrumentId2));
    TestHelper.checkNoError(futureResponse2);
    TimeUnit.MINUTES.sleep(2);
    webSocketChecker.assertNoError();
    feedChecker.assertNoError();
  }

  @Test
  public void subscribeTicker() throws InterruptedException {
    var feedChecker = new WebSocketFeedChecker();
    var hasInstrumentId = feedChecker.<Ticker>getHasInstrumentIdCheckerFromList(ticker -> ticker.instrumentId);
    int instrumentId1 = 66;
    var futureResponse = subscriptionService.subscribeTicker(
        new SubscribeTickerParamBuilder(instrumentId1, 60, 3),
        hasInstrumentId.apply(instrumentId1),
        hasInstrumentId.apply(instrumentId1));
    TestHelper.checkNoError(futureResponse);
    var instrumentId2 = 154;
    var futureResponse2 = subscriptionService.subscribeTicker(
        new SubscribeTickerParamBuilder(instrumentId2, 60, 3),
        hasInstrumentId.apply(instrumentId2),
        hasInstrumentId.apply(instrumentId2));
    TestHelper.checkNoError(futureResponse2);
    TimeUnit.MINUTES.sleep(1);
    var unsubscribeFuture = subscriptionService
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
    var futureResponse = subscriptionService.subscribeTrades(
        new SubscribeTradesParamBuilder(instrumentId1, 3),
        hasInstrumentId.apply(instrumentId1),
        hasInstrumentId.apply(instrumentId1));
    TestHelper.checkNoError(futureResponse);
    var instrumentId2 = 154;

    var futureResponse2 = subscriptionService.subscribeTrades(
        new SubscribeTradesParamBuilder(instrumentId2, 3),
        hasInstrumentId.apply(instrumentId2),
        hasInstrumentId.apply(instrumentId2));
    TestHelper.checkNoError(futureResponse2);
    TimeUnit.MINUTES.sleep(2);
    webSocketChecker.assertNoError();
    feedChecker.assertNoError();
    subscriptionService.unsubscribeTrades(new UnsubscribeTradesParamBuilder(instrumentId2));
  } 

  @Test
  public void subscribeOrderState() throws InterruptedException, ExecutionException {
    var futureResponse = subscriptionService.subscribeOrderStateEvents(
        new SubscribeOrderStateEventsParamBuilder(credentials.accountId).instrumentId(66),
        order -> System.out.println(order));
    TestHelper.checkNoError(futureResponse);
    TimeUnit.SECONDS.sleep(2);
    var anInstrument = notbankWebsocketClient.getInstrumentService().getInstrument("USDTCLP").get();
    notbankWebsocketClient.getTradingService().sendOrder(
        new SendOrderParamBuilder(
            anInstrument,
            credentials.accountId,
            TimeInForce.FOK,
            OrderSide.BUY,
            OrderType.MARKET,
            new BigDecimal("1")));
    TimeUnit.SECONDS.sleep(65);
    notbankWebsocketClient.getTradingService()
        .cancelAllOrders(new CancelAllOrdersParamBuilder(anInstrument.instrumentId, credentials.accountId)).get();
    TimeUnit.SECONDS.sleep(67);
    webSocketChecker.assertNoError();
  }

  @Test
  public void subscribeAccountEvents() throws InterruptedException {
    var futureResponse = subscriptionService.subscribeAccountEvents(
        new SubscribeAccountEventsParamBuilder(credentials.accountId)
            .accountInfoHandler(accountInfo -> System.out.println("Account Info: " + accountInfo))
            .balanceHandler(balance -> System.out.println("Balance: " + balance))
            .cancelOrderReject(cancelOrderReject -> System.out.println("Cancel Order Reject: " + cancelOrderReject))
            .depositHandler(deposit -> System.out.println("Deposit: " + deposit))
            .depositTicketHandler(depositTicket -> System.out.println("Deposit Ticket: " + depositTicket))
            .orderHandler(order -> System.out.println("Order: " + order)));
    TestHelper.checkNoError(futureResponse);
    TimeUnit.MINUTES.sleep(4);
    webSocketChecker.assertNoError();
  }
}
