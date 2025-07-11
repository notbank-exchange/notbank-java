package exchange.notbank.trading.constants;

import com.squareup.moshi.Json;

public enum LiquidityProviderType {
  @Json(name = "Maker")
  MAKER,
  @Json(name = "Taker")
  TAKER;
}