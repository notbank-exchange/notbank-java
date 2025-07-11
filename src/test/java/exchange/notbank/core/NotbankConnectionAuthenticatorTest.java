package exchange.notbank.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;

import exchange.notbank.core.NotbankException.ErrorType;

public class NotbankConnectionAuthenticatorTest {
  @Test
  public void invalidKeyExceptionThrownInFuture() {
    var authenticationFuture = NotbankConnectionAuthenticator.authenticate(
        o -> CompletableFuture.completedFuture(null),
        "123",
        "apikey",
        "");
    var authenticationResult = CompletableFutureAdapter.getToEither(authenticationFuture);
    if (authenticationResult.isRight()) {
      fail();
    }
    assertEquals(authenticationResult.getLeft().errorType, ErrorType.EXECUTION_ERROR);
    assertTrue(authenticationResult.getLeft().getMessage().toLowerCase().contains("empty key"));

  }
}
