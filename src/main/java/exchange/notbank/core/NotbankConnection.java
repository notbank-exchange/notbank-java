package exchange.notbank.core;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import exchange.notbank.core.websocket.callback.subscription.SubscriptionId;
import io.vavr.control.Either;

public interface NotbankConnection extends AutoCloseable {
  public <T> CompletableFuture<T> requestGet(
      EndpointCategory endpointCategory,
      String endpoint,
      ParamBuilder paramBuilder,
      Function<String, Either<NotbankException, T>> deserializeFn);

  public <T> CompletableFuture<T> requestPost(
      EndpointCategory endpointCategory,
      String endpoint,
      ParamBuilder paramBuilder,
      Function<String, Either<NotbankException, T>> deserializeFn);

  public <T> CompletableFuture<T> requestPostByText(
      EndpointCategory endpointCategory,
      String endpoint,
      String text,
      HttpConfiguration httpConfiguration,
      Function<String, Either<NotbankException, T>> deserializeFn);

  public <T> CompletableFuture<T> requestPost(
      EndpointCategory endpointCategory,
      String endpoint,
      ParamListBuilder paramBuilder,
      Function<String, Either<NotbankException, T>> deserializeFn);

  public <T> CompletableFuture<T> requestDelete(
      EndpointCategory endpointCategory,
      String endpoint,
      ParamBuilder paramBuilder,
      Function<String, Either<NotbankException, T>> deserializeFn);

  public CompletableFuture<Either<NotbankException, String>> subscribe(SubscriptionData subscriptionData);

  public CompletableFuture<Either<NotbankException, Void>> unsubscribe(
      String endpoint,
      ParamBuilder paramBuilder,
      List<SubscriptionId> removeCallbacks);

  /**
   * https://apidoc.notbank.exchange/#authenticateuser
   */
  public CompletableFuture<Void> authenticateUser(AuthenticateUserParamBuilder paramBuilder);

  /**
   * https://apidoc.notbank.exchange/#webauthenticateuser
   */
  public CompletableFuture<WebAuthenticateResponse> webAuthenticateUser(
      WebAuthenticateUserParamBuilder paramBuilder);

  /**
   * https://apidoc.notbank.exchange/#logout
   */
  public CompletableFuture<Void> logOut();

  public CompletableFuture<Void> ping();
}
