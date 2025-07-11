package exchange.notbank.core;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class TimeoutInterceptor {
  private final Integer requestTimeoutInMillis;

  public TimeoutInterceptor(Integer requestTimeoutInMillis) {
    this.requestTimeoutInMillis = requestTimeoutInMillis;
  }

  public <T> CompletableFuture<T> apply(CompletableFuture<T> completableFuture) {
    return completableFuture.orTimeout(requestTimeoutInMillis, TimeUnit.MILLISECONDS);
  }
}
