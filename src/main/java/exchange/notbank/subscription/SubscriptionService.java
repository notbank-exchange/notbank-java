package exchange.notbank.subscription;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import exchange.notbank.core.CompletableFutureAdapter;
import exchange.notbank.core.NotbankConnection;
import exchange.notbank.core.NotbankException;
import exchange.notbank.core.ParamBuilder;
import exchange.notbank.core.SubscriptionResponseHandler;
import exchange.notbank.core.websocket.CallbackId;
import exchange.notbank.core.websocket.SubscriptionCallbacks;
import exchange.notbank.core.websocket.SubscriptionHandler;
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
import exchange.notbank.subscription.responses.Level2Ticker;
import exchange.notbank.subscription.responses.SocketTrade;
import exchange.notbank.trading.responses.Level1Ticker;
import exchange.notbank.trading.responses.Order;
import exchange.notbank.trading.responses.Ticker;
import io.vavr.control.Either;

public class SubscriptionService {
  private final Supplier<CompletableFuture<NotbankConnection>> getNotbankConnection;
  private final SubscriptionResponseAdapter userResponseAdapter;

  public SubscriptionService(Supplier<CompletableFuture<NotbankConnection>> getNotbankConnection,
      SubscriptionResponseAdapter userResponseAdapter) {
    this.getNotbankConnection = getNotbankConnection;
    this.userResponseAdapter = userResponseAdapter;
  }

  private <T> CompletableFuture<Either<NotbankException, String>> subscribe(
      String endpoint,
      ParamBuilder paramBuilder,
      Function<Consumer<Throwable>, List<SubscriptionHandler<T>>> subscriptionHandlers,
      Function<SubscriptionHandler<T>, CallbackId> callbackIdGetter,
      Function<SubscriptionCallbacks, BiConsumer<CallbackId, Consumer<T>>> callbackAdder) {
    return getNotbankConnection.get()
        .thenCompose(connection -> connection.subscribe(endpoint, paramBuilder,
            subscriptionHandlers, callbackIdGetter,
            callbackAdder));
  }

  private CompletableFuture<Void> unsubscribe(
      String endpoint,
      ParamBuilder paramBuilder,
      List<Consumer<SubscriptionCallbacks>> removeCallbacks) {
    var futureResponse = getNotbankConnection.get()
        .thenCompose(connection -> connection.unsubscribe(endpoint, paramBuilder, removeCallbacks));
    return CompletableFutureAdapter.fromEither(futureResponse);
  }

  /**
   * https://apidoc.notbank.exchange/#subscribelevel1
   */
  public CompletableFuture<Void> subscribeLevel1(SubscribeLevel1ParamBuilder paramBuilder,
      Consumer<Level1Ticker> snapshotHandler,
      Consumer<Level1Ticker> updateHandler) {
    var instrumentId = paramBuilder.getInstrumentId();
    var handlers = List.of(
        new SubscriptionHandler<>(Endpoints.SUBSCRIBE_LEVEL_1, snapshotHandler),
        new SubscriptionHandler<>(Endpoints.UPDATE_LEVEL_1, updateHandler));
    var futureResponse = subscribe(Endpoints.SUBSCRIBE_LEVEL_1, paramBuilder, o -> handlers,
        handler -> CallbackId.Factory.create(handler.eventName, instrumentId),
        subscriptionCallbacks -> (callbackId, callback) -> subscriptionCallbacks.addLevel1TickerCallback(callbackId,
            callback))
        .thenApply(either -> either.<Void>map(o -> null));
    return CompletableFutureAdapter.fromEither(futureResponse);
  }

  /**
   * https://apidoc.notbank.exchange/#subscribelevel2
   */
  public CompletableFuture<Void> subscribeLevel2(SubscribeLevel2ParamBuilder paramBuilder,
      Consumer<List<Level2Ticker>> snapshotHandler,
      Consumer<List<Level2Ticker>> updateHandler) {
    var instrumentId = paramBuilder.getInstrumentId();
    var handlers = List.of(
        new SubscriptionHandler<>(Endpoints.SUBSCRIBE_LEVEL_2, snapshotHandler),
        new SubscriptionHandler<>(Endpoints.UPDATE_LEVEL_2, updateHandler));
    var futureResponse = subscribe(Endpoints.SUBSCRIBE_LEVEL_2, paramBuilder, o -> handlers,
        handler -> CallbackId.Factory.create(handler.eventName, instrumentId),
        subscriptionCallbacks -> (callbackId, callback) -> subscriptionCallbacks.addLevel2TickerCallback(callbackId,
            callback))
        .thenApply(either -> either.<Void>map(o -> null));
    return CompletableFutureAdapter.fromEither(futureResponse);
  }

  /**
   * https://apidoc.notbank.exchange/#subscribetrades
   */
  public CompletableFuture<Void> subscribeTrades(SubscribeTradesParamBuilder paramBuilder,
      Consumer<List<SocketTrade>> subscriptionHandler) {
    var instrumentId = paramBuilder.getInstrumentId();
    var handlers = List.of(new SubscriptionHandler<>(Endpoints.SUBSCRIBE_TRADES, subscriptionHandler));
    var futureResponse = subscribe(Endpoints.SUBSCRIBE_TRADES, paramBuilder, o -> handlers,
        handler -> CallbackId.Factory.create(handler.eventName, instrumentId),
        subscriptionCallbacks -> (callbackId, callback) -> subscriptionCallbacks.addSocketTradesCallback(callbackId,
            callback))
        .thenApply(either -> either.<Void>map(o -> null));
    return CompletableFutureAdapter.fromEither(futureResponse);
  }

  /**
   * https://apidoc.notbank.exchange/#subscribeticker
   */
  public CompletableFuture<Void> subscribeTicker(SubscribeTickerParamBuilder paramBuilder,
      Consumer<List<Ticker>> snapshotHandler,
      Consumer<List<Ticker>> updateHandler) {
    var instrumentId = paramBuilder.getInstrumentId();
    var handlers = List.of(
        new SubscriptionHandler<>(Endpoints.SUBSCRIBE_TICKER, snapshotHandler),
        new SubscriptionHandler<>(Endpoints.UPDATE_TICKER, updateHandler));
    var futureResponse = subscribe(Endpoints.SUBSCRIBE_TICKER, paramBuilder, o -> handlers,
        handler -> CallbackId.Factory.create(handler.eventName, instrumentId),
        subscriptionCallbacks -> (callbackId, callback) -> subscriptionCallbacks.addTickersCallback(callbackId,
            callback))
        .thenApply(either -> either.<Void>map(o -> null));
    return CompletableFutureAdapter.fromEither(futureResponse);
  }

  /**
   * https://apidoc.notbank.exchange/#unsubscribelevel1
   */
  public CompletableFuture<Void> unsubscribeLevel1(UnsubscribeLevel1ParamBuilder paramBuilder) {
    return unsubscribe(Endpoints.UNSUBSCRIBE_LEVEL_1, paramBuilder,
        List.of(
            subscriptionCallbacks -> subscriptionCallbacks.removeLevel1TickerCallback(
                CallbackId.Factory.create(Endpoints.SUBSCRIBE_LEVEL_1, paramBuilder.instrumentId)),
            subscriptionCallbacks -> subscriptionCallbacks.removeLevel1TickerCallback(
                CallbackId.Factory.create(Endpoints.UPDATE_LEVEL_1, paramBuilder.instrumentId))));
  }

  /**
   * https://apidoc.notbank.exchange/#unsubscribelevel2
   */
  public CompletableFuture<Void> unsubscribeLevel2(UnsubscribeLevel2ParamBuilder paramBuilder) {
    return unsubscribe(Endpoints.UNSUBSCRIBE_LEVEL_2, paramBuilder,
        List.of(
            subscriptionCallbacks -> subscriptionCallbacks.removeLevel2TickerCallback(
                CallbackId.Factory.create(Endpoints.SUBSCRIBE_LEVEL_2, paramBuilder.instrumentId)),
            subscriptionCallbacks -> subscriptionCallbacks.removeLevel2TickerCallback(
                CallbackId.Factory.create(Endpoints.UPDATE_LEVEL_1, paramBuilder.instrumentId))));
  }

  /**
   * https://apidoc.notbank.exchange/#unsubscribetrades
   */
  public CompletableFuture<Void> unsubscribeTrades(UnsubscribeTradesParamBuilder paramBuilder) {
    return unsubscribe(Endpoints.UNSUBSCRIBE_TRADES, paramBuilder,
        List.of(subscriptionCallbacks -> subscriptionCallbacks.removeSocketTradeCallback(
            CallbackId.Factory.create(Endpoints.SUBSCRIBE_TRADES, paramBuilder.instrumentId))));
  }

  /**
   * https://apidoc.notbank.exchange/#unsubscribeticker
   */
  public CompletableFuture<Void> unsubscribeTicker(UnsubscribeTickerParamBuilder paramBuilder) {
    return unsubscribe(Endpoints.UNSUBSCRIBE_TICKER, paramBuilder,
        List.of(
            subscriptionCallbacks -> subscriptionCallbacks.removeTickerCallback(
                CallbackId.Factory.create(Endpoints.SUBSCRIBE_TICKER, paramBuilder.instrumentId)),
            subscriptionCallbacks -> subscriptionCallbacks.removeTickerCallback(
                CallbackId.Factory.create(Endpoints.UPDATE_TICKER, paramBuilder.instrumentId))));
  }

  /**
   * https://apidoc.notbank.exchange/#subscribeaccountevents
   */
  public CompletableFuture<Void> subscribeAccountEvents(
      SubscribeAccountEventsParamBuilder paramBuilder) {
    var futureResponse = subscribe(Endpoints.SUBSCRIBE_ACCOUNT_EVENTS, paramBuilder,
        onError -> paramBuilder.getSubscriptions(userResponseAdapter, onError),
        handler -> CallbackId.Factory.create(handler.eventName),
        subscriptionCallbacks -> (callbackId, callback) -> subscriptionCallbacks.addAccountCallback(callbackId,
            callback))
        .thenApply(SubscriptionResponseHandler::checkBoolean);
    return CompletableFutureAdapter.fromEither(futureResponse);
  }

  /**
   * https://apidoc.notbank.exchange/#unsubscribeaccountevents
   */
  public CompletableFuture<Void> unsubscribeAccountEvents(UnsubscribeAccountEventsParamBuilder paramBuilder) {
    return unsubscribe(Endpoints.UNSUBSCRIBE_ACCOUNT_EVENTS, paramBuilder,
        AccountEventNames.ALL_EVENTS.stream()
            .<Consumer<SubscriptionCallbacks>>map(
                accountEventName -> subscriptionCallbacks -> subscriptionCallbacks.removeAccountCallback(
                    CallbackId.Factory.create(accountEventName, paramBuilder.accountId)))
            .toList());
  }

  /**
   * https://apidoc.notbank.exchange/#subscribeorderstateevents
   */
  public CompletableFuture<Void> subscribeOrderStateEvents(
      SubscribeOrderStateEventsParamBuilder paramBuilder,
      Consumer<Order> subscriptionHandler) {
    var accountId = paramBuilder.getAccountId();
    var handlers = List.of(new SubscriptionHandler<>(Endpoints.SUBSCRIBE_ORDER_STATE_EVENTS, subscriptionHandler));
    var futureResponse = subscribe(Endpoints.SUBSCRIBE_ORDER_STATE_EVENTS, paramBuilder, o -> handlers,
        handler -> CallbackId.Factory.create(handler.eventName, accountId),
        subscriptionCallbacks -> (callbackId, callback) -> subscriptionCallbacks.addOrderCallback(callbackId,
            callback))
        .thenApply(SubscriptionResponseHandler::checkBoolean);
    return CompletableFutureAdapter.fromEither(futureResponse);
  }

  /**
   * https://apidoc.notbank.exchange/#unsubscribeorderstateevents
   */
  public CompletableFuture<Void> unsubscribeOrderStateEvents(UnsubscribeOrderStateEventsParamBuilder paramBuilder) {
    return unsubscribe(Endpoints.UNSUBSCRIBE_ORDER_STATE_EVENTS, paramBuilder,
        List.of(subscriptionCallbacks -> subscriptionCallbacks.removeOrderCallback(
            CallbackId.Factory.create(Endpoints.SUBSCRIBE_ORDER_STATE_EVENTS, paramBuilder.accountId))));
  }
}
