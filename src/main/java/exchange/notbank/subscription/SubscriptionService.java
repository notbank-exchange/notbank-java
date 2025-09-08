package exchange.notbank.subscription;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

import exchange.notbank.core.CompletableFutureAdapter;
import exchange.notbank.core.NotbankConnection;
import exchange.notbank.core.NotbankException;
import exchange.notbank.core.ParamBuilder;
import exchange.notbank.core.SubscriptionData;
import exchange.notbank.core.SubscriptionResponseHandler;
import exchange.notbank.core.websocket.callback.subscription.SubscriptionHandler;
import exchange.notbank.core.websocket.callback.subscription.SubscriptionId;
import exchange.notbank.core.websocket.callback.subscription.SubscriptionIdMaker;
import exchange.notbank.subscription.constants.AccountEventNames;
import exchange.notbank.subscription.constants.Endpoints;
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
import exchange.notbank.trading.responses.Level1;
import exchange.notbank.trading.responses.Order;
import exchange.notbank.trading.responses.Ticker;
import io.vavr.control.Either;

public class SubscriptionService {
  private final Supplier<CompletableFuture<NotbankConnection>> getNotbankConnection;
  private final SubscriptionResponseAdapter responseAdapter;

  public SubscriptionService(Supplier<CompletableFuture<NotbankConnection>> getNotbankConnection,
      SubscriptionResponseAdapter responseAdapter) {
    this.getNotbankConnection = getNotbankConnection;
    this.responseAdapter = responseAdapter;
  }

  private CompletableFuture<Either<NotbankException, String>> subscribe(
      String endpoint,
      ParamBuilder paramBuilder,
      List<SubscriptionHandler> subscriptionHandlers) {
    return getNotbankConnection.get()
        .thenCompose(connection -> connection.subscribe(
            new SubscriptionData(endpoint, paramBuilder, subscriptionHandlers)));

  }

  private CompletableFuture<Void> unsubscribe(
      String endpoint,
      ParamBuilder paramBuilder,
      List<SubscriptionId> removeCallbacks) {
    var futureResponse = getNotbankConnection.get()
        .thenCompose(connection -> connection.unsubscribe(endpoint, paramBuilder, removeCallbacks));
    return CompletableFutureAdapter.fromEither(futureResponse);
  }

  /**
   * https://apidoc.notbank.exchange/#subscribelevel1
   */
  public CompletableFuture<Void> subscribeLevel1(SubscribeLevel1ParamBuilder paramBuilder,
      Consumer<Level1> snapshotHandler,
      Consumer<Level1> updateHandler) {
    var handlers = List.of(
        SubscriptionHandler.Factory.create(
            SubscriptionIdMaker.get(Endpoints.SUBSCRIBE_LEVEL_1, paramBuilder.getInstrumentId()),
            responseAdapter::toLevel1,
            snapshotHandler),
        SubscriptionHandler.Factory.create(
            SubscriptionIdMaker.get(Endpoints.UPDATE_LEVEL_1, paramBuilder.getInstrumentId()),
            responseAdapter::toLevel1,
            updateHandler));
    var futureResponse = subscribe(Endpoints.SUBSCRIBE_LEVEL_1, paramBuilder, handlers);
    return CompletableFutureAdapter.fromEither(futureResponse).thenApply(o -> null);
  }

  /**
   * https://apidoc.notbank.exchange/#subscribelevel2
   */
  public CompletableFuture<Void> subscribeLevel2(SubscribeLevel2ParamBuilder paramBuilder,
      Consumer<List<Level2>> snapshotHandler,
      Consumer<List<Level2>> updateHandler) {
    var handlers = List.of(
        SubscriptionHandler.Factory.create(
            SubscriptionIdMaker.get(Endpoints.SUBSCRIBE_LEVEL_2, paramBuilder.getInstrumentId()),
            responseAdapter::toLevel2List,
            snapshotHandler),
        SubscriptionHandler.Factory.create(
            SubscriptionIdMaker.get(Endpoints.UPDATE_LEVEL_2, paramBuilder.getInstrumentId()),
            responseAdapter::toLevel2List,
            updateHandler));
    var futureResponse = subscribe(Endpoints.SUBSCRIBE_LEVEL_2, paramBuilder, handlers);
    return CompletableFutureAdapter.fromEither(futureResponse).thenApply(o -> null);
  }

  /**
   * https://apidoc.notbank.exchange/#subscribetrades
   */
  public CompletableFuture<Void> subscribeTrades(SubscribeTradesParamBuilder paramBuilder,
      Consumer<List<SocketTrade>> snapshotHandler,
      Consumer<List<SocketTrade>> updateHandler) {
    var handlers = List.of(
        SubscriptionHandler.Factory.create(
            SubscriptionIdMaker.get(Endpoints.SUBSCRIBE_TRADES, paramBuilder.getInstrumentId()),
            responseAdapter::toSocketTradeList,
            snapshotHandler),
        SubscriptionHandler.Factory.create(
            SubscriptionIdMaker.get(Endpoints.UPDATE_TRADES, paramBuilder.getInstrumentId()),
            responseAdapter::toSocketTradeList,
            updateHandler));
    var futureResponse = subscribe(Endpoints.SUBSCRIBE_TRADES, paramBuilder, handlers);
    return CompletableFutureAdapter.fromEither(futureResponse).thenApply(o -> null);
  }

  /**
   * https://apidoc.notbank.exchange/#subscribeticker
   */
  public CompletableFuture<Void> subscribeTicker(SubscribeTickerParamBuilder paramBuilder,
      Consumer<List<Ticker>> snapshotHandler,
      Consumer<List<Ticker>> updateHandler) {
    var handlers = List.of(
        SubscriptionHandler.Factory.create(
            SubscriptionIdMaker.get(Endpoints.SUBSCRIBE_TICKER, paramBuilder.getInstrumentId()),
            responseAdapter::toTickerList,
            snapshotHandler),
        SubscriptionHandler.Factory.create(
            SubscriptionIdMaker.get(Endpoints.UPDATE_TICKER, paramBuilder.getInstrumentId()),
            responseAdapter::toTickerList,
            updateHandler));
    var futureResponse = subscribe(Endpoints.SUBSCRIBE_TICKER, paramBuilder, handlers);
    return CompletableFutureAdapter.fromEither(futureResponse).thenApply(o -> null);
  }

  /**
   * https://apidoc.notbank.exchange/#unsubscribelevel1
   */
  public CompletableFuture<Void> unsubscribeLevel1(UnsubscribeLevel1ParamBuilder paramBuilder) {
    return unsubscribe(Endpoints.UNSUBSCRIBE_LEVEL_1, paramBuilder,
        List.of(
            SubscriptionIdMaker.get(Endpoints.SUBSCRIBE_LEVEL_1, paramBuilder.instrumentId),
            SubscriptionIdMaker.get(Endpoints.UPDATE_LEVEL_1, paramBuilder.instrumentId)));
  }

  /**
   * https://apidoc.notbank.exchange/#unsubscribelevel2
   */
  public CompletableFuture<Void> unsubscribeLevel2(UnsubscribeLevel2ParamBuilder paramBuilder) {
    return unsubscribe(Endpoints.UNSUBSCRIBE_LEVEL_2, paramBuilder,
        List.of(
            SubscriptionIdMaker.get(Endpoints.SUBSCRIBE_LEVEL_2, paramBuilder.instrumentId),
            SubscriptionIdMaker.get(Endpoints.UPDATE_LEVEL_1, paramBuilder.instrumentId)));
  }

  /**
   * https://apidoc.notbank.exchange/#unsubscribetrades
   */
  public CompletableFuture<Void> unsubscribeTrades(UnsubscribeTradesParamBuilder paramBuilder) {
    return unsubscribe(Endpoints.UNSUBSCRIBE_TRADES, paramBuilder,
        List.of(
            SubscriptionIdMaker.get(Endpoints.SUBSCRIBE_TRADES, paramBuilder.instrumentId),
            SubscriptionIdMaker.get(Endpoints.UPDATE_TRADES, paramBuilder.instrumentId)));
  }

  /**
   * https://apidoc.notbank.exchange/#unsubscribeticker
   */
  public CompletableFuture<Void> unsubscribeTicker(UnsubscribeTickerParamBuilder paramBuilder) {
    return unsubscribe(Endpoints.UNSUBSCRIBE_TICKER, paramBuilder,
        List.of(
            SubscriptionIdMaker.get(Endpoints.SUBSCRIBE_TICKER, paramBuilder.instrumentId),
            SubscriptionIdMaker.get(Endpoints.UPDATE_TICKER, paramBuilder.instrumentId)));
  }

  /**
   * https://apidoc.notbank.exchange/#subscribeaccountevents
   */
  public CompletableFuture<Void> subscribeAccountEvents(
      SubscribeAccountEventsParamBuilder paramBuilder) {
    var handlers = paramBuilder.getHandlers(responseAdapter);
    var futureResponse = subscribe(Endpoints.SUBSCRIBE_ACCOUNT_EVENTS, paramBuilder, handlers)
        .thenApply(SubscriptionResponseHandler::checkBoolean);
    return CompletableFutureAdapter.fromEither(futureResponse);
  }

  /**
   * https://apidoc.notbank.exchange/#unsubscribeaccountevents
   */
  public CompletableFuture<Void> unsubscribeAccountEvents(UnsubscribeAccountEventsParamBuilder paramBuilder) {
    return unsubscribe(Endpoints.UNSUBSCRIBE_ACCOUNT_EVENTS, paramBuilder,
        AccountEventNames.ALL_EVENTS.stream()
            .map(
                accountEventName -> SubscriptionIdMaker.get(accountEventName, paramBuilder.accountId))
            .toList());
  }

  /**
   * https://apidoc.notbank.exchange/#subscribeorderstateevents
   */
  public CompletableFuture<Void> subscribeOrderStateEvents(
      SubscribeOrderStateEventsParamBuilder paramBuilder,
      Consumer<Order> subscriptionHandler) {
    var handlers = List.of(
        SubscriptionHandler.Factory.create(
            SubscriptionIdMaker.get(Endpoints.ORDER_STATE_EVENT, paramBuilder.getAccountId()),
            responseAdapter::toOrder,
            subscriptionHandler));
    var futureResponse = subscribe(Endpoints.SUBSCRIBE_ORDER_STATE_EVENTS, paramBuilder, handlers)
        .thenApply(SubscriptionResponseHandler::checkBoolean);
    return CompletableFutureAdapter.fromEither(futureResponse);
  }

  /**
   * https://apidoc.notbank.exchange/#unsubscribeorderstateevents
   */
  public CompletableFuture<Void> unsubscribeOrderStateEvents(UnsubscribeOrderStateEventsParamBuilder paramBuilder) {
    return unsubscribe(Endpoints.UNSUBSCRIBE_ORDER_STATE_EVENTS, paramBuilder,
        List.of(
            SubscriptionIdMaker.get(Endpoints.ORDER_STATE_EVENT, paramBuilder.accountId)));
  }
}
