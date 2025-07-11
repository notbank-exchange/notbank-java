package exchange.notbank;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import exchange.notbank.core.NotbankException;

public class WebSocketChecker {
  private Optional<NotbankException> error = Optional.empty();

  public void fromThrowable(Throwable err) {
    error = Optional.of(NotbankException.Factory.create(NotbankException.ErrorType.UNKNOWN, err.getMessage()));
  }

  public void assertNoError() {
    assertTrue(error.isEmpty(), () -> error.get().toString());
  }
}
