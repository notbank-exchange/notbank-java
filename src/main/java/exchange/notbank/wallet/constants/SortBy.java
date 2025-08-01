package exchange.notbank.wallet.constants;

import com.squareup.moshi.Json;

public enum SortBy {
  @Json(name="ASC")
  ASC,
  @Json(name="DESC")
  DESC;
}
