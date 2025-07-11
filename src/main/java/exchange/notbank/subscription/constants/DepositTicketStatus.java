package exchange.notbank.subscription.constants;

import com.squareup.moshi.Json;

public enum DepositTicketStatus {
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
  @Json(name = "Confirmed ")
  CONFIRMED,
  @Json(name = "AmlProcessing")
  AML_PROCESSING,
  @Json(name = "AmlAccepted")
  AML_ACCEPTED,
  @Json(name = "AmlRejected")
  AML_REJECTED,
  @Json(name = "AmlFailed ")
  AML_FAILED,
  @Json(name = "LimitsAccepted")
  LIMIT_SACCEPTED,
  @Json(name = "LimitsRejected")
  LIMIT_SREJECTED;
}
