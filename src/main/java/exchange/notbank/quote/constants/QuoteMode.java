package exchange.notbank.quote.constants;

import com.squareup.moshi.Json;

public enum QuoteMode {
  @Json(name = "direct")
  DIRECT,
  @Json(name = "inverse")
  INVERSE;
}
