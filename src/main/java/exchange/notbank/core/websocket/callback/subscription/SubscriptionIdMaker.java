package exchange.notbank.core.websocket.callback.subscription;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import exchange.notbank.core.responses.MessageFrame;
import exchange.notbank.core.websocket.WebsocketJsonAdapters;

public class SubscriptionIdMaker {
  private final Map<String, Function<String, String>> suffixFnByEvent;

  public SubscriptionIdMaker(Map<String, Function<String, String>> suffixFnByEvent) {
    this.suffixFnByEvent = suffixFnByEvent;
  }

  public static class Factory {
    public static SubscriptionIdMaker create(WebsocketJsonAdapters jsonAdapters) {
      var suffixFnByEventBuilder = new SuffixFnByEventBuilder(
          jsonAdapters.objectListAdapter(),
          jsonAdapters.instrumentIdAndAccountIdJsonAdapter());
      return new SubscriptionIdMaker(suffixFnByEventBuilder.build());
    }
  }

  public SubscriptionId get(MessageFrame message) {
    var suffixFn = Optional.ofNullable(suffixFnByEvent.get(message.functionName));
    var idStr = suffixFn.isEmpty()
        ? message.functionName
        : message.functionName + suffixFn.get().apply(message.payload);
    return new SubscriptionId(idStr);
  }

  public static SubscriptionId get(String eventName) {
    var idStr = eventName;
    return new SubscriptionId(idStr);
  }

  public static SubscriptionId get(String eventName, Integer firstIdentifier) {
    var idStr = eventName + Suffixer.toSuffix(firstIdentifier.toString());
    return new SubscriptionId(idStr);
  }

  public static SubscriptionId get(String eventName, Integer firstIdentifier, Integer secondIdentifier) {
    var idStr = eventName
        + Suffixer.toSuffix(firstIdentifier.toString())
        + Suffixer.toSuffix(secondIdentifier.toString());
    return new SubscriptionId(idStr);
  }

  public static SubscriptionId get(String eventName, Optional<Integer> firstIdentifier,
      Optional<Integer> secondIdentifier) {
    var idStr = eventName
        + Suffixer.toSuffix(firstIdentifier.map(Object::toString))
        + Suffixer.toSuffix(secondIdentifier.map(Object::toString));
    return new SubscriptionId(idStr);
  }

}
