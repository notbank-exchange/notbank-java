package exchange.notbank.trading.constants;

import com.squareup.moshi.Json;

public enum Direction {
  @Json(name = "NoChange")
  NO_CHANGE,
  @Json(name = "Uptick")
  UP_TICK,
  @Json(name = "DownTick")
  DOWN_TICK;

}
