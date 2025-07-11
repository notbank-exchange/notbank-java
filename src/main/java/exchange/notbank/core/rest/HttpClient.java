package exchange.notbank.core.rest;

import java.lang.reflect.ParameterizedType;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import exchange.notbank.core.EndpointCategory;
import exchange.notbank.core.NotbankException;
import exchange.notbank.core.constants.HttpMethod;
import io.vavr.control.Either;

public class HttpClient implements AutoCloseable {
  private final String host;
  private final java.net.http.HttpClient client;
  private final Optional<Duration> optRequestTimeoutInMillis;
  private final JsonAdapter<Map<String, Object>> mapJsonAdapter;
  private final JsonAdapter<List<String>> mapListJsonAdapter;
  private final BiConsumer<HttpRequest, String> peekHttpRequest;
  private final Consumer<HttpResponse<String>> peekJsonResponse;

  public HttpClient(
      String host,
      java.net.http.HttpClient client,
      Moshi moshi,
      Duration requestTimeoutInMillis,
      BiConsumer<HttpRequest, String> peekHttpRequest,
      Consumer<HttpResponse<String>> peekHttpResponse) {
    this.host = host;
    this.client = client;
    ParameterizedType mapType = Types.newParameterizedType(Map.class, String.class, Object.class);
    ParameterizedType mapListType = Types.newParameterizedType(List.class, mapType);
    this.mapJsonAdapter = moshi.adapter(mapType);
    this.mapListJsonAdapter = moshi.adapter(mapListType);
    this.optRequestTimeoutInMillis = Optional.of(requestTimeoutInMillis);
    this.peekHttpRequest = peekHttpRequest;
    this.peekJsonResponse = peekHttpResponse;
  }

  public HttpClient(
      String host,
      java.net.http.HttpClient client,
      Moshi moshi,
      BiConsumer<HttpRequest, String> peekHttpRequest,
      Consumer<HttpResponse<String>> peekHttpResponse) {
    this.host = host;
    this.client = client;
    ParameterizedType mapType = Types.newParameterizedType(Map.class, String.class, Object.class);
    ParameterizedType mapListType = Types.newParameterizedType(List.class, String.class);
    this.mapJsonAdapter = moshi.adapter(mapType);
    this.mapListJsonAdapter = moshi.adapter(mapListType);
    this.optRequestTimeoutInMillis = Optional.empty();
    this.peekHttpRequest = peekHttpRequest;
    this.peekJsonResponse = peekHttpResponse;
  }

  public CompletableFuture<Either<NotbankException, String>> get(
      EndpointCategory endpointCategory,
      String endpoint,
      Function<Builder, Builder> customizeRequestBuilderFn) {
    var request = newRequest(endpointCategory.getValue() + endpoint, HttpMethod.GET, customizeRequestBuilderFn);
    return doRequest(endpointCategory, request, "<no body>");
  }

  public CompletableFuture<Either<NotbankException, String>> get(
      EndpointCategory endpointCategory,
      String endpoint,
      Map<String, String> params,
      Function<Builder, Builder> customizeRequestBuilderFn) {
    String endpointWithParams = URLEncoder.asUrl(endpointCategory.getValue() + endpoint, params);
    var request = newRequest(endpointWithParams, HttpMethod.GET, customizeRequestBuilderFn);
    return doRequest(endpointCategory, request, "<no body>");
  }

  public CompletableFuture<Either<NotbankException, String>> postList(
      EndpointCategory endpointCategory,
      String endpoint,
      List<Map<String, Object>> params,
      Function<Builder, Builder> customizeRequestBuilderFn) {
    var cleanList = convertListValuesToString(params);
    var jsonBody = mapListJsonAdapter.toJson(cleanList);
    var request = newJsonEncodedRequestWithParamsAsList(endpointCategory.getValue() + endpoint, HttpMethod.POST, params,
        customizeRequestBuilderFn);
    return doRequest(endpointCategory, request, jsonBody);
  }

  public CompletableFuture<Either<NotbankException, String>> fetch(
      String method,
      EndpointCategory endpointCategory,
      String endpoint,
      Map<String, Object> params,
      Function<Builder, Builder> customizeRequestBuilderFn) {
    var jsonBody = mapJsonAdapter.toJson(params);
    var request = newJsonEncodedRequestWithParams(endpointCategory.getValue() + endpoint, method, jsonBody,
        customizeRequestBuilderFn);
    return doRequest(endpointCategory, request, jsonBody);
  }

  private HttpRequest newRequest(
      String endpoint,
      String method,
      Function<Builder, Builder> customizeRequestBuilderFn) {
    return customizeRequestBuilderFn.apply(withTimeoutIfPresent(HttpRequest.newBuilder()))
        .uri(URI.create(buildUrl(endpoint)))
        .method(method, HttpRequest.BodyPublishers.noBody())
        .build();
  }

  private HttpRequest newJsonEncodedRequestWithParams(
      String endpoint,
      String method,
      String jsonBody,
      Function<Builder, Builder> customizeRequestBuilderFn) {
    return customizeRequestBuilderFn.apply(withTimeoutIfPresent(HttpRequest.newBuilder()))
        .uri(URI.create(buildUrl(endpoint)))
        .method(method, HttpRequest.BodyPublishers.ofString(jsonBody))
        .header("Content-Type", "application/json")
        .header("charset", "utf-8")
        .build();
  }

  private HttpRequest newJsonEncodedRequestWithParamsAsList(
      String endpoint,
      String method,
      List<Map<String, Object>> params,
      Function<Builder, Builder> customizeRequestBuilderFn) {
    var cleanList = convertListValuesToString(params);
    var jsonBody = mapListJsonAdapter.toJson(cleanList);
    return customizeRequestBuilderFn.apply(withTimeoutIfPresent(HttpRequest.newBuilder()))
        .uri(URI.create(buildUrl(endpoint)))
        .method(method, HttpRequest.BodyPublishers.ofString(jsonBody))
        .header("Content-Type", "application/json")
        .header("charset", "utf-8")
        .build();
  }

  private List<String> convertListValuesToString(List<Map<String, Object>> params) {
    return params.stream()
        .map(this.mapJsonAdapter::toJson)
        .toList();
  }

  private Builder withTimeoutIfPresent(Builder builder) {
    if (optRequestTimeoutInMillis.isPresent()) {
      return builder.timeout(optRequestTimeoutInMillis.get());
    }
    return builder;
  }

  private CompletableFuture<Either<NotbankException, String>> doRequest(EndpointCategory endpointCategory,
      HttpRequest request, String jsonBodyForLogs) {
    peekHttpRequest.accept(request, jsonBodyForLogs);
    return client.sendAsync(request, BodyHandlers.ofString())
        .thenApply(response -> handleHttpResponse(endpointCategory, response));
  }

  private Either<NotbankException, String> handleHttpResponse(
      EndpointCategory endpointCategory,
      HttpResponse<String> httpResponse) {
    peekJsonResponse.accept(httpResponse);
    var statusCode = httpResponse.statusCode();
    if (endpointCategory.equals(EndpointCategory.NB)) {
      return Either.right(httpResponse.body());
    }
    if (statusCode >= 400) {
      return Either.left(
          NotbankException.Factory.create(
              NotbankException.ErrorType.HTTP_ERROR,
              "not a successfull response", statusCode, httpResponse.body()));
    }
    var jsonBody = httpResponse.body();
    return Either.right(jsonBody);
  }

  private String buildUrl(String endpoint) {
    return "https://" + host + endpoint;
  }

  public static class Factory extends HttpClientFactory {
  }

  @Override
  public void close() throws Exception {
    // client.close();
  }
}
