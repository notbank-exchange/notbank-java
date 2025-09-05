package exchange.notbank.core.websocket.restarter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import exchange.notbank.core.CompletableFutureAdapter;
import exchange.notbank.core.NotbankConnection;
import exchange.notbank.core.SubscriptionData;
import exchange.notbank.core.websocket.callback.subscription.SubscriptionHandler;
import exchange.notbank.core.websocket.callback.subscription.SubscriptionId;

public class Resubscriber {
  public Resubscriber(List<SubscriptionData> currentSubscriptions) {
    this.currentSubscriptions = currentSubscriptions;
  }

  private final List<SubscriptionData> currentSubscriptions;

  public static class Factory {
    public static Resubscriber create() {
      return new Resubscriber(new ArrayList<>());
    }
  }

  void saveSubscription(SubscriptionData subscriptionData) {
    currentSubscriptions.add(subscriptionData);
  }

  CompletableFuture<Void> resubscribe(NotbankConnection connection) {
    List<CompletableFuture<Void>> subscriptionResults = new ArrayList<>();
    for (var subscriptionData : currentSubscriptions) {
      var subscribedFuture = connection.subscribe(subscriptionData);
      CompletableFuture<Void> subscribed = CompletableFutureAdapter.fromEither(subscribedFuture).thenApply(o -> null);
      subscriptionResults.add(subscribed);
    }
    return CompletableFuture.allOf(subscriptionResults.toArray(new CompletableFuture[subscriptionResults.size()]));
  }

  public void removeSubscriptions(List<SubscriptionId> subscriptionsIds) {
    for (SubscriptionData subscriptionData : currentSubscriptions) {
      for (SubscriptionHandler handler : subscriptionData.subscriptionHandlers()) {
        if (subscriptionsIds.contains(handler.subscriptionId())) {
          currentSubscriptions.remove(subscriptionData);
          return;
        }
      }
    }
  }
}
