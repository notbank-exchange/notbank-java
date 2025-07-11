package exchange.notbank.core;

import io.vavr.control.Either;

public class SubscriptionResponseHandler {
  public static Either<NotbankException, Void> checkBoolean(Either<NotbankException, String> either) {
    return either.flatMap(jsonResponse -> jsonResponse.contains("true")
        ? Either.right(null)
        : Either.left(NotbankException.Factory.create(NotbankException.ErrorType.RESPONSE_ERROR, "subscription failed")));
  }
}
