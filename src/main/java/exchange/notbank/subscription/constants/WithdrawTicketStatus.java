package exchange.notbank.subscription.constants;

import com.squareup.moshi.Json;

public enum WithdrawTicketStatus {
  @Json(name = "New")
  NEW,
  @Json(name = "AdminProcessing")
  ADMIN_PROCESSING,
  @Json(name = "Accepted")
  ACCEPTED,
  @Json(name = "Rejected")
  REJECTED,
  @Json(name = "SystemProcessing")
  SYSTEM_PROCESSING,
  @Json(name = "FullyProcessed")
  FULLY_PROCESSED,
  @Json(name = "Failed")
  FAILED,
  @Json(name = "Pending")
  PENDING,
  @Json(name = "Pending2Fa")
  PENDING_2FA,
  @Json(name = "AutoAccepted")
  AUTO_ACCEPTED,
  @Json(name = "Delayed")
  DELAYED,
  @Json(name = "UserCanceled")
  USER_CANCELED,
  @Json(name = "AdminCanceled")
  ADMIN_CANCELED,
  @Json(name = "AmlProcessing")
  AML_PROCESSING,
  @Json(name = "AmlAccepted")
  AML_ACCEPTED,
  @Json(name = "AmlRejected")
  AML_REJECTED,
  @Json(name = "AmlFailed")
  AML_FAILED,
  @Json(name = "LimitsAccepted")
  LIMITS_ACCEPTED,
  @Json(name = "LimitsRejected")
  LIMITS_REJECTED,
  @Json(name = "Submitted")
  SUBMITTED,
  @Json(name = "Confirmed")
  CONFIRMED,
  @Json(name = "ManuallyConfirmed")
  MANUALLY_CONFIRMED,
  @Json(name = "Confirmed2Fa")
  CONFIRMED_2FA;
}
