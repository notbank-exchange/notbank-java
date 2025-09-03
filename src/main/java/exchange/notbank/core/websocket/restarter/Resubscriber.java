package exchange.notbank.core.websocket.restarter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import exchange.notbank.core.CompletableFutureAdapter;
import exchange.notbank.core.NotbankConnection;
import exchange.notbank.core.NotbankException;
import exchange.notbank.core.SubscriptionData;
import exchange.notbank.core.NotbankException.ErrorType;
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

  CompletableFuture<Void> subscribe(NotbankConnection connection) {
    List<CompletableFuture<Void>> subscriptionResults = new ArrayList<>();
    for (var subscriptionData : currentSubscriptions) {
      var subscribedFuture = connection.subscribe(subscriptionData);
      var some = CompletableFutureAdapter.fromEither(subscribedFuture).thenCompose(this::checkSubscriptionResult);
      subscriptionResults.add(some);
    }
    return CompletableFuture.allOf(subscriptionResults.toArray(new CompletableFuture[subscriptionResults.size()]))
        .thenApply(o -> null);
  }

  private CompletionStage<Void> checkSubscriptionResult(String subscriptionResult) {
    if (subscriptionResult.contains("true")) {
      return CompletableFuture.completedStage(null);
    }
    return CompletableFuture
        .failedStage(NotbankException.Factory.create(ErrorType.RESPONSE_ERROR,
            "subscription failed. response was " + subscriptionResult));
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
