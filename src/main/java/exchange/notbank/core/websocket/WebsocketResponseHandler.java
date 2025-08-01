package exchange.notbank.core.websocket;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

import exchange.notbank.core.constants.MessageType;
import exchange.notbank.core.responses.MessageFrame;
import exchange.notbank.core.responses.StandardResponse;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonDataException;

public class WebsocketResponseHandler implements AutoCloseable {
  private final ExecutorService executorService;
  private final CallbackManager callbackManager;
  private final MessageFrameAdapter messageFrameAdapter;
  private final JsonAdapter<StandardResponse> standardResponseJsonAdapter;
  private final Consumer<Throwable> onError;

  public WebsocketResponseHandler(ExecutorService executorService, CallbackManager callbackManager,
      MessageFrameAdapter messageFrameAdapter,
      JsonAdapter<StandardResponse> standardResponseJsonAdapter, Consumer<Throwable> onError) {
    this.executorService = executorService;
    this.callbackManager = callbackManager;
    this.messageFrameAdapter = messageFrameAdapter;
    this.standardResponseJsonAdapter = standardResponseJsonAdapter;
    this.onError = onError;
  }

  public void handle(String message) {
    var messageFrame = messageFrameAdapter.fromJson(message);
    if (messageFrame.isLeft()) {
      executorService.submit(() -> onError.accept(messageFrame.getLeft()));
      return;
    }
    var callback = callbackManager.sequencedCallbacks.pop(messageFrame.get().sequenceNumber);
    if (callback.isPresent()) {
      executorService.submit(() -> callback.get().accept(messageFrame.get()));
      if (isErrorMessage(messageFrame.get())) {
        return;
      }
      // it may exists a subcription callback for the function name at the same time the sequence callback exists, so we continue with function even if callback was called
    }
    var subscriptionRunnable = callbackManager.subscriptionCallbacks.getRunnable(messageFrame.get());
    if (subscriptionRunnable.isLeft()) {
      executorService.submit(() -> onError.accept(subscriptionRunnable.getLeft()));
      return;
    }
    if (subscriptionRunnable.get().isPresent()) {
      executorService.submit(subscriptionRunnable.get().get());
    }
  }

  private Boolean isErrorMessage(MessageFrame messageFrame) {
    var isErrorType = messageFrame.messageType.equals(MessageType.ERROR);
    if (isErrorType) {
      return true;
    }
    try {
      var standardError = standardResponseJsonAdapter.fromJson(messageFrame.payload);
      return standardError.result != null && standardError.result == false;
    } catch (IOException | JsonDataException e) {
      return false;
    }
  }

  @Override
  public void close() throws Exception {
    executorService.shutdown();
  }
}
