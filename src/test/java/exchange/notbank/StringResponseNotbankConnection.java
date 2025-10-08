package exchange.notbank;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import exchange.notbank.core.AuthenticateUserParamBuilder;
import exchange.notbank.core.CompletableFutureAdapter;
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

public class StringResponseNotbankConnection implements NotbankConnection {
  private String jsonResponse;

  public StringResponseNotbankConnection() {
    this.jsonResponse = "";
  }

  public void setJsonResponse(String jsonResponse) {
    this.jsonResponse = jsonResponse;
  }

  @Override
  public void close() throws Exception {
    return;
  }

  private <T> CompletableFuture<T> getDeserializedJson(Function<String, Either<NotbankException, T>> deserializeFn) {
    var deserializedJson = deserializeFn.apply(jsonResponse);
    return CompletableFutureAdapter.fromEither(CompletableFuture.completedFuture(deserializedJson));
  }

  @Override
  public <T> CompletableFuture<T> requestGet(EndpointCategory endpointCategory, String endpoint,
      ParamBuilder paramBuilder, Function<String, Either<NotbankException, T>> deserializeFn) {
    return getDeserializedJson(deserializeFn);
  }

  @Override
  public <T> CompletableFuture<T> requestPost(EndpointCategory endpointCategory, String endpoint,
      ParamBuilder paramBuilder, Function<String, Either<NotbankException, T>> deserializeFn) {
    return getDeserializedJson(deserializeFn);
  }

  @Override
  public <T> CompletableFuture<T> requestPostByText(EndpointCategory endpointCategory, String endpoint, String text,
      HttpConfiguration httpConfiguration, Function<String, Either<NotbankException, T>> deserializeFn) {
    return getDeserializedJson(deserializeFn);
  }

  @Override
  public <T> CompletableFuture<T> requestPost(EndpointCategory endpointCategory, String endpoint,
      ParamListBuilder paramBuilder, Function<String, Either<NotbankException, T>> deserializeFn) {
    return getDeserializedJson(deserializeFn);
  }

  @Override
  public <T> CompletableFuture<T> requestDelete(EndpointCategory endpointCategory, String endpoint,
      ParamBuilder paramBuilder, Function<String, Either<NotbankException, T>> deserializeFn) {
    return getDeserializedJson(deserializeFn);
  }

  @Override
  public CompletableFuture<Either<NotbankException, String>> subscribe(SubscriptionData subscriptionData) {
    return CompletableFuture.completedFuture(Either.right(null));
  }

  @Override
  public CompletableFuture<Either<NotbankException, Void>> unsubscribe(String endpoint, ParamBuilder paramBuilder,
      List<SubscriptionId> removeCallbacks) {
    return CompletableFuture.completedFuture(Either.right(null));
  }

  @Override
  public CompletableFuture<Void> authenticateUser(AuthenticateUserParamBuilder paramBuilder) {
    return CompletableFuture.completedFuture(null);
  }

  @Override
  public CompletableFuture<WebAuthenticateResponse> webAuthenticateUser(WebAuthenticateUserParamBuilder paramBuilder) {
    return CompletableFuture.completedFuture(new WebAuthenticateResponse(null, null, null, null, null, null, null));
  }

  @Override
  public CompletableFuture<Void> logOut() {
    return CompletableFuture.completedFuture(null);
  }

  @Override
  public CompletableFuture<Void> ping() {
    return CompletableFuture.completedFuture(null);
  }

}
