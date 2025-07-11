package exchange.notbank.users.constants;

import com.squareup.moshi.Json;

public enum FeeProductType {
  @Json(name = "BaseProduct")
  BASE_PRODUCT,
  @Json(name = "SingleProduct")
  SINGLE_PRODUCT;
}
