package exchange.notbank.product.constants;

import com.squareup.moshi.Json;

public enum ProductType {
  @Json(name = "Unknown")
  UNKNOWN,
  @Json(name = "NationalCurrency")
  NATIONAL_CURRENCY,
  @Json(name = "CryptoCurrency")
  CRYPTO_CURRENCY,
  @Json(name = "Contract")
  CONTRACT;
}
