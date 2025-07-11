package exchange.notbank.core;

import io.vavr.control.Either;

public class StandardResponseAdapter {
  public static Either<NotbankException, Void> toNull(String jsonStr) {
    return Either.right(null);
  }
}
