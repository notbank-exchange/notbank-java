package exchange.notbank.core;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface GetFutureSupplier<T> {
  public T get() throws InterruptedException, ExecutionException, TimeoutException;
}
