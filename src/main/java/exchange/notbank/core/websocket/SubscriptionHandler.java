package exchange.notbank.core.websocket;

import java.util.function.Consumer;

public class SubscriptionHandler<T> {
  public final String eventName;
  public final Consumer<T> eventHandler;

  public SubscriptionHandler(String eventName, Consumer<T> eventHandler) {
    this.eventName = eventName;
    this.eventHandler = eventHandler;
  }
}