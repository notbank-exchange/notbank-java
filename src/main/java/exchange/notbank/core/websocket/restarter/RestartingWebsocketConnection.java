package exchange.notbank.core.websocket.restarter;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import exchange.notbank.core.AuthenticateUserParamBuilder;
import exchange.notbank.core.EndpointCategory;
import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.NotbankConnection;
import exchange.notbank.core.NotbankException;
import exchange.notbank.core.ParamBuilder;
import exchange.notbank.core.ParamListBuilder;
import exchange.notbank.core.SubscriptionData;
import exchange.notbank.core.WebAuthenticateResponse;
import exchange.notbank.core.WebAuthenticateUserParamBuilder;
import exchange.notbank.core.websocket.callback.subscription.SubscriptionId;
import io.vavr.control.Either;

public class RestartingWebsocketConnection implements NotbankConnection {
  private final WebsocketRestarter restarter;

  public RestartingWebsocketConnection(WebsocketRestarter restarter) {
    this.restarter = restarter;
  }

  @Override
  public void close() throws Exception {
    restarter.close();
  }

  @Override
  public <T> CompletableFuture<T> requestGet(EndpointCategory endpointCategory, String endpoint,
      ParamBuilder paramBuilder, Function<String, Either<NotbankException, T>> deserializeFn) {
    var connection = restarter.getConnection();
    if (connection.isLeft()) {
      return CompletableFuture.failedFuture(connection.getLeft());
    }
    return connection.get().requestGet(endpointCategory, endpoint, paramBuilder, deserializeFn);
  }

  @Override
  public <T> CompletableFuture<T> requestPost(EndpointCategory endpointCategory, String endpoint,
      ParamBuilder paramBuilder, Function<String, Either<NotbankException, T>> deserializeFn) {
    var connection = restarter.getConnection();
    if (connection.isLeft()) {
      return CompletableFuture.failedFuture(connection.getLeft());
    }
    return connection.get().requestPost(endpointCategory, endpoint, paramBuilder, deserializeFn);
  }

  @Override
  public <T> CompletableFuture<T> requestPostByText(EndpointCategory endpointCategory, String endpoint, String text,
      HttpConfiguration httpConfiguration, Function<String, Either<NotbankException, T>> deserializeFn) {
    var connection = restarter.getConnection();
    if (connection.isLeft()) {
      return CompletableFuture.failedFuture(connection.getLeft());
    }
    return connection.get().requestPostByText(endpointCategory, endpoint, text, httpConfiguration,
        deserializeFn);
  }

  @Override
  public <T> CompletableFuture<T> requestPost(EndpointCategory endpointCategory, String endpoint,
      ParamListBuilder paramBuilder, Function<String, Either<NotbankException, T>> deserializeFn) {
    var connection = restarter.getConnection();
    if (connection.isLeft()) {
      return CompletableFuture.failedFuture(connection.getLeft());
    }
    return connection.get().requestPost(endpointCategory, endpoint, paramBuilder, deserializeFn);
  }

  @Override
  public <T> CompletableFuture<T> requestDelete(EndpointCategory endpointCategory, String endpoint,
      ParamBuilder paramBuilder, Function<String, Either<NotbankException, T>> deserializeFn) {
    var connection = restarter.getConnection();
    if (connection.isLeft()) {
      return CompletableFuture.failedFuture(connection.getLeft());
    }
    return connection.get().requestDelete(endpointCategory, endpoint, paramBuilder, deserializeFn);
  }

  @Override
  public CompletableFuture<Either<NotbankException, String>> subscribe(SubscriptionData subscriptionData) {
    var connection = restarter.getConnection();
    if (connection.isLeft()) {
      return CompletableFuture.failedFuture(connection.getLeft());
    }
    return connection.get().subscribe(subscriptionData)
        .thenCompose(
            subscriptionResult -> {
              if (subscriptionResult.isRight()) {
                restarter.resubscriber.saveSubscription(subscriptionData);
              }
              return CompletableFuture.completedStage(subscriptionResult);
            });
  }

  @Override
  public CompletableFuture<Either<NotbankException, Void>> unsubscribe(String endpoint, ParamBuilder paramBuilder,
      List<SubscriptionId> subscriptionsIds) {
    var connection = restarter.getConnection();
    if (connection.isLeft()) {
      return CompletableFuture.failedFuture(connection.getLeft());
    }
    return connection.get().unsubscribe(endpoint, paramBuilder, subscriptionsIds)
        .thenCompose(
            unsubscriptionResult -> {
              if (unsubscriptionResult.isRight()) {
                restarter.resubscriber.removeSubscriptions(subscriptionsIds);
              }
              return CompletableFuture.completedStage(unsubscriptionResult);
            });
  }

  @Override
  public CompletableFuture<Void> authenticateUser(AuthenticateUserParamBuilder paramBuilder) {
    var connection = restarter.getConnection();
    if (connection.isLeft()) {
      return CompletableFuture.failedFuture(connection.getLeft());
    }
    return connection.get().authenticateUser(paramBuilder)
        .thenCompose(
            authResult -> {
              restarter.reauther.updateReauthenticateFn(conn -> conn.authenticateUser(paramBuilder));
              return CompletableFuture.completedStage(authResult);
            });
  }

  @Override
  public CompletableFuture<WebAuthenticateResponse> webAuthenticateUser(WebAuthenticateUserParamBuilder paramBuilder) {
    var connection = restarter.getConnection();
    if (connection.isLeft()) {
      return CompletableFuture.failedFuture(connection.getLeft());
    }
    return connection.get().webAuthenticateUser(paramBuilder)
        .thenCompose(
            authResult -> {
              restarter.reauther.updateReauthenticateFn(conn -> {
                conn.webAuthenticateUser(paramBuilder);
                return null;
              });
              return CompletableFuture.completedStage(authResult);
            });
  }

  @Override
  public CompletableFuture<Void> logOut() {
    var connection = restarter.getConnection();
    if (connection.isLeft()) {
      return CompletableFuture.failedFuture(connection.getLeft());
    }
    return connection.get().logOut();
  }

  @Override
  public CompletableFuture<Void> ping() {
    var connection = restarter.getConnection();
    if (connection.isLeft()) {
      return CompletableFuture.failedFuture(connection.getLeft());
    }
    return connection.get().ping();
  }

  public void reconnect() {
    restarter.reconnect();
  }

}
