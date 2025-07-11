package exchange.notbank.core.interceptors;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

public class PeriodicActionInterceptorTest {
  @Test
  public void actionOcurrsOnEachIntercept() throws InterruptedException {
    var counter = new Counter<String>();
    var periodicActionInterceptor = new PeriodicActionInterceptor<>(counter::countUp, Duration.ofMillis(1));
    assertEquals(0, counter.getCount());
    periodicActionInterceptor.intercept("some data");
    assertEquals(1, counter.getCount());
    TimeUnit.MILLISECONDS.sleep(10);
    periodicActionInterceptor.intercept("some other data");
    assertEquals(2, counter.getCount());
    TimeUnit.MILLISECONDS.sleep(10);
    periodicActionInterceptor.intercept("more data");
    assertEquals(3, counter.getCount());
    assertEquals(3, counter.getCount());
  }

  @Test
  public void actionOnlyOcurrsOnce() throws InterruptedException {
    var counter = new Counter<String>();
    var periodicActionInterceptor = new PeriodicActionInterceptor<>(counter::countUp, Duration.ofMinutes(1));
    assertEquals(0, counter.getCount());
    periodicActionInterceptor.intercept("some data");
    assertEquals(1, counter.getCount());
    TimeUnit.MILLISECONDS.sleep(10);
    periodicActionInterceptor.intercept("some other data");
    assertEquals(1, counter.getCount());
    TimeUnit.MILLISECONDS.sleep(10);
    periodicActionInterceptor.intercept("more data");
    assertEquals(1, counter.getCount());
    assertEquals(1, counter.getCount());
  }

  public static class Counter<T> {
    private Integer count = 0;

    public CompletableFuture<T> countUp(T data) {
      count++;
      return CompletableFuture.completedFuture(data);
    }

    public Integer getCount() {
      return count;
    }
  }

}
