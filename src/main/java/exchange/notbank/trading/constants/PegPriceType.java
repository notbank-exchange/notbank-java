package exchange.notbank.trading.constants;

import com.squareup.moshi.Json;

public enum PegPriceType {
  @Json(name = "Last")
  LAST,
  @Json(name = "Bid")
  BID,
  @Json(name = "Ask")
  ASK,
  @Json(name = "Midpoint")
  MIDPOINT;
}
