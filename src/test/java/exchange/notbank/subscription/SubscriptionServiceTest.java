package exchange.notbank.subscription;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

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
import exchange.notbank.subscription.responses.Level2Ticker;
import exchange.notbank.subscription.responses.SocketTrade;
import exchange.notbank.trading.responses.Level1Ticker;
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
        credentials.apiSecretKey);
  }

  @AfterAll
  public static void afterAll() throws Exception {
    notbankClient.getNotbankConnection().close();
    notbankWebsocketClient.getNotbankConnection().close();
  }

  @Test
  public void subscribeLevel1() throws InterruptedException {
    var feedChecker = new WebSocketFeedChecker();
    var futureResponse = subscriptionService.subscribeLevel1(new SubscribeLevel1ParamBuilder(1),
        level1Ticker -> feedChecker.<Level1Ticker>checkInstrumentId(t -> t.instrumentId, 1),
        level1Ticker -> feedChecker.<Level1Ticker>checkInstrumentId(t -> t.instrumentId, 1));
    TestHelper.checkNoError(futureResponse);
    var futureResponse2 = subscriptionService.subscribeLevel1(new SubscribeLevel1ParamBuilder(2),
        level1Ticker -> feedChecker.<Level1Ticker>checkInstrumentId(t -> t.instrumentId, 2),
        level1Ticker -> feedChecker.<Level1Ticker>checkInstrumentId(t -> t.instrumentId, 2));
    TestHelper.checkNoError(futureResponse2);
    TimeUnit.MINUTES.sleep(2);
    webSocketChecker.assertNoError();
    feedChecker.assertNoError();
  }

  @Test
  public void subscribeLevel2() throws InterruptedException {
    var feedChecker = new WebSocketFeedChecker();
    int instrumentId1 = 66;
    var futureResponse = subscriptionService.subscribeLevel2(
        new SubscribeLevel2ParamBuilder(instrumentId1, 2),
        level1Ticker -> feedChecker.<Level2Ticker>checkInstrumentId(t -> t.productPairCode, instrumentId1),
        level1Ticker -> feedChecker.<Level2Ticker>checkInstrumentId(t -> t.productPairCode, instrumentId1));
    TestHelper.checkNoError(futureResponse);
    var instrumentId2 = 154;
    var futureResponse2 = subscriptionService.subscribeLevel2(
        new SubscribeLevel2ParamBuilder(instrumentId2, 2),
        level1Ticker -> feedChecker.<Level2Ticker>checkInstrumentId(t -> t.productPairCode, instrumentId2),
        level1Ticker -> feedChecker.<Level2Ticker>checkInstrumentId(t -> t.productPairCode, instrumentId2));
    TestHelper.checkNoError(futureResponse2);
    TimeUnit.MINUTES.sleep(2);
    webSocketChecker.assertNoError();
    feedChecker.assertNoError();
  }

  @Test
  public void subscribeTicker() throws InterruptedException {
    var feedChecker = new WebSocketFeedChecker();
    int instrumentId1 = 66;
    var futureResponse = subscriptionService.subscribeTicker(
        new SubscribeTickerParamBuilder(instrumentId1, 60, 3),
        level1Ticker -> feedChecker.<List<Ticker>>checkInstrumentId(getInstrumentId(ticker -> ticker.instrumentId),
            instrumentId1),
        level1Ticker -> feedChecker.<List<Ticker>>checkInstrumentId(getInstrumentId(ticker -> ticker.instrumentId),
            instrumentId1));
    TestHelper.checkNoError(futureResponse);
    var instrumentId2 = 154;
    var futureResponse2 = subscriptionService.subscribeTicker(
        new SubscribeTickerParamBuilder(instrumentId2, 60, 3),
        level1Ticker -> feedChecker.<List<Ticker>>checkInstrumentId(getInstrumentId(ticker -> ticker.instrumentId),
            instrumentId2),
        level1Ticker -> feedChecker.<List<Ticker>>checkInstrumentId(getInstrumentId(ticker -> ticker.instrumentId),
            instrumentId2));
    TestHelper.checkNoError(futureResponse2);
    TimeUnit.MINUTES.sleep(1);
    var unsubscribeFuture = subscriptionService
        .unsubscribeTicker(new UnsubscribeTickerParamBuilder(instrumentId1));
    TestHelper.checkNoError(unsubscribeFuture);
    TimeUnit.MINUTES.sleep(2);
    webSocketChecker.assertNoError();
    feedChecker.assertNoError();
  }

  private <T> Function<List<T>, Integer> getInstrumentId(Function<T, Integer> idExtractor) {
    return list -> list.isEmpty() ? 0 : idExtractor.apply(list.get(0));
  }

  @Test
  public void subscribeSocketTrades() throws InterruptedException {
    var feedChecker = new WebSocketFeedChecker();
    int instrumentId1 = 66;
    var futureResponse = subscriptionService.subscribeTrades(
        new SubscribeTradesParamBuilder(instrumentId1, 3),
        trades -> feedChecker.<List<SocketTrade>>checkInstrumentId(getInstrumentId(trade -> trade.instrumentId),
            instrumentId1));
    TestHelper.checkNoError(futureResponse);
    var instrumentId2 = 154;
    var futureResponse2 = subscriptionService.subscribeTrades(
        new SubscribeTradesParamBuilder(instrumentId2, 3),
        trades -> feedChecker.<List<SocketTrade>>checkInstrumentId(getInstrumentId(trade -> trade.instrumentId),
            instrumentId2));
    TestHelper.checkNoError(futureResponse2);
    TimeUnit.MINUTES.sleep(4);
    webSocketChecker.assertNoError();
    feedChecker.assertNoError();
  }

  public void subscribeOrderState() throws InterruptedException {
    var futureResponse = subscriptionService.subscribeOrderStateEvents(
        new SubscribeOrderStateEventsParamBuilder(13),
        order -> System.out.println(order));
    TestHelper.checkNoError(futureResponse);
    TimeUnit.MINUTES.sleep(1);
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
