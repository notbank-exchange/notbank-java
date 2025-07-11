package exchange.notbank.trading.constants;

import com.squareup.moshi.Json;

public enum TimeInForce {
  @Json(name = "Unknown")
  UNKNOWN,
  @Json(name = "GTC")
  GTC,
  @Json(name = "OPG")
  OPG,
  @Json(name = "IOC")
  IOC,
  @Json(name = "FOK")
  FOK,
  @Json(name = "GTX")
  GTX,
  @Json(name = "GTD")
  GTD;
}
