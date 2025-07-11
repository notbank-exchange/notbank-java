package exchange.notbank.core.interceptors;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class PeriodicActionInterceptor<T> {
  private final Function<T, CompletableFuture<T>> action;
  private final Duration actionInterval;
  private Instant lastActionInstant = Instant.ofEpochSecond(0);

  public PeriodicActionInterceptor(Function<T, CompletableFuture<T>> action, Duration actionInterval) {
    this.action = action;
    this.actionInterval = actionInterval;
  }

  public CompletableFuture<T> intercept(T data) {
    var currentInstant = Instant.now();
    if (actionIsNotRequired(currentInstant)) {
      return CompletableFuture.completedFuture(data);
    }
    lastActionInstant = currentInstant;
    var actionResult = action.apply(data);
    return actionResult;
  }

  private Duration getDeltaSinceLastAction(Instant currentInstant) {
    return Duration.between(lastActionInstant, currentInstant);
  }

  private boolean actionIsNotRequired(Instant currentInstant) {
    var deltaSinceLastAuthentication = getDeltaSinceLastAction(currentInstant);
    return deltaSinceLastAuthentication.compareTo(actionInterval) < 0;
  }
}
