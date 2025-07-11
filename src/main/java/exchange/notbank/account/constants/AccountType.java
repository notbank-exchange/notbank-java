package exchange.notbank.account.constants;

import com.squareup.moshi.Json;

public enum AccountType {
  @Json(name = "Asset")
  ASSET,
  @Json(name = "Liability")
  LIABILITY,
  @Json(name = "Receivable")
  RECEIVABLE,
  @Json(name = "Margin")
  MARGIN,
  @Json(name = "MarginAsset")
  MARGIN_ASSET;
}
