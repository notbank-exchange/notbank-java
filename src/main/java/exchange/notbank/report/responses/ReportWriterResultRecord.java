package exchange.notbank.report.responses;

public class ReportWriterResultRecord {
  public final Integer RequestingUser;
  public final String urtTicketIdreport;
  public final String descriptorId;
  public final String resultStatus;
  public final String reportExecutionStartTime;
  public final String reportExecutionCompleteTime;
  public final String reportOutputFilePathname;
  public final String reportDescriptiveHeaderstring;

  public ReportWriterResultRecord(Integer requestingUser, String urtTicketIdreport, String descriptorId,
      String resultStatus, String reportExecutionStartTime, String reportExecutionCompleteTime,
      String reportOutputFilePathname, String reportDescriptiveHeaderstring) {
    RequestingUser = requestingUser;
    this.urtTicketIdreport = urtTicketIdreport;
    this.descriptorId = descriptorId;
    this.resultStatus = resultStatus;
    this.reportExecutionStartTime = reportExecutionStartTime;
    this.reportExecutionCompleteTime = reportExecutionCompleteTime;
    this.reportOutputFilePathname = reportOutputFilePathname;
    this.reportDescriptiveHeaderstring = reportDescriptiveHeaderstring;
  }

  @Override
  public String toString() {
    return "ReportWriterResultRecord [RequestingUser=" + RequestingUser + ", urtTicketIdreport=" + urtTicketIdreport
        + ", descriptorId=" + descriptorId + ", resultStatus=" + resultStatus + ", reportExecutionStartTime="
        + reportExecutionStartTime + ", reportExecutionCompleteTime=" + reportExecutionCompleteTime
        + ", reportOutputFilePathname=" + reportOutputFilePathname + ", reportDescriptiveHeaderstring="
        + reportDescriptiveHeaderstring + "]";
  }
}
