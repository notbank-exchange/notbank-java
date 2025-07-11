package exchange.notbank.trading.constants;

import com.squareup.moshi.Json;

public enum TradeType {
  @Json(name = "sell")
  SELL,
  @Json(name = "buy")
  BUY;
}
