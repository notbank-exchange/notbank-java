package exchange.notbank.trading.constants;

import com.squareup.moshi.Json;

public enum OrderType {
  @Json(name = "Unknown")
  UNKNOWN,
  @Json(name = "Market")
  MARKET,
  @Json(name = "Limit")
  LIMIT,
  @Json(name = "StopMarket")
  STOP_MARKET,
  @Json(name = "StopLimit")
  STOP_LIMIT,
  @Json(name = "TrailingStopMarket")
  TRAILING_STOP_MARKET,
  @Json(name = "TrailingStopLimit")
  TRAILING_STOP_LIMIT,
  @Json(name = "BlockTrade")
  BLOCK_TRADE;
}
