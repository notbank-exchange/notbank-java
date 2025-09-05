package exchange.notbank.core.websocket.restarter;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import exchange.notbank.core.CompletableFutureAdapter;
import exchange.notbank.core.NotbankConnection;
import exchange.notbank.core.NotbankException;
import exchange.notbank.core.NotbankException.ErrorType;
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

  public Either<NotbankException, NotbankConnection> getConnection() {
    if (connection.isEmpty()) {
      return Either.left(NotbankException.Factory.create(ErrorType.API_CONNECTION, "websocket not connected"));
    }
    return Either.right(this.connection.get());
  }

  public void reconnect() {
    if (reconnecting || closeRequested) {
      return;
    }
    reconnecting = true;
    closeCurrentConnection();
    connection = Optional.empty();
    var newConnection = newConnection();
    if (newConnection.isEmpty()) {
      // close requested
      return;
    }
    connection = Optional.of(newConnection.get());
    var authed = await(reauther.authenticate(connection.get()));
    if (authed.isLeft()) {
      connectionConfiguration.onError().accept(authed.getLeft());
      return;
    }
    var subscribed = await(resubscriber.resubscribe(connection.get()));
    if (subscribed.isLeft()) {
      connectionConfiguration.onError().accept(subscribed.getLeft());
      return;
    }
    pinger.startPing(connection.get(), this::reconnect);
    reconnecting = false;
  }

  private Optional<? extends NotbankConnection> newConnection() {
    while (!closeRequested) {
      var newConnetion = createNewConnection();
      if (newConnetion.isRight()) {
        return newConnetion.toJavaOptional();
      }
    }
    return Optional.empty();
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

  private Optional<Exception> closeCurrentConnection() {
    pinger.stop();
    if (connection.isEmpty()) {
      return Optional.empty();
    }
    try {
      connection.get().close();
    } catch (Exception e) {
      return Optional.of(e);
    }
    return Optional.empty();
  }

  private <T> Either<NotbankException, T> await(CompletableFuture<T> future) {
    return CompletableFutureAdapter.getToEither(future, 10, TimeUnit.SECONDS);
  }

  @Override
  public void close() throws Exception {
    closeRequested = true;
    var error = closeCurrentConnection();
    if (error.isPresent()) {
      throw error.get();
    }
  }
}
