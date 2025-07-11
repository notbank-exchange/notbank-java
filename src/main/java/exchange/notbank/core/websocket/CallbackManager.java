package exchange.notbank.core.websocket;

import exchange.notbank.core.responses.MessageFrame;
import com.squareup.moshi.Moshi;

public class CallbackManager {
  public final SequencedCallbacks<MessageFrame> sequencedCallbacks;
  public final SubscriptionCallbacks subscriptionCallbacks;

  public CallbackManager(SequencedCallbacks<MessageFrame> sequencedCallbacks,
      SubscriptionCallbacks subscriptionCallbacks) {
    this.sequencedCallbacks = sequencedCallbacks;
    this.subscriptionCallbacks = subscriptionCallbacks;
  }

  public static class Factory {
    public static CallbackManager create(Moshi moshi) {
      return new CallbackManager(
          SequencedCallbacks.Factory.create(),
          SubscriptionCallbacks.Factory.create(moshi));
    }
  }
}
