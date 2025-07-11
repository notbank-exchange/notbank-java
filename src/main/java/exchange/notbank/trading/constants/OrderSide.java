package exchange.notbank.trading.constants;

import com.squareup.moshi.Json;

public enum OrderSide {
  @Json(name = "Buy")
  BUY,
  @Json(name = "Sell")
  SELL;
}
