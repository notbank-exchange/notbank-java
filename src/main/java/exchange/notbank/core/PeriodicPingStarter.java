package exchange.notbank.core;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import io.vavr.control.Either;

public class PeriodicPingStarter {
  private final NotbankRequester requester;
  private final BiConsumer<TimerTask, Integer> schedulePing;
  private final Integer pingIntervalMillis;
  private final Consumer<Either<NotbankException, Void>> pongHandler;

  public PeriodicPingStarter(
      NotbankRequester requester,
      BiConsumer<TimerTask, Integer> schedulePing,
      Integer pingIntervalMillis,
      Consumer<Either<NotbankException, Void>> pongHandler) {
    this.requester = requester;
    this.schedulePing = schedulePing;
    this.pingIntervalMillis = pingIntervalMillis;
    this.pongHandler = pongHandler;
  }

  public static class Factory {
    public static PeriodicPingStarter create(
        Timer timer,
        Integer pingIntervalMillis,
        Integer pingTimeoutInMillis,
        Consumer<Either<NotbankException, Void>> pongHandler) {
      return new PeriodicPingStarter(
          new NotbankRequester(new TimeoutInterceptor(pingTimeoutInMillis)),
          (task, pingPeriod) -> timer.schedule(task, 0, pingPeriod),
          pingIntervalMillis,
          pongHandler);
    }

    public static PeriodicPingStarter create(
        Timer timer,
        Integer pingIntervalMillis,
        Integer pingTimeoutInMillis) {
      return new PeriodicPingStarter(
          new NotbankRequester(new TimeoutInterceptor(pingTimeoutInMillis)),
          (task, pingPeriod) -> timer.schedule(task, 0, pingPeriod),
          pingIntervalMillis,
          o -> {
          });
    }

    public static PeriodicPingStarter create(
        ScheduledExecutorService executor,
        Integer pingIntervalMillis,
        Integer pingTimeoutInMillis,
        Consumer<Either<NotbankException, Void>> pongHandler) {
      return new PeriodicPingStarter(
          new NotbankRequester(new TimeoutInterceptor(pingTimeoutInMillis)),
          (task, pingPeriod) -> executor.scheduleAtFixedRate(task, 0, pingPeriod, TimeUnit.MILLISECONDS),
          pingIntervalMillis,
          pongHandler);
    }

    public static PeriodicPingStarter create(
        ScheduledExecutorService executor,
        Integer pingIntervalMillis,
        Integer pingTimeoutInMillis) {
      return new PeriodicPingStarter(
          new NotbankRequester(new TimeoutInterceptor(pingTimeoutInMillis)),
          (task, pingPeriod) -> executor.scheduleAtFixedRate(task, 0, pingPeriod, TimeUnit.MILLISECONDS),
          pingIntervalMillis,
          o -> {
          });
    }
  }

  public TimerTask startPing(NotbankConnection connection) {
    var pingTask = new TimerTask() {
      public void run() {
        var pong = requester.runAndGetError(() -> connection.ping());
        pongHandler.accept(pong);
      }
    };
    schedulePing.accept(pingTask, pingIntervalMillis);
    return pingTask;
  }

}
