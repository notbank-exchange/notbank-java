package exchange.notbank.core.interceptors;

import java.util.Timer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Function;

import exchange.notbank.core.NotbankConnection;
import exchange.notbank.core.NotbankConnectionAuthenticator;
import exchange.notbank.core.PeriodicPingStarter;
import exchange.notbank.core.UserCredentials;

public class PeriodicPingAuthenticatorInterceptor {
  private final PeriodicPingStarter periodicPingStarter;
  private final UserCredentials userCredentials;
  private Function<NotbankConnection, CompletableFuture<NotbankConnection>> getConnectionInterceptor = this::startPing;

  public PeriodicPingAuthenticatorInterceptor(
      PeriodicPingStarter periodicPingStarter,
      UserCredentials userCredentials) {
    this.periodicPingStarter = periodicPingStarter;
    this.userCredentials = userCredentials;
  }

  public CompletableFuture<NotbankConnection> pingConnectionIfRequired(NotbankConnection connection) {
    return getConnectionInterceptor.apply(connection);
  }

  private CompletableFuture<NotbankConnection> startPing(NotbankConnection connection) {
    authenticateConnection(connection);
    periodicPingStarter.startPing(connection);
    this.getConnectionInterceptor = notbankConnection -> CompletableFuture.completedFuture(notbankConnection);
    return authenticateConnection(connection).thenApply(o -> connection);
  }

  public CompletableFuture<Void> authenticateConnection(NotbankConnection connection) {
    return NotbankConnectionAuthenticator.authenticate(
        connection,
        userCredentials.userId.toString(),
        userCredentials.apiPublicKey,
        userCredentials.apiSecretKey);
  }

  public static class Factory {
    public static PeriodicPingAuthenticatorInterceptor create(
        Timer timer,
        Integer pingIntervalMillis,
        Integer pingTimeoutMillis,
        UserCredentials userCredentials) {
      return new PeriodicPingAuthenticatorInterceptor(
          PeriodicPingStarter.Factory.create(timer, pingIntervalMillis, pingTimeoutMillis),
          userCredentials);
    }

    public static PeriodicPingAuthenticatorInterceptor create(
        ScheduledExecutorService executor,
        Integer pingIntervalMillis,
        Integer pingTimeoutMillis,
        UserCredentials userCredentials) {
      return new PeriodicPingAuthenticatorInterceptor(
          PeriodicPingStarter.Factory.create(executor, pingIntervalMillis, pingTimeoutMillis),
          userCredentials);
    }
  }
}
