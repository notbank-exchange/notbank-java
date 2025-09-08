package exchange.notbank.core.websocket.callback;

import exchange.notbank.core.responses.MessageFrame;
import exchange.notbank.core.websocket.SequencedCallbacks;
import exchange.notbank.core.websocket.WebsocketJsonAdapters;
import exchange.notbank.core.websocket.callback.subscription.SubscriptionCallbacks;

public record CallbackManager(SequencedCallbacks<MessageFrame> sequencedCallbacks,
    SubscriptionCallbacks subscriptionCallbacks) {

  public static class Factory {
    public static CallbackManager create(WebsocketJsonAdapters jsonAdapters) {
      return new CallbackManager(
          SequencedCallbacks.Factory.create(),
          SubscriptionCallbacks.Factory.create(jsonAdapters));
    }
  }
}
