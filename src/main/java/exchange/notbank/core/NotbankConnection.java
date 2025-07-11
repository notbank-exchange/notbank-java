package exchange.notbank.core;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import exchange.notbank.core.responses.MessageFrame;
import exchange.notbank.core.websocket.CallbackId;
import exchange.notbank.core.websocket.RequestHandler;
import exchange.notbank.core.websocket.SubscriptionCallbacks;
import exchange.notbank.core.websocket.SubscriptionHandler;

import io.vavr.control.Either;

public interface NotbankConnection extends AutoCloseable {
  public <T> CompletableFuture<T> requestGet(EndpointCategory endpointCategory, String endpoint,
      ParamBuilder paramBuilder, Function<String, Either<NotbankException, T>> deserializeFn);

  public <T> CompletableFuture<T> requestPost(EndpointCategory endpointCategory, String endpoint,
      ParamBuilder paramBuilder, Function<String, Either<NotbankException, T>> deserializeFn);

  public <T> CompletableFuture<T> requestPost(EndpointCategory endpointCategory, String endpoint,
      ParamListBuilder paramBuilder, Function<String, Either<NotbankException, T>> deserializeFn);

  public <T> CompletableFuture<T> requestDelete(EndpointCategory endpointCategory, String endpoint,
      ParamBuilder paramBuilder, Function<String, Either<NotbankException, T>> deserializeFn);

  public <T> CompletableFuture<Either<NotbankException, String>> subscribe(
      String endpoint,
      ParamBuilder paramBuilder,
      Function<Consumer<Throwable>, List<SubscriptionHandler<T>>> subscriptionHandlers,
      Function<SubscriptionHandler<T>, CallbackId> callbackIdGetter,
      Function<SubscriptionCallbacks, BiConsumer<CallbackId, Consumer<T>>> callbackAdder);

  public CompletableFuture<Either<NotbankException, Void>> unsubscribe(
      String endpoint,
      ParamBuilder paramBuilder,
      List<Consumer<SubscriptionCallbacks>> removeCallbacks);

  public void setRequestResponses(
      List<RequestHandler> requestHandlers,
      String subscriptionEndpoint,
      Function<RequestHandler, CallbackId> callbackIdGetter,
      Function<SubscriptionCallbacks, BiConsumer<CallbackId, Consumer<MessageFrame>>> callbackAdder);

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
