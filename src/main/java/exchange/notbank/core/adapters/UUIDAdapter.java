package exchange.notbank.core.adapters;

import java.util.UUID;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

public class UUIDAdapter {
  @ToJson
  String toJson(UUID value) {
    return value.toString();
  }

  @FromJson
  UUID fromJson(String value) {
    return UUID.fromString(value);
  }
}
