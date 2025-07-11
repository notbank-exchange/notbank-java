package exchange.notbank.trading.constants;

import com.squareup.moshi.Json;

public enum SendOrderResponseStatus {
  @Json(name = "Accepted")
  ACCEPTED,
  @Json(name = "Rejected")
  REJECTED;
}
