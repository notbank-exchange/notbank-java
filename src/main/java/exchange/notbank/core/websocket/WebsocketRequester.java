package exchange.notbank.core.websocket;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import exchange.notbank.core.NotbankException;
import exchange.notbank.core.constants.MessageType;
import exchange.notbank.core.responses.MessageFrame;
import com.squareup.moshi.JsonAdapter;

import io.vavr.control.Either;

public class WebsocketRequester {
  private final Consumer<String> webSocketSendFn;
  private final CallbackManager callbackManager;
  private final PayloadGetter payloadGetter;
  private final JsonAdapter<MessageFrame> messageFrameJsonAdapter;

  public WebsocketRequester(
      Consumer<String> webSocketSendFn,
      CallbackManager callbackManager,
      PayloadGetter payloadGetter,
      JsonAdapter<MessageFrame> messageFrameJsonAdapter) {
    this.webSocketSendFn = webSocketSendFn;
    this.callbackManager = callbackManager;
    this.payloadGetter = payloadGetter;
    this.messageFrameJsonAdapter = messageFrameJsonAdapter;
  }

  public static class Factory {
    public static WebsocketRequester create(
        Consumer<String> webSocketSendFn,
        CallbackManager callbackManager,
        PayloadGetter payloadGetter,
        JsonAdapter<MessageFrame> messageFrameJsonAdapter) {
      return new WebsocketRequester(webSocketSendFn, callbackManager, payloadGetter, messageFrameJsonAdapter);
    }
  }

  public <T> CompletableFuture<Either<NotbankException, String>> subscribe(
      String endpoint,
      String message,
      List<SubscriptionHandler<T>> subscriptionshandlers,
      Function<SubscriptionHandler<T>, CallbackId> callbackIdGetter,
      Function<SubscriptionCallbacks, BiConsumer<CallbackId, Consumer<T>>> callbackAdder) {
    subscriptionshandlers.stream()
        .forEach(subscriptionHandler -> callbackAdder.apply(callbackManager.subscriptionCallbacks).accept(
            callbackIdGetter.apply(subscriptionHandler),
            subscriptionHandler.eventHandler));
    return requestToFuture(endpoint, message, MessageType.REQUEST);
  }

  public CompletableFuture<Either<NotbankException, Void>> unsubscribe(String endpoint, String message,
      List<Consumer<SubscriptionCallbacks>> removeCallbacks) {
    removeCallbacks.stream().forEach(removeCallback -> removeCallback.accept(callbackManager.subscriptionCallbacks));
    return requestToFuture(endpoint, message, MessageType.REQUEST).thenApply(result -> result.map(o -> null));
  }

  public void setRequestHandlers(
      List<RequestHandler> requestHandlers,
      String subscriptionEndpoint,
      Function<RequestHandler, CallbackId> callbackIdGetter,
      Function<SubscriptionCallbacks, BiConsumer<CallbackId, Consumer<MessageFrame>>> callbackAdder) {
    requestHandlers.stream().forEach(requestHandler -> {
      callbackAdder.apply(callbackManager.subscriptionCallbacks).accept(
          callbackIdGetter.apply(requestHandler),
          getReplayFn(requestHandler));
    });
  }

  private Consumer<MessageFrame> getReplayFn(RequestHandler requestHandler) {
    return messageFrame -> {
      var response = requestHandler.eventHandler.apply(payloadGetter.get(messageFrame));
      if (response.isEmpty()) {
        return;
      }
      var responseMessageFrame = new MessageFrame(
          MessageType.REPLY,
          messageFrame.sequenceNumber,
          messageFrame.functionName,
          response.get());
      webSocketSendFn.accept(messageFrameJsonAdapter.toJson(responseMessageFrame));
    };
  }

  public CompletableFuture<Either<NotbankException, String>> request(String endpoint, String message) {
    return requestToFuture(endpoint, message, MessageType.REQUEST);
  }

  private CompletableFuture<Either<NotbankException, String>> requestToFuture(
      String endpoint, String message, MessageType messageType) {
    var future = new CompletableFuture<Either<NotbankException, String>>();
    var sequenceNumber = callbackManager.sequencedCallbacks.put(handleRequestResponse(future));
    var messageFrame = new MessageFrame(messageType, sequenceNumber, endpoint, message);
    var messageFrameStr = messageFrameJsonAdapter.toJson(messageFrame);
    this.webSocketSendFn.accept(messageFrameStr);
    return future;
  }

  private Consumer<MessageFrame> handleRequestResponse(CompletableFuture<Either<NotbankException, String>> future) {
    return messageFrame -> future.complete(payloadGetter.get(messageFrame));
  }
}
