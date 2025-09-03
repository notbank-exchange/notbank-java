package exchange.notbank.core.websocket.callback.subscription;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import exchange.notbank.core.responses.MessageFrame;
import exchange.notbank.core.websocket.WebsocketJsonAdapters;

public class SubscriptionCallbacks {
  private final SubscriptionIdMaker subscriptionIdMaker;
  private final ConcurrentHashMap<SubscriptionId, Consumer<String>> subscriptions;

  public SubscriptionCallbacks(SubscriptionIdMaker subscriptionIdMaker,
      ConcurrentHashMap<SubscriptionId, Consumer<String>> subscriptions) {
    this.subscriptionIdMaker = subscriptionIdMaker;
    this.subscriptions = subscriptions;
  }

  public static class Factory {
    public static SubscriptionCallbacks create(WebsocketJsonAdapters jsonAdapters) {
      return new SubscriptionCallbacks(
          SubscriptionIdMaker.Factory.create(jsonAdapters),
          new ConcurrentHashMap<>());
    }
  }

  public Optional<Consumer<String>> get(MessageFrame messageFrame) {
    var subscriptionId = subscriptionIdMaker.get(messageFrame);
    return Optional.ofNullable(subscriptions.get(subscriptionId));
  }

  public Optional<Consumer<String>> get(SubscriptionId eventIdentifier) {
    return Optional.ofNullable(subscriptions.get(eventIdentifier));
  }

  public void remove(SubscriptionId eventIdentifier) {
    subscriptions.remove(eventIdentifier);
  }

  public void put(EventHandler eventHandler) {
    subscriptions.put(eventHandler.subscriptionId(), eventHandler.eventHandler());
  }
}
