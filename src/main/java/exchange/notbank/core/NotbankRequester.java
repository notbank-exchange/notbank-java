package exchange.notbank.core;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import io.vavr.control.Either;

public class NotbankRequester {
  private final TimeoutInterceptor timeoutInterceptor;

  public NotbankRequester(TimeoutInterceptor timeoutInterceptor) {
    this.timeoutInterceptor = timeoutInterceptor;
  }

  public <T> Either<String, T> run(
      Supplier<CompletableFuture<T>> getCompletableFuture) {
    return runAndGetError(getCompletableFuture).mapLeft(err -> err.getMessage());
  }

  public <T> Either<NotbankException, T> runAndGetError(Supplier<CompletableFuture<T>> getCompletableFuture) {
    var completableFuture = getCompletableFuture.get();
    return CompletableFutureAdapter.getToEither(timeoutInterceptor.apply(completableFuture));
  }
}
