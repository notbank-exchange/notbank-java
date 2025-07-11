package exchange.notbank.fee.adapters;

import exchange.notbank.fee.constants.IntSide;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

public class IntSideAdapter {
  @ToJson
  int toJson(IntSide value) {
    return value.value;
  }

  @FromJson
  IntSide fromJson(int value) {
    return IntSide.values()[value];
  }
}
