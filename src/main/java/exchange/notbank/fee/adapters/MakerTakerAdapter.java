package exchange.notbank.fee.adapters;

import exchange.notbank.fee.constants.MakerTaker;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

public class MakerTakerAdapter {
  @ToJson
  int toJson(MakerTaker value) {
    return value.value;
  }

  @FromJson
  MakerTaker fromJson(int value) {
    return MakerTaker.values()[value];
  }
}
