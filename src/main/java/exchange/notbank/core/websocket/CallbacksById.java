package exchange.notbank.core.websocket;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;

import exchange.notbank.core.NotbankException;
import exchange.notbank.core.responses.MessageFrame;

import io.vavr.Function2;
import io.vavr.control.Either;

public class CallbacksById<EventType> {
  private final ConcurrentHashMap<CallbackId, Consumer<EventType>> callbacks;
  private final Function2<String, EventType, CallbackId> callbackIdGetter;
  private final Function<MessageFrame, Either<NotbankException, EventType>> eventDeserializer;

  public CallbacksById(ConcurrentHashMap<CallbackId, Consumer<EventType>> callbacks,
      Function2<String, EventType, CallbackId> callbackIdGetter,
      Function<MessageFrame, Either<NotbankException, EventType>> eventDeserializer) {
    this.callbacks = callbacks;
    this.callbackIdGetter = callbackIdGetter;
    this.eventDeserializer = eventDeserializer;
  }

  public static class Factory {
    public static <EventType> CallbacksById<EventType> create(
        Function2<String, EventType, CallbackId> callbackIdGetter,
        Function<MessageFrame, Either<NotbankException, EventType>> eventDeserializer) {
      return new CallbacksById<>(new ConcurrentHashMap<>(), callbackIdGetter, eventDeserializer);
    }
  }

  public void add(CallbackId callbackId, Consumer<EventType> handler) {
    callbacks.put(callbackId, handler);
  }

  public Either<NotbankException, Optional<Runnable>> getRunnable(MessageFrame messageFrame) {
    var event = eventDeserializer.apply(messageFrame);
    if (event.isLeft()) {
      return Either.left(event.getLeft());
    }
    var callbackId = callbackIdGetter.apply(messageFrame.functionName, event.get());
    var callback = Optional.ofNullable(callbacks.get(callbackId));
    if (callback.isEmpty()) {
      return Either.right(Optional.empty());
    }
    Runnable runnable = () -> callback.get().accept(event.get());
    return Either.right(Optional.of(runnable));
  }

  public void remove(CallbackId callbackId) {
    callbacks.remove(callbackId);
  }
}
