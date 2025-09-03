package exchange.notbank.core.websocket.restarter;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import exchange.notbank.core.NotbankConnection;

public class Reauther {
  private Function<NotbankConnection, CompletableFuture<Void>> reauthenticateFn;

  public Reauther(Function<NotbankConnection, CompletableFuture<Void>> reauthenticateFn) {
    this.reauthenticateFn = reauthenticateFn;
  }

  public static class Factory {
    public static Reauther create() {
      return new Reauther(o -> CompletableFuture.completedFuture(null));
    }
  }

  public CompletableFuture<Void> authenticate(NotbankConnection connection) {
    return reauthenticateFn.apply(connection);
  }

  public void updateReauthenticateFn(Function<NotbankConnection, CompletableFuture<Void>> reauthenticateFn) {
    this.reauthenticateFn = reauthenticateFn;
  }

}
