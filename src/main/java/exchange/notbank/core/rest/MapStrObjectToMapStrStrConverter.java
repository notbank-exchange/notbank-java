package exchange.notbank.core.rest;

import java.util.Map;
import java.util.stream.Collectors;

public class MapStrObjectToMapStrStrConverter {
  public static Map<String, String> convert(Map<String, Object> params) {
    return params.entrySet().stream().map(entry -> {
      return Map.entry(entry.getKey(), entry.getValue().toString());
    }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }
}
