package exchange.notbank;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class WebSocketFeedChecker {
  private Optional<String> error = Optional.empty();

  public <T> Function<Integer, Consumer<T>> getHasInstrumentIdChecker(Function<T, Integer> idExtractor) {
    return instrumentId -> checkInstrumentId(idExtractor, instrumentId);
  }

  public <T> Function<Integer, Consumer<List<T>>> getHasInstrumentIdCheckerFromList(Function<T, Integer> idExtractor) {
    return instrumentId -> checkInstrumentId(getInstrumentId(idExtractor), instrumentId);
  }

  private <T> Function<List<T>, Integer> getInstrumentId(Function<T, Integer> idExtractor) {
    return list -> list.isEmpty() ? -1 : idExtractor.apply(list.get(0));
  }

  public <T> Consumer<T> checkInstrumentId(Function<T, Integer> getInstrumentIdFn, Integer expectedInstrumentId) {
    return (T t) -> {
      System.out.println(t);
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
