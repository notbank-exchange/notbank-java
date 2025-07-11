package exchange.notbank.core.websocket;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class SequencedCallbacks<T> {
  private final ConcurrentHashMap<Long, Consumer<T>> callbacks;
  private Long sequenceNumber;

  public SequencedCallbacks(ConcurrentHashMap<Long, Consumer<T>> callbacks, Long sequenceNumber) {
    this.callbacks = callbacks;
    this.sequenceNumber = sequenceNumber;
  }

  public static class Factory {
    public static <T> SequencedCallbacks<T> create() {
      return new SequencedCallbacks<>(new ConcurrentHashMap<>(), 2L);
    }
  }

  private Long getNextSequenceNumber() {
    var currentSequenceNumber = this.sequenceNumber;
    this.sequenceNumber += 2;
    return currentSequenceNumber;
  }

  public Long put(Consumer<T> callback) {
    var currentSequenceNumber = getNextSequenceNumber();
    this.callbacks.put(currentSequenceNumber, callback);
    return currentSequenceNumber;
  }

  public Optional<Consumer<T>> pop(Long sequenceNumber) {
    var callback = Optional.ofNullable(callbacks.get(sequenceNumber));
    if (callback.isPresent()) {
      callbacks.remove(sequenceNumber);
    }
    return callback;
  }
}
