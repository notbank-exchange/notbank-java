package exchange.notbank.core.websocket;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import exchange.notbank.core.NotbankConnection;
import exchange.notbank.core.AuthenticateUserParamBuilder;
import exchange.notbank.core.CompletableFutureAdapter;
import exchange.notbank.core.EndpointCategory;
import exchange.notbank.core.JsonDeserializer;
import exchange.notbank.core.NotbankException;
import exchange.notbank.core.ParamBuilder;
import exchange.notbank.core.ParamListBuilder;
import exchange.notbank.core.StandardResponseAdapter;
import exchange.notbank.core.WebAuthenticateResponse;
import exchange.notbank.core.WebAuthenticateUserParamBuilder;
import exchange.notbank.core.responses.MessageFrame;
import com.squareup.moshi.JsonAdapter;

import io.vavr.control.Either;
import okhttp3.WebSocket;

public class WebsocketNotbankConnection implements NotbankConnection {
  private static final String AUTHENTICATE_USER = "AuthenticateUser";
  private static final String WEB_AUTHENTICATE_USER = "WebAuthenticateUser";
  private static final String LOG_OUT = "Logout";
  private static final String PING = "Ping";
  private final WebSocket webSocket;
  private final WebsocketRequester websocketRequester;
  private final WebsocketResponseHandler websocketResponseHandler;
  private final JsonAdapter<Map<String, Object>> mapJsonAdapter;
  private final JsonAdapter<List<Map<String, Object>>> listJsonAdapter;
  private final JsonAdapter<WebAuthenticateResponse> webAuthenticateResponseJsonAdapter;
  private final Consumer<Throwable> onError;

  public WebsocketNotbankConnection(
      WebSocket webSocket,
      WebsocketRequester websocketRequester,
      WebsocketResponseHandler websocketResponseHandler,
      JsonAdapter<Map<String, Object>> mapJsonAdapter,
      JsonAdapter<List<Map<String, Object>>> listJsonAdapter,
      JsonAdapter<WebAuthenticateResponse> webAuthenticateResponseJsonAdapter,
      Consumer<Throwable> onError) {
    this.webSocket = webSocket;
    this.websocketRequester = websocketRequester;
    this.websocketResponseHandler = websocketResponseHandler;
    this.mapJsonAdapter = mapJsonAdapter;
    this.listJsonAdapter = listJsonAdapter;
    this.webAuthenticateResponseJsonAdapter = webAuthenticateResponseJsonAdapter;
    this.onError = onError;
  }

  public static class Factory extends WebsocketNotbankConnectionFactory {
  }

  @Override
  public <T> CompletableFuture<T> requestGet(EndpointCategory endpointCategory, String endpoint,
      ParamBuilder paramBuilder,
      Function<String, Either<NotbankException, T>> deserializationFn) {
    var future = websocketRequester.request(endpoint, mapJsonAdapter.toJson(paramBuilder.getParams()))
        .thenApply(jsonStr -> JsonDeserializer.deserialize(jsonStr, deserializationFn));
    return CompletableFutureAdapter.fromEither(future);
  }

  @Override
  public <T> CompletableFuture<T> requestPost(EndpointCategory endpointCategory, String endpoint,
      ParamBuilder paramBuilder,
      Function<String, Either<NotbankException, T>> deserializationFn) {
    var future = websocketRequester.request(endpoint, mapJsonAdapter.toJson(paramBuilder.getParams()))
        .thenApply(jsonStr -> JsonDeserializer.deserialize(jsonStr, deserializationFn));
    return CompletableFutureAdapter.fromEither(future);
  }

  @Override
  public <T> CompletableFuture<T> requestPost(EndpointCategory endpointCategory, String endpoint,
      ParamListBuilder paramBuilder,
      Function<String, Either<NotbankException, T>> deserializationFn) {
    var future = websocketRequester.request(endpoint, listJsonAdapter.toJson(paramBuilder.getParams()))
        .thenApply(jsonStr -> JsonDeserializer.deserialize(jsonStr, deserializationFn));
    return CompletableFutureAdapter.fromEither(future);
  }

  @Override
  public <T> CompletableFuture<Either<NotbankException, String>> subscribe(
      String endpoint,
      ParamBuilder paramBuilder,
      Function<Consumer<Throwable>, List<SubscriptionHandler<T>>> subscriptionHandlers,
      Function<SubscriptionHandler<T>, CallbackId> callbackIdGetter,
      Function<SubscriptionCallbacks, BiConsumer<CallbackId, Consumer<T>>> callbackAdder) {
    return websocketRequester.subscribe(endpoint, mapJsonAdapter.toJson(paramBuilder.getParams()),
        subscriptionHandlers.apply(onError), callbackIdGetter, callbackAdder);
  }

  @Override
  public CompletableFuture<Either<NotbankException, Void>> unsubscribe(String endpoint, ParamBuilder paramBuilder,
      List<Consumer<SubscriptionCallbacks>> removeCallbacks) {
    return websocketRequester.unsubscribe(endpoint, mapJsonAdapter.toJson(paramBuilder.getParams()), removeCallbacks);
  }

  @Override
  public void setRequestResponses(
      List<RequestHandler> requestHandlers,
      String subscriptionEndpoint,
      Function<RequestHandler, CallbackId> callbackIdGetter,
      Function<SubscriptionCallbacks, BiConsumer<CallbackId, Consumer<MessageFrame>>> callbackAdder) {
    websocketRequester.setRequestHandlers(requestHandlers, subscriptionEndpoint, callbackIdGetter, callbackAdder);
  }

  @Override
  public CompletableFuture<Void> authenticateUser(AuthenticateUserParamBuilder paramBuilder) {
    Map<String, Object> params = Map.of(
        "APIKey", paramBuilder.apiKey,
        "Signature", paramBuilder.signature,
        "UserId", paramBuilder.userId,
        "Nonce", paramBuilder.nonce);
    return websocketRequester.request(AUTHENTICATE_USER, mapJsonAdapter.toJson(params))
        .thenAccept(x -> {
        });

  }

  @Override
  public CompletableFuture<WebAuthenticateResponse> webAuthenticateUser(WebAuthenticateUserParamBuilder paramBuilder) {
    return requestPost(EndpointCategory.AP, WEB_AUTHENTICATE_USER, paramBuilder,
        jsonStr -> JsonDeserializer.deserialize(jsonStr, webAuthenticateResponseJsonAdapter));
  }

  /**
   * https://apidoc.notbank.exchange/#logout
   */
  @Override
  public CompletableFuture<Void> logOut() {
    return requestPost(EndpointCategory.AP, LOG_OUT, ParamBuilder.EMPTY, StandardResponseAdapter::toNull);
  }

  /**
   * https://apidoc.notbank.exchange/#ping
   */
  @Override
  public CompletableFuture<Void> ping() {
    return requestPost(EndpointCategory.AP, PING, ParamBuilder.EMPTY, StandardResponseAdapter::toNull);
  }

  @Override
  public void close() throws Exception {
    websocketResponseHandler.close();
    webSocket.cancel();
  }

  @Override
  public <T> CompletableFuture<T> requestDelete(EndpointCategory endpointCategory, String endpoint,
      ParamBuilder paramBuilder, Function<String, Either<NotbankException, T>> deserializeFn) {
    throw new UnsupportedOperationException("Unsupported delete operation for websocket communication");
  }
}
