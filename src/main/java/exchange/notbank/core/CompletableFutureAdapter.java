package exchange.notbank.core;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.vavr.control.Either;

public class CompletableFutureAdapter {
  public static <T> Either<NotbankException, T> getToEither(CompletableFuture<T> future, long timeout,
      TimeUnit timeUnit) {
    return getToEither(() -> future.get(timeout, timeUnit));
  }

  public static <T> Either<NotbankException, T> getToEither(CompletableFuture<T> future) {
    return getToEither(() -> future.get());
  }

  private static <T> Either<NotbankException, T> getToEither(
      GetFutureSupplier<T> getFutureSupplier) {
    try {
      var result = getFutureSupplier.get();
      return Either.right(result);
    } catch (InterruptedException e) {
      return Either.left(
          NotbankException.Factory.create(NotbankException.ErrorType.INTERRUPTED,
              "interrupted while waiting future result", e));
    } catch (TimeoutException e) {
      return Either.left(
          NotbankException.Factory.create(NotbankException.ErrorType.TIMED_OUT,
              "timed out while waiting future result", e));
    } catch (ExecutionException e) {
      if (e.getCause() instanceof NotbankException) {
        return Either.left((NotbankException) e.getCause());
      }
      return Either.left(
          NotbankException.Factory.create(
              NotbankException.ErrorType.EXECUTION_ERROR,
              "execution error. " + Optional.ofNullable(e.getMessage()).orElse(""), e));
    }
  }

  public static <T> CompletableFuture<T> fromEither(CompletableFuture<Either<NotbankException, T>> futureEither) {
    return futureEither.thenApply(result -> {
      if (result.isLeft()) {
        var brokerError = result.getLeft();
        throw new CompletionException(brokerError);
      }
      return result.get();
    });
  }
}
