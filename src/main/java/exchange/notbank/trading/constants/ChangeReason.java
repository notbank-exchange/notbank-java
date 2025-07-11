package exchange.notbank.trading.constants;

import com.squareup.moshi.Json;

public enum ChangeReason {
  @Json(name = "Unknown")
  UNKNOWN,
  @Json(name = "NewInputAccepted")
  NEW_INPUT_ACCEPTED,
  @Json(name = "NewInputRejected")
  NEW_INPUT_REJECTED,
  @Json(name = "OtherRejected")
  OTHER_REJECTED,
  @Json(name = "Expired")
  EXPIRED,
  @Json(name = "Trade")
  TRADE,
  @Json(name = "SystemCanceled_NoMoreMarket")
  SYSTEM_CANCELED_NO_MORE_MARKET,
  @Json(name = "SystemCanceled_BelowMinimum")
  SYSTEM_CANCELED_BELOW_MINIMUM,
  @Json(name = "SystemCanceled_PriceCollar")
  SYSTEM_CANCELED_PRICE_COLLAR,
  @Json(name = "SystemCanceled_MarginFailed")
  SYSTEM_CANCELED_MARGIN_FAILED,
  @Json(name = "UserModified")
  USER_MODIFIED;
}
