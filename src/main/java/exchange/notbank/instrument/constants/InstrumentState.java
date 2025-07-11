package exchange.notbank.instrument.constants;

import com.squareup.moshi.Json;

public enum InstrumentState {
  @Json(name = "Both")
  BOTH,
  @Json(name = "Inactive")
  INACTIVE;
}
