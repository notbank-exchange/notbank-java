package exchange.notbank.fee.adapters;

import exchange.notbank.fee.constants.IntOrderType;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

public class IntOrderTypeAdapter {
  @ToJson
  int toJson(IntOrderType value) {
    return value.value;
  }

  @FromJson
  IntOrderType fromJson(int value) {
    return IntOrderType.values()[value];
  }
}
