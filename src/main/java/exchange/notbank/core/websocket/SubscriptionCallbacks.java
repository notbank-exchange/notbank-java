package exchange.notbank.core.websocket;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import exchange.notbank.core.NotbankException;
import exchange.notbank.core.responses.MessageFrame;
import exchange.notbank.subscription.constants.Endpoints;
import exchange.notbank.subscription.responses.Level2;
import exchange.notbank.subscription.responses.SocketTrade;
import exchange.notbank.trading.responses.Ticker;
import exchange.notbank.trading.responses.Level1;
import exchange.notbank.trading.responses.Order;
import exchange.notbank.subscription.constants.AccountEventNames;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import io.vavr.control.Either;

public class SubscriptionCallbacks {
  private final CallbacksById<List<Ticker>> tickersCallbacks;
  private final CallbacksById<Level1> level1TickerCallbacks;
  private final CallbacksById<List<Level2>> level2TickerCallbacks;
  private final CallbacksById<List<SocketTrade>> socketTradeCallbacks;
  private final CallbacksById<AccountEventPayload> accountCallbacks;
  private final CallbacksById<Order> orderCallbacks;

  public SubscriptionCallbacks(CallbacksById<List<Ticker>> tickersCallbacks,
      CallbacksById<Level1> level1TickerCallbacks, CallbacksById<List<Level2>> level2TickerCallbacks,
      CallbacksById<List<SocketTrade>> socketTradeCallbacks, CallbacksById<AccountEventPayload> accountCallbacks,
      CallbacksById<Order> orderCallbacks) {
    this.tickersCallbacks = tickersCallbacks;
    this.level1TickerCallbacks = level1TickerCallbacks;
    this.level2TickerCallbacks = level2TickerCallbacks;
    this.socketTradeCallbacks = socketTradeCallbacks;
    this.accountCallbacks = accountCallbacks;
    this.orderCallbacks = orderCallbacks;
  }

  public static class Factory {
    public static SubscriptionCallbacks create(Moshi moshi) {
      var payloadGetter = PayloadGetter.Factory.create(moshi);
      ParameterizedType TickerListType = Types.newParameterizedType(List.class, Ticker.class);
      JsonAdapter<List<Ticker>> tickerListAdapter = moshi.adapter(TickerListType);
      JsonAdapter<Level1> level1TickerAdapter = moshi.adapter(Level1.class);
      ParameterizedType Level2TickerListType = Types.newParameterizedType(List.class, Level2.class);
      JsonAdapter<List<Level2>> level2TickerListAdapter = moshi.adapter(Level2TickerListType);
      ParameterizedType SocketTradeListType = Types.newParameterizedType(List.class, SocketTrade.class);
      JsonAdapter<List<SocketTrade>> socketTradeListAdapter = moshi.adapter(SocketTradeListType);
      JsonAdapter<Order> orderAdapter = moshi.adapter(Order.class);
      JsonAdapter<AccountEventPayload> accountEventPayloadAdapter = moshi.adapter(AccountEventPayload.class);
      return new SubscriptionCallbacks(
          CallbacksById.Factory.create(
              (eventName, tickers) -> CallbackId.Factory.create(eventName,
                  getInstrumentId(tickers, ticker -> ticker.instrumentId)),
              PayloadDeserializer.newDeserializer(payloadGetter, tickerListAdapter)),
          CallbacksById.Factory.create(
              (eventName, level1Ticker) -> CallbackId.Factory.create(eventName, level1Ticker.instrumentId),
              PayloadDeserializer.newDeserializer(payloadGetter, level1TickerAdapter)),
          CallbacksById.Factory.create(
              (eventName, level2Ticker) -> CallbackId.Factory.create(eventName,
                  getInstrumentId(level2Ticker, ticker -> ticker.productPairCode)),
              PayloadDeserializer.newDeserializer(payloadGetter, level2TickerListAdapter)),
          CallbacksById.Factory.create(
              (eventName, socketTrades) -> CallbackId.Factory.create(eventName,
                  getInstrumentId(socketTrades, socketTrade -> socketTrade.instrumentId)),
              PayloadDeserializer.newDeserializer(payloadGetter, socketTradeListAdapter)),
          CallbacksById.Factory.create(
              (eventName, accountEventPayload) -> CallbackId.Factory.create(eventName,
                  accountEventPayload.getAccountId()),
              messageFrame -> PayloadDeserializer.newDeserializer(payloadGetter, accountEventPayloadAdapter)
                  .apply(messageFrame)
                  .map(accountEventPayload -> accountEventPayload.setPaylaod(messageFrame.payload))),
          CallbacksById.Factory.create(
              (eventName, order) -> CallbackId.Factory.create(eventName, order.account),
              PayloadDeserializer.newDeserializer(payloadGetter, orderAdapter)));
    }

    private static <T> Integer getInstrumentId(List<T> list, Function<T, Integer> getInstrumentId) {
      return list.stream().findFirst().map(getInstrumentId).orElse(0);
    }
  }

  public Either<NotbankException, Optional<Runnable>> getRunnable(MessageFrame messageFrame) {
    if (isTickerEvent(messageFrame)) {
      return tickersCallbacks.getRunnable(messageFrame);
    }
    if (isLevel1TickerEvent(messageFrame)) {
      return level1TickerCallbacks.getRunnable(messageFrame);
    }
    if (isLevel2TickerEvent(messageFrame)) {
      return level2TickerCallbacks.getRunnable(messageFrame);
    }
    if (isSocketTradeEvent(messageFrame)) {
      return socketTradeCallbacks.getRunnable(messageFrame);
    }
    if (isOrderEvent(messageFrame)) {
      return orderCallbacks.getRunnable(messageFrame);
    }
    if (isAccountEvent(messageFrame)) {
      return accountCallbacks.getRunnable(messageFrame);
    }
    return Either.right(Optional.empty());
  }

  public void addTickersCallback(CallbackId callbackId, Consumer<List<Ticker>> callback) {
    tickersCallbacks.add(callbackId, callback);
  }

  public void addLevel1TickerCallback(CallbackId callbackId, Consumer<Level1> callback) {
    level1TickerCallbacks.add(callbackId, callback);
  }

  public void addLevel2TickerCallback(CallbackId callbackId, Consumer<List<Level2>> callback) {
    level2TickerCallbacks.add(callbackId, callback);
  }

  public void addSocketTradesCallback(CallbackId callbackId, Consumer<List<SocketTrade>> callback) {
    socketTradeCallbacks.add(callbackId, callback);
  }

  public void addAccountCallback(CallbackId callbackId, Consumer<AccountEventPayload> callback) {
    accountCallbacks.add(callbackId, callback);
  }

  public void addOrderCallback(CallbackId callbackId, Consumer<Order> callback) {
    orderCallbacks.add(callbackId, callback);
  }

  public void removeTickerCallback(CallbackId callbackId) {
    tickersCallbacks.remove(callbackId);
  }

  public void removeLevel1TickerCallback(CallbackId callbackId) {
    level1TickerCallbacks.remove(callbackId);
  }

  public void removeLevel2TickerCallback(CallbackId callbackId) {
    level2TickerCallbacks.remove(callbackId);
  }

  public void removeSocketTradeCallback(CallbackId callbackId) {
    socketTradeCallbacks.remove(callbackId);
  }

  public void removeAccountCallback(CallbackId callbackId) {
    accountCallbacks.remove(callbackId);
  }

  public void removeOrderCallback(CallbackId callbackId) {
    orderCallbacks.remove(callbackId);
  }

  private boolean isTickerEvent(MessageFrame messageFrame) {
    return messageFrame.functionName.equals(Endpoints.UPDATE_TICKER)
        || messageFrame.functionName.equals(Endpoints.SUBSCRIBE_TICKER);
  }

  private boolean isLevel1TickerEvent(MessageFrame messageFrame) {
    return messageFrame.functionName.equals(Endpoints.UPDATE_LEVEL_1)
        || messageFrame.functionName.equals(Endpoints.SUBSCRIBE_LEVEL_1);
  }

  private boolean isLevel2TickerEvent(MessageFrame messageFrame) {
    return messageFrame.functionName.equals(Endpoints.UPDATE_LEVEL_2)
        || messageFrame.functionName.equals(Endpoints.SUBSCRIBE_LEVEL_2);
  }

  private boolean isSocketTradeEvent(MessageFrame messageFrame) {
    return messageFrame.functionName.equals(Endpoints.SUBSCRIBE_TRADES)
        || messageFrame.functionName.equals(Endpoints.UPDATE_TRADES);
  }

  private boolean isOrderEvent(MessageFrame messageFrame) {
    return messageFrame.functionName.equals(Endpoints.SUBSCRIBE_ORDER_STATE_EVENTS);
  }

  private boolean isAccountEvent(MessageFrame messageFrame) {
    return AccountEventNames.ALL_EVENTS.stream()
        .filter(eventName -> eventName.equals(messageFrame.functionName))
        .findFirst()
        .isPresent();
  }
}
