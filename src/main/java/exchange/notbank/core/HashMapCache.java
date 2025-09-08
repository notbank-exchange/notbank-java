package exchange.notbank.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class HashMapCache<T> {
  public final Map<String, T> values;

  public HashMapCache(Map<String, T> values) {
    this.values = values;
  }

  public static class Factory {
    public static <T> HashMapCache<T> create() {
      return new HashMapCache<>(new HashMap<>());
    }
  }

  public void update(List<T> newValues, Function<T, String> getKey) {
    newValues.stream().forEach(value -> values.put(getKey.apply(value), value));
  }

  public Optional<T> get(String key) {
    return Optional.ofNullable(values.get(key));
  }
}
