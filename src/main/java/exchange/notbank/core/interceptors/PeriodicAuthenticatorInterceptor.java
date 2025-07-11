package exchange.notbank.core.interceptors;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import exchange.notbank.core.NotbankConnection;
import exchange.notbank.core.NotbankConnectionAuthenticator;
import exchange.notbank.core.UserCredentials;

public class PeriodicAuthenticatorInterceptor {
  private final PeriodicActionInterceptor<NotbankConnection> periodicActionInterceptor;

  PeriodicAuthenticatorInterceptor(
      PeriodicActionInterceptor<NotbankConnection> periodicActionInterceptor) {
    this.periodicActionInterceptor = periodicActionInterceptor;
  }

  public CompletableFuture<NotbankConnection> authenticateConnectionIfRequired(NotbankConnection connection) {
    return periodicActionInterceptor.intercept(connection);
  }

  public static class Factory {
    public static PeriodicAuthenticatorInterceptor create(
        UserCredentials userCredentials,
        Integer authenticationIntervalInMinutes) {
      return new PeriodicAuthenticatorInterceptor(
          new PeriodicActionInterceptor<>(
              connection -> NotbankConnectionAuthenticator.authenticate(
                  connection,
                  userCredentials.userId.toString(),
                  userCredentials.apiPublicKey,
                  userCredentials.apiSecretKey).thenApply(o -> connection),
              Duration.ofMinutes(authenticationIntervalInMinutes)));
    }
  }
}
