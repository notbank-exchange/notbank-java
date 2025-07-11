package exchange.notbank.core;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.Timer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exchange.notbank.TestHelper;

public class PeriodicPingStarterTest {
  private Optional<NotbankException> error;
  private Integer count;

  @BeforeEach
  public void beforeEach() {
    error = Optional.empty();
    count = 0;
  }

  @Test
  public void pingTest() throws InterruptedException, ExecutionException {
    var serviceFactory = TestHelper.newWebsocketClient(System.out::println);
    var pinger = PeriodicPingStarter.Factory.create(
        new Timer(),
        180_000,
        3_000,
        pong -> {
          if (pong.isLeft()) {
            error = Optional.of(pong.getLeft());
          } else {
            count++;
          }
        });
    var pingerTask = pinger.startPing(serviceFactory.getNotbankConnection());
    TimeUnit.SECONDS.sleep(20);
    pingerTask.cancel();
    assertTrue(error.isEmpty(), () -> error.get().toString());
    assertTrue(count > 6);
  }
}
