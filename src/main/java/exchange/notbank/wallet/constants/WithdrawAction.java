package exchange.notbank.wallet.constants;

import com.squareup.moshi.Json;

public enum WithdrawAction {
  @Json(name = "enable")
  ENABLE,
  @Json(name = "disable")
  DISABLE;
}
