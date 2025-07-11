package exchange.notbank.users.constants;

import com.squareup.moshi.Json;

public enum TransactionType {
  @Json(name = "Fee")
  FEE,
  @Json(name = "Trade")
  TRADE,
  @Json(name = "Other")
  OTHER,
  @Json(name = "Reverse")
  REVERSE,
  @Json(name = "Hold")
  HOLD,
  @Json(name = "Rebate")
  REBATE,
  @Json(name = "MarginAcquisition")
  MARGIN_ACQUISITION,
  @Json(name = "MarginRelinquish")
  MARGIN_RELINQUISH;
}
