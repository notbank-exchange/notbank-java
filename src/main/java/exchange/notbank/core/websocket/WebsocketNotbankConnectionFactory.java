package exchange.notbank.core.websocket;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import exchange.notbank.core.websocket.callback.CallbackManager;
import okhttp3.WebSocket;

class WebsocketNotbankConnectionFactory {
  public static CompletableFuture<WebsocketNotbankConnection> create(
      WebsocketNotbankConnectionConfiguration configuration) {
    var callbackManager = CallbackManager.Factory.create(configuration.jsonAdapters());
    var messageFrameAdapter = new MessageFrameAdapter(
        configuration.jsonAdapters().messageFrameJsonAdapter(),
        configuration.jsonAdapters().standardResponseJsonAdapter());
    var payloadGetter = new PayloadGetter(configuration.jsonAdapters().standardResponseJsonAdapter());
    var websocketResponseHandler = new WebsocketResponseHandler(
        Executors.newSingleThreadExecutor(),
        callbackManager,
        messageFrameAdapter,
        configuration.jsonAdapters().standardResponseJsonAdapter(),
        configuration.onError());
    var webSocketFuture = getWebSocketAndAttachHandler(
        websocketResponseHandler,
        configuration.host(),
        configuration.onError(),
        configuration.peekMessageIn());
    return webSocketFuture.thenApply(webSocket -> {
      var websocketRequester = WebsocketRequester.Factory.create(
          messageFrameStr -> {
            configuration.peekMessageOut().accept(messageFrameStr);
            webSocket.send(messageFrameStr);
          },
          callbackManager,
          payloadGetter,
          configuration.jsonAdapters().messageFrameJsonAdapter());
      var connection = new WebsocketNotbankConnection(
          webSocket,
          websocketRequester,
          websocketResponseHandler,
          configuration.jsonAdapters().mapStringObjectJsonAdapter(),
          configuration.jsonAdapters().listOfMapStringObjectJsonAdapter(),
          configuration.jsonAdapters().webAuthenticateResponseJsonAdapter(),
          err -> configuration.onError().accept(err));
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
