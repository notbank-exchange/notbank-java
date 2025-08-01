package exchange.notbank.wallet.adapters;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import exchange.notbank.wallet.constants.Direction;

public class DirectionAdapter {
  @ToJson
  int toJson(Direction value) {
    return value.value;
  }

  @FromJson
  Direction fromJson(int value) {
    return Direction.values()[value];
  }
}
