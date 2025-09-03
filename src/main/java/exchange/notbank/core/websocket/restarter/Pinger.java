package exchange.notbank.core.websocket.restarter;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import exchange.notbank.core.NotbankConnection;

public class Pinger {
  private final Timer timer;
  private final Integer pingIntervalMillis;
  private final Integer pingTimeoutInMillis;

  public Pinger(Timer timer,
      Integer pingIntervalMillis,
      Integer pingTimeoutInMillis) {
    this.timer = timer;
    this.pingIntervalMillis = pingIntervalMillis;
    this.pingTimeoutInMillis = pingTimeoutInMillis;
  }

  public static class Factory {
    public static Pinger create() {
      return create(5_000, 5_000);
    }

    public static Pinger create(
        Integer pingIntervalMillis,
        Integer pingTimeoutInMillis) {
      return new Pinger(
          new Timer(),
          pingIntervalMillis,
          pingTimeoutInMillis);
    }
  }

  public void startPing(NotbankConnection connection, Runnable restartConnection) {
    var pingTask = new TimerTask() {
      public void run() {
        try {
          connection.ping().get(pingTimeoutInMillis, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
          restartConnection.run();
        } catch (InterruptedException e) {
          stop();
          Thread.currentThread().interrupt();
          return;
        } catch (ExecutionException e) {
          stop();
          return;
        }
      }
    };
    timer.schedule(pingTask, pingIntervalMillis);
  }

  void stop() {
    timer.cancel();
  }

}
