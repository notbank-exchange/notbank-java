package exchange.notbank.core.websocket;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocketListener;

public class WebSocketThreadCreator {
  public static Thread create(String host, WebSocketListener webSocketListener) {
    return new Thread(() -> {
      var client = new OkHttpClient.Builder().readTimeout(10, TimeUnit.SECONDS).build();
      var request = new Request.Builder().url(buildUrl(host)).build();
      client.newWebSocket(request, webSocketListener);
    });
  }

  private static String buildUrl(String host) {
    return "wss://" + host + "/wsgateway/";
  }

}
