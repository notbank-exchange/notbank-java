package exchange.notbank.trading.constants;

import com.squareup.moshi.Json;

public enum OrderState {
  @Json(name = "Unknown")
  UNKNOWN,
  @Json(name = "Working")
  WORKING,
  @Json(name = "Rejected")
  REJECTED,
  @Json(name = "Canceled")
  CANCELED,
  @Json(name = "Expired")
  EXPIRED,
  @Json(name = "FullyExecuted")
  FULLY_EXECUTED;
}
