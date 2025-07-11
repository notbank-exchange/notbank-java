package exchange.notbank.core.websocket;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;

import exchange.notbank.core.MoshiFactory;
import exchange.notbank.core.NotbankConnection;
import exchange.notbank.core.WebAuthenticateResponse;
import exchange.notbank.core.responses.MessageFrame;
import exchange.notbank.core.responses.StandardResponse;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Types;

import okhttp3.WebSocket;

class WebsocketNotbankConnectionFactory {

  public static CompletableFuture<WebsocketNotbankConnection> create(
      String host,
      Function<NotbankConnection, CompletableFuture<NotbankConnection>> notbankConnectionInterceptor,
      Consumer<Throwable> onError,
      Consumer<String> peekMessageIn,
      Consumer<String> peekMessageOut) {
    var moshi = MoshiFactory.create();
    var standardResponseJsonAdapter = moshi.adapter(StandardResponse.class);
    var messageFrameJsonAdapter = moshi.adapter(MessageFrame.class);
    var webAuthenticateResponseJsonAdapter = moshi.adapter(WebAuthenticateResponse.class);
    Type MapStringObjectType = Types.newParameterizedType(Map.class, String.class, Object.class);
    Type ListOfMapStringObjectType = Types.newParameterizedType(List.class, MapStringObjectType);
    JsonAdapter<Map<String, Object>> mapStringObjectJsonAdapter = moshi.adapter(MapStringObjectType);
    JsonAdapter<List<Map<String, Object>>> listOfMapStringObjectJsonAdapter = moshi.adapter(ListOfMapStringObjectType);
    var callbackManager = CallbackManager.Factory.create(moshi);
    var messageFrameAdapter = new MessageFrameAdapter(messageFrameJsonAdapter, standardResponseJsonAdapter);
    var payloadGetter = new PayloadGetter(standardResponseJsonAdapter);
    var websocketResponseHandler = new WebsocketResponseHandler(
        Executors.newSingleThreadExecutor(),
        callbackManager,
        messageFrameAdapter,
        standardResponseJsonAdapter,
        onError);
    var webSocketFuture = getWebSocketAndAttachHandler(websocketResponseHandler, host, onError,
        peekMessageIn);
    return webSocketFuture.thenApply(webSocket -> {
      var websocketRequester = WebsocketRequester.Factory.create(
          messageFrameStr -> {
            peekMessageOut.accept(messageFrameStr);
            webSocket.send(messageFrameStr);
          },
          callbackManager,
          payloadGetter,
          messageFrameJsonAdapter);
      var connection = new WebsocketNotbankConnection(
          webSocket,
          websocketRequester,
          websocketResponseHandler,
          mapStringObjectJsonAdapter,
          listOfMapStringObjectJsonAdapter,
          webAuthenticateResponseJsonAdapter,
          onError);
      return connection;
    });
  }

  private static CompletableFuture<WebSocket> getWebSocketAndAttachHandler(
      WebsocketResponseHandler websocketResponseHandler,
      String host,
      Consumer<Throwable> onError,
      Consumer<String> peekMessageIn) {

    var connectedFuture = new CompletableFuture<WebSocket>();
    var simpleWebsocketListener = new SimpleWebsocketListener(
        websocketResponseHandler::handle,
        webSocket -> connectedFuture.complete(webSocket),
        onError,
        peekMessageIn);
    var thread = WebSocketThreadCreator.create(host, simpleWebsocketListener);
    thread.start();
    return connectedFuture;
  }
}
