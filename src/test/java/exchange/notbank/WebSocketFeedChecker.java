package exchange.notbank;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class WebSocketFeedChecker {
  private Optional<String> error = Optional.empty();

  public <T> Consumer<T> checkInstrumentId(Function<T, Integer> getInstrumentIdFn, Integer expectedInstrumentId) {
    return (T t) -> {
      Integer instrumentId = getInstrumentIdFn.apply(t);
      if (instrumentId == null || !instrumentId.equals(expectedInstrumentId)) {
        error = Optional.of("Expected instrument id is " + expectedInstrumentId + " but got " + instrumentId);
      }
    };
  }

  public void assertNoError() {
    assertTrue(error.isEmpty(), () -> error.get().toString());
  }
}
