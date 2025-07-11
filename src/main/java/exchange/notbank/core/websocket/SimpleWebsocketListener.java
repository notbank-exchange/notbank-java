package exchange.notbank.core.websocket;

import java.util.function.Consumer;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class SimpleWebsocketListener extends WebSocketListener {
  private final Consumer<String> messageHandler;
  private final Consumer<WebSocket> openCallback;
  private final Consumer<Throwable> onFailuerCallback;
  private final Consumer<String> peekMessageIn;

  public SimpleWebsocketListener(
      Consumer<String> messageHandler,
      Consumer<WebSocket> openCallback,
      Consumer<Throwable> onFailuerCallback,
      Consumer<String> peekMessageIn) {
    this.messageHandler = messageHandler;
    this.openCallback = openCallback;
    this.onFailuerCallback = onFailuerCallback;
    this.peekMessageIn = peekMessageIn;
  }

  @Override
  public void onMessage(WebSocket webSocket, String text) {
    super.onMessage(webSocket, text);
    peekMessageIn.accept(text);
    messageHandler.accept(text);
  }

  @Override
  public void onOpen(WebSocket webSocket, Response response) {
    super.onOpen(webSocket, response);
    openCallback.accept(webSocket);
  }

  @Override
  public void onFailure(WebSocket webSocket, Throwable t, Response response) {
    super.onFailure(webSocket, t, response);
    onFailuerCallback.accept(t);
  }

}
