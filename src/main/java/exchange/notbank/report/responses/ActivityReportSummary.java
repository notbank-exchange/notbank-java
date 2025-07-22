package exchange.notbank.report.responses;

import java.util.List;
import java.util.UUID;

import com.squareup.moshi.Json;

import exchange.notbank.report.constants.ReportFrequency;
import exchange.notbank.report.constants.RequestStatus;

public class ActivityReportSummary {
  @Json(name = "RequestingUser")
  public final Integer requestingUser;
  public final Integer OMSId;
  public final String reportFlavor;
  public final String createTime;
  public final String initialRunTime;
  public final String intervalStartTime;
  public final String intervalEndTime;
  @Json(name = "RequestStatus")
  public final RequestStatus requestStatus;
  @Json(name = "ReportFrequency")
  public final ReportFrequency reportFrequency;
  public final Long intervalDuration;
  @Json(name = "RequestId")
  public final UUID requestId;
  public final UUID lastInstanceId;
  public final List<Integer> accountIds;

  public ActivityReportSummary(Integer requestingUser, Integer oMSId, String reportFlavor, String createTime,
      String initialRunTime, String intervalStartTime, String intervalEndTime, RequestStatus requestStatus,
      ReportFrequency reportFrequency, Long intervalDuration, UUID requestId, UUID lastInstanceId,
      List<Integer> accountIds) {
    this.requestingUser = requestingUser;
    OMSId = oMSId;
    this.reportFlavor = reportFlavor;
    this.createTime = createTime;
    this.initialRunTime = initialRunTime;
    this.intervalStartTime = intervalStartTime;
    this.intervalEndTime = intervalEndTime;
    this.requestStatus = requestStatus;
    this.reportFrequency = reportFrequency;
    this.intervalDuration = intervalDuration;
    this.requestId = requestId;
    this.lastInstanceId = lastInstanceId;
    this.accountIds = accountIds;
  }

  @Override
  public String toString() {
    return "ActivityReport [requestingUser=" + requestingUser + ", OMSId=" + OMSId + ", reportFlavor=" + reportFlavor
        + ", createTime=" + createTime + ", initialRunTime=" + initialRunTime + ", intervalStartTime="
        + intervalStartTime + ", intervalEndTime=" + intervalEndTime + ", requestStatus=" + requestStatus
        + ", reportFrequency=" + reportFrequency + ", intervalDuration=" + intervalDuration + ", requestId=" + requestId
        + ", lastInstanceId=" + lastInstanceId + ", accountIds=" + accountIds + "]";
  }
}
