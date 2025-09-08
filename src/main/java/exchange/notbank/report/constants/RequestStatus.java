package exchange.notbank.report.constants;

import com.squareup.moshi.Json;

public enum RequestStatus {
  @Json(name = "Submitted")
  SUBMITTED,
  @Json(name = "Validating")
  VALIDATING,
  @Json(name = "Scheduled")
  SCHEDULED,
  @Json(name = "InProgress")
  IN_PROGRESS,
  @Json(name = "Completed")
  COMPLETED,
  @Json(name = "Aborting")
  ABORTING,
  @Json(name = "Aborted")
  ABORTED,
  @Json(name = "UserCancelled")
  USER_CANCELLED,
  @Json(name = "SysRetired")
  SYS_RETIRED,
  @Json(name = "UserCancelledPending")
  USER_CANCELLED_PENDING;
}
