package exchange.notbank.core.rest;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Redirect;
import java.time.Duration;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.squareup.moshi.Moshi;

class HttpClientFactory {
  public static HttpClient newHttpClient(
      String host,
      Moshi moshi,
      BiConsumer<HttpRequest, String> peekHttpRequest,
      Consumer<HttpResponse<String>> peekHttpResponse) {
    var client = java.net.http.HttpClient.newBuilder().build();
    return new HttpClient(host, client, moshi, peekHttpRequest,
        peekHttpResponse);
  }

  public static HttpClient newHttpClient(
      String host,
      Moshi moshi,
      Duration requestTimeoutInMillis,
      BiConsumer<HttpRequest, String> peekHttpRequest,
      Consumer<HttpResponse<String>> peekHttpResponse) {
    var client = java.net.http.HttpClient.newBuilder().followRedirects(Redirect.NORMAL).build();
    return new HttpClient(host, client, moshi, requestTimeoutInMillis, peekHttpRequest,
        peekHttpResponse);
  }
}
