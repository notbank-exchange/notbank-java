package exchange.notbank.core.rest;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import exchange.notbank.core.AuthenticateUserParamBuilder;
import exchange.notbank.core.CompletableFutureAdapter;
import exchange.notbank.core.EndpointCategory;
import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.NotbankConnection;
import exchange.notbank.core.NotbankException;
import exchange.notbank.core.ParamBuilder;
import exchange.notbank.core.ParamListBuilder;
import exchange.notbank.core.WebAuthenticateResponse;
import exchange.notbank.core.WebAuthenticateUserParamBuilder;
import exchange.notbank.core.constants.HttpMethod;
import exchange.notbank.core.responses.MessageFrame;
import exchange.notbank.core.websocket.CallbackId;
import exchange.notbank.core.websocket.RequestHandler;
import exchange.notbank.core.websocket.SubscriptionCallbacks;
import exchange.notbank.core.websocket.SubscriptionHandler;
import io.vavr.control.Either;

public class HttpNotbankConnection implements NotbankConnection {
  private static final String AUTHENTICATE_USER = "AuthenticateUser";
  private static final String LOG_OUT = "Logout";
  private static final String PING = "Ping";
  private final HttpClient httpClient;
  private final AuthenticationResponseAdapter authenticationResponseAdapter;

  private final SessionToken sessionToken;

  public HttpNotbankConnection(HttpClient httpClient,
      AuthenticationResponseAdapter authenticationResponseAdapter) {
    this.httpClient = httpClient;
    this.authenticationResponseAdapter = authenticationResponseAdapter;
    this.sessionToken = new SessionToken();
  }

  @Override
  public <T> CompletableFuture<T> requestGet(EndpointCategory endpointCategory, String endpoint,
      ParamBuilder paramBuilder,
      Function<String, Either<NotbankException, T>> deserializationFn) {
    var future = httpClient.get(
        endpointCategory,
        endpoint,
        MapStrObjectToMapStrStrConverter.convert(paramBuilder.getParams()),
        sessionToken.withSessionTokenIfPresent(paramBuilder.getHttpConfiguration()));
    return handleFutureResponse(future, deserializationFn);
  }

  @Override
  public <T> CompletableFuture<T> requestPost(EndpointCategory endpointCategory, String endpoint,
      ParamBuilder paramBuilder,
      Function<String, Either<NotbankException, T>> deserializationFn) {
    var future = httpClient.fetch(
        HttpMethod.POST,
        endpointCategory,
        endpoint,
        paramBuilder.getParams(),
        sessionToken.withSessionTokenIfPresent(paramBuilder.getHttpConfiguration()));
    return handleFutureResponse(future, deserializationFn);
  }

  @Override
  public <T> CompletableFuture<T> requestPost(EndpointCategory endpointCategory, String endpoint,
      ParamListBuilder paramBuilder,
      Function<String, Either<NotbankException, T>> deserializationFn) {
    var future = httpClient.postList(
        endpointCategory,
        endpoint,
        paramBuilder.getParams(),
        sessionToken.withSessionTokenIfPresent(paramBuilder.getHttpConfiguration()));
    return handleFutureResponse(future, deserializationFn);

  }

  @Override
  public <T> CompletableFuture<T> requestDelete(EndpointCategory endpointCategory, String endpoint,
      ParamBuilder paramBuilder,
      Function<String, Either<NotbankException, T>> deserializationFn) {
    var future = httpClient.fetch(
        HttpMethod.DELETE,
        endpointCategory,
        endpoint,
        paramBuilder.getParams(),
        sessionToken.withSessionTokenIfPresent(paramBuilder.getHttpConfiguration()));
    return handleFutureResponse(future, deserializationFn);
  }

  @Override
  public <T> CompletableFuture<T> requestPostByText(EndpointCategory endpointCategory, String endpoint, String text,
      HttpConfiguration httpConfiguration, Function<String, Either<NotbankException, T>> deserializeFn) {
    var future = httpClient.postText(endpointCategory, endpoint, text, httpConfiguration.customizeRequestBuilderFn);
    return handleFutureResponse(future, deserializeFn);
  }

  private <T> CompletableFuture<T> handleFutureResponse(
      CompletableFuture<Either<NotbankException, String>> futureResponse,
      Function<String, Either<NotbankException, T>> deserializationFn) {
    var response = futureResponse.thenApply(jsonString -> jsonString.flatMap(deserializationFn));
    return CompletableFutureAdapter.fromEither(response);
  }

  @Override
  public CompletableFuture<Void> authenticateUser(AuthenticateUserParamBuilder paramBuilder) {
    var customizeRequestBuilderFn = paramBuilder.getHttpConfiguration().customizeRequestBuilderFn;
    var updatedCustomizeRequestBuilderFn = customizeRequestBuilderFn.andThen(SetAuthHeaderFn.get(paramBuilder));
    paramBuilder.setHttpConfiguration(new HttpConfiguration(updatedCustomizeRequestBuilderFn));
    return requestGet(
        EndpointCategory.AP,
        AUTHENTICATE_USER,
        paramBuilder,
        authenticationResponseAdapter::toResponse)
        .thenCompose(authUserResponse -> !authUserResponse.authenticated
            ? CompletableFuture.failedStage(NotbankException.Factory.create(
                NotbankException.ErrorType.RESPONSE_ERROR,
                authUserResponse.errorMessage))
            : CompletableFuture.completedStage(authUserResponse))
        .thenAccept(sessionToken::setSessionToken);
  }

  /**
   * https://apidoc.notbank.exchange/#ping
   */
  @Override
  public CompletableFuture<Void> ping() {
    return requestPost(EndpointCategory.AP, PING, ParamBuilder.EMPTY, authenticationResponseAdapter::toNone);
  }

  @Override
  public CompletableFuture<Void> logOut() {
    return requestPost(EndpointCategory.AP, LOG_OUT, ParamBuilder.EMPTY, authenticationResponseAdapter::toNone);
  }

  @Override
  public void close() throws Exception {
    httpClient.close();
  }

  @Override
  public void setRequestResponses(
      List<RequestHandler> requestHandlers,
      String subscriptionEndpoint,
      Function<RequestHandler, CallbackId> callbackIdGetter,
      Function<SubscriptionCallbacks, BiConsumer<CallbackId, Consumer<MessageFrame>>> callbackAdder) {
    throw new UnsupportedOperationException("Unsupported method 'setRequestResponses' for http communication");
  }

  @Override
  public CompletableFuture<WebAuthenticateResponse> webAuthenticateUser(WebAuthenticateUserParamBuilder paramBuilder) {
    throw new UnsupportedOperationException("Unsupported method 'webAuthenticateUser' for http communication");
  }

  @Override
  public CompletableFuture<Either<NotbankException, Void>> unsubscribe(String endpoint, ParamBuilder paramBuilder,
      List<Consumer<SubscriptionCallbacks>> removeCallback) {
    throw new UnsupportedOperationException("Unsupported method 'unsubscribe' for http communication");
  }

  @Override
  public <T> CompletableFuture<Either<NotbankException, String>> subscribe(String endpoint, ParamBuilder paramBuilder,
      Function<Consumer<Throwable>, List<SubscriptionHandler<T>>> subscriptionHandlers,
      Function<SubscriptionHandler<T>, CallbackId> callbackIdGetter,
      Function<SubscriptionCallbacks, BiConsumer<CallbackId, Consumer<T>>> callbackAdder) {
    throw new UnsupportedOperationException("Unsupported method 'subscribe' for http communication");
  }
}
