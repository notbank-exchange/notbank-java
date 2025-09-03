package exchange.notbank.core.websocket.callback.subscription;

import java.util.function.Consumer;
import java.util.function.Function;

import exchange.notbank.core.NotbankException;
import io.vavr.control.Either;

public record SubscriptionHandler(
    SubscriptionId subscriptionId,
    Function<Consumer<NotbankException>, Consumer<String>> eventHandler) {

  public static class Factory {
    public static <T> SubscriptionHandler create(
        SubscriptionId subscriptionId,
        Function<String, Either<NotbankException, T>> adapter,
        Consumer<T> callback) {
      return new SubscriptionHandler(subscriptionId, onError -> jsonStr -> {
        var data = adapter.apply(jsonStr);
        if (data.isLeft()) {
          onError.accept(data.getLeft());
        } else {
          callback.accept(data.get());
        }
      });
    }
  }
}