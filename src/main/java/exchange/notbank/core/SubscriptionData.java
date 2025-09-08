package exchange.notbank.core;

import java.util.List;

import exchange.notbank.core.websocket.callback.subscription.SubscriptionHandler;

public record SubscriptionData(
    String endpoint,
    ParamBuilder paramBuilder,
    List<SubscriptionHandler> subscriptionHandlers) {
}
