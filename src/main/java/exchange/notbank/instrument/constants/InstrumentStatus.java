package exchange.notbank.instrument.constants;

import com.squareup.moshi.Json;

public enum InstrumentStatus {
  @Json(name = "Unknown")
  UNKNOWN,
  @Json(name = "Running")
  RUNNING,
  @Json(name = "Paused")
  PAUSED,
  @Json(name = "Stopped")
  STOPPED,
  @Json(name = "Starting")
  STARTING,
  @Json(name = "RunningPostOnly")
  RUNNING_POST_ONLY;
}
