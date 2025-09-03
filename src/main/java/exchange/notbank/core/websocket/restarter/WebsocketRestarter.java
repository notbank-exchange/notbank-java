package exchange.notbank.core.websocket.restarter;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import exchange.notbank.core.CompletableFutureAdapter;
import exchange.notbank.core.NotbankConnection;
import exchange.notbank.core.NotbankException;
import exchange.notbank.core.websocket.WebsocketNotbankConnection;
import exchange.notbank.core.websocket.WebsocketNotbankConnectionConfiguration;
import io.vavr.control.Either;

public class WebsocketRestarter implements AutoCloseable {
  private final WebsocketNotbankConnectionConfiguration connectionConfiguration;
  private final Pinger pinger;
  public final Resubscriber resubscriber;
  public final Reauther reauther;
  private Optional<NotbankConnection> connection;
  private volatile Boolean reconnecting;
  private volatile Boolean closeRequested;

  public WebsocketRestarter(WebsocketNotbankConnectionConfiguration connectionConfiguration, Pinger pinger,
      Resubscriber resubscriber, Reauther reauther, Optional<NotbankConnection> connection, Boolean reconnecting,
      Boolean closeRequested) {
    this.connectionConfiguration = connectionConfiguration;
    this.pinger = pinger;
    this.resubscriber = resubscriber;
    this.reauther = reauther;
    this.connection = connection;
    this.reconnecting = reconnecting;
    this.closeRequested = closeRequested;
  }

  public static class Factory {
    public static WebsocketRestarter create(
        WebsocketNotbankConnectionConfiguration connectionConfiguration) {
      return new WebsocketRestarter(
          connectionConfiguration,
          Pinger.Factory.create(),
          Resubscriber.Factory.create(),
          Reauther.Factory.create(),
          Optional.empty(),
          false,
          false);
    }
  }

  public NotbankConnection getConnection() {
    return this.connection.get();
  }

  public void reconnect() {
    if (reconnecting || closeRequested) {
      return;
    }
    reconnecting = true;
    closeCurrentConnection();
    var newConnection = newConnection();
    if (newConnection.isLeft()) {
      return;
    }
    connection = Optional.of(newConnection.get());
    var authed = await(reauther.authenticate(getConnection()));
    if (authed.isLeft()) {
      // TODO: do something
      return;
    }
    var subscribed = await(resubscriber.subscribe(getConnection()));
    if (subscribed.isLeft()) {
      // TODO: do something
      return;
    }
    pinger.startPing(getConnection(), this::reconnect);
    reconnecting = false;
  }

  private Either<Void, ? extends NotbankConnection> newConnection() {
    while (!closeRequested) {
      var newConnetion = createNewConnection();
      if (newConnetion.isRight()) {
        return newConnetion.mapLeft(o -> null);
      }
    }
    return Either.left(null);
  }

  private Either<String, ? extends NotbankConnection> createNewConnection() {
    var connection = WebsocketNotbankConnection.Factory.create(new WebsocketNotbankConnectionConfiguration(
        connectionConfiguration.host(),
        connectionConfiguration.notbankConnectionInterceptor(),
        connectionConfiguration.jsonAdapters(),
        err -> {
          if (reconnecting) {
            return;
          }
          connectionConfiguration.onError();
        },
        connectionConfiguration.peekMessageIn(),
        connectionConfiguration.peekMessageOut()));
    return await(connection).mapLeft(Object::toString);
  }

  private void closeCurrentConnection() {
    pinger.stop();
    if (connection.isEmpty()) {
      return;
    }
    try {
      connection.get().close();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private <T> Either<NotbankException, T> await(CompletableFuture<T> future) {
    return CompletableFutureAdapter.getToEither(future, 10, TimeUnit.SECONDS);
  }

  @Override
  public void close() throws Exception {
    closeRequested = true;
    closeCurrentConnection();
  }
}
