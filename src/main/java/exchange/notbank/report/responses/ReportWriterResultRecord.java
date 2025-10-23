package exchange.notbank.report.responses;

import com.squareup.moshi.Json;

public class ReportWriterResultRecord {
  @Json(name = "RequestingUser")
  public final Integer requestingUser;
  public final String urtTicketId;
  public final String descriptorId;
  public final String resultStatus;
  public final String reportExecutionStartTime;
  public final String reportExecutionCompleteTime;
  public final String reportOutputFilePathname;
  public final String reportDescriptiveHeaderstring;
  @Json(name = "ReportLocation")
  public final String reportLocation;

  public ReportWriterResultRecord(Integer requestingUser, String urtTicketId, String descriptorId, String resultStatus,
      String reportExecutionStartTime, String reportExecutionCompleteTime, String reportOutputFilePathname,
      String reportDescriptiveHeaderstring, String reportLocation) {
    this.requestingUser = requestingUser;
    this.urtTicketId = urtTicketId;
    this.descriptorId = descriptorId;
    this.resultStatus = resultStatus;
    this.reportExecutionStartTime = reportExecutionStartTime;
    this.reportExecutionCompleteTime = reportExecutionCompleteTime;
    this.reportOutputFilePathname = reportOutputFilePathname;
    this.reportDescriptiveHeaderstring = reportDescriptiveHeaderstring;
    this.reportLocation = reportLocation;
  }

  @Override
  public String toString() {
    return "ReportWriterResultRecord [requestingUser=" + requestingUser + ", urtTicketId=" + urtTicketId
        + ", descriptorId=" + descriptorId + ", resultStatus=" + resultStatus + ", reportExecutionStartTime="
        + reportExecutionStartTime + ", reportExecutionCompleteTime=" + reportExecutionCompleteTime
        + ", reportOutputFilePathname=" + reportOutputFilePathname + ", reportDescriptiveHeaderstring="
        + reportDescriptiveHeaderstring + ", reportLocation=" + reportLocation + "]";
  }

}
