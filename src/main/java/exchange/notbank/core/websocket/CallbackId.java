package exchange.notbank.core.websocket;

import java.util.Optional;

public class CallbackId {
  private final String eventName;
  private final Optional<Integer> firstId;
  private final Optional<Integer> secondId;

  public CallbackId(String eventName, Optional<Integer> firstId, Optional<Integer> secondId) {
    this.eventName = eventName;
    this.firstId = firstId;
    this.secondId = secondId;
  }

  public static class Factory {
    public static CallbackId create(String eventName, Integer firstId, Integer secondId) {
      return new CallbackId(eventName, Optional.ofNullable(firstId), Optional.ofNullable(secondId));
    }

    public static CallbackId create(String eventName, Integer firstId) {
      return new CallbackId(eventName, Optional.ofNullable(firstId), Optional.empty());
    }

    public static CallbackId create(String eventName) {
      return new CallbackId(eventName, Optional.empty(), Optional.empty());
    }
  }

  @Override
  public int hashCode() {
    var hashCode = eventName.hashCode();
    if (firstId.isPresent()) {
      hashCode += firstId.get().hashCode();
    }
    if (secondId.isPresent()) {
      hashCode += secondId.get().hashCode();
    }
    return hashCode;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof CallbackId)) {
      return false;
    }
    var otherCallbackId = (CallbackId) obj;
    return eventName.equals(otherCallbackId.eventName)
        && firstId.equals(otherCallbackId.firstId)
        && secondId.equals(otherCallbackId.secondId);
  }

  @Override
  public String toString() {
    return "CallbackId [eventName=" + eventName + ", firstId=" + firstId.orElse(0) + ", secondId=" + secondId.orElse(0)
        + "]";
  }
}
