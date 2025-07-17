package exchange.notbank.report.responses;

import java.util.List;

import com.squareup.moshi.Json;

public class ActivityReport {
  @Json(name = "RequestingUser")
  public final Integer requestingUser;
  public final Integer OMSId;
  public final String reportFlavor;
  public final String createTime;
  public final String initialRunTime;
  public final String intervalStartTime;
  public final String intervalEndTime;
  @Json(name = "RequestStatus")
  public final String requestStatus;
  @Json(name = "ReportFrequency")
  public final String reportFrequency;
  public final Long intervalDuration;
  @Json(name = "RequestId")
  public final String requestId;
  public final String lastInstanceId;
  public final List<Integer> accountIds;

  public ActivityReport(Integer requestingUser, Integer oMSId, String reportFlavor, String createTime,
      String initialRunTime, String intervalStartTime, String intervalEndTime, String requestStatus,
      String reportFrequency, Long intervalDuration, String requestId, String lastInstanceId,
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
