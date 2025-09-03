package exchange.notbank.core.websocket.callback.subscription;

import java.util.function.Consumer;

public record EventHandler(
    SubscriptionId subscriptionId,
    Consumer<String> eventHandler) {
}