package exchange.notbank.report;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import exchange.notbank.CredentialsLoader.UserCredentials;
import exchange.notbank.TestHelper;
import exchange.notbank.report.constants.ReportFrequency;
import exchange.notbank.report.constants.RequestStatus;
import exchange.notbank.report.paramBuilders.CancelUserReportParamBuilder;
import exchange.notbank.report.paramBuilders.DownloadDocumentParamBuilder;
import exchange.notbank.report.paramBuilders.DownloadDocumentSliceParamBuilder;
import exchange.notbank.report.paramBuilders.GenerateActivityReportParamBuilder;
import exchange.notbank.report.paramBuilders.GetUserReportTicketsByStatusParamBuilder;
import exchange.notbank.report.paramBuilders.GetUserReportTicketsParamBuilder;
import exchange.notbank.report.paramBuilders.GetUserReportWriterResultRecordsParamBuilder;
import exchange.notbank.report.paramBuilders.RemoveUserReportTicketParamBuilder;
import exchange.notbank.report.paramBuilders.ScheduleActivityReportParamBuilder;

public class ReportServiceTest {
  private static Integer accountId;
  private static UserCredentials credentials;
  private static ReportService service;

  @BeforeAll
  public static void beforeAll() throws InterruptedException, ExecutionException {
    var client = TestHelper.newRestClient();
    service = client.getReportService();
    credentials = TestHelper.getUserCredentials();
    accountId = credentials.accountId;
  }

  @Test
  public void generateTradeActivityReport() {
    var futureResponse = service.generateTradeActivityReport(new GenerateActivityReportParamBuilder(
        List.of(accountId),
        "2025-06-10T16:00:00.000Z",
        "2025-07-15T16:00:00.000Z"));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void generateTransactionActivityReport() {
    var futureResponse = service.generateTransactionActivityReport(new GenerateActivityReportParamBuilder(
        List.of(accountId),
        "2025-06-10T16:00:00.000Z",
        "2025-07-15T16:00:00.000Z"));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void generatePnLActivityReport() {
    var futureResponse = service.generatePnLActivityReport(new GenerateActivityReportParamBuilder(
        List.of(accountId),
        "2025-06-10T16:00:00.000Z",
        "2025-07-15T16:00:00.000Z"));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void generateProductDeltaActivityReport() {
    var futureResponse = service.generateProductDeltaActivityReport(new GenerateActivityReportParamBuilder(
        List.of(accountId),
        "2025-06-10T16:00:00.000Z",
        "2025-07-15T16:00:00.000Z"));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void scheduleTradeActivityReport() {
    var futureResponse = service.scheduleTradeActivityReport(
        new ScheduleActivityReportParamBuilder(List.of(accountId), "2025-07-19T16:00:00.000Z"));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void scheduleTransactionActivityReport() {
    var futureResponse = service.scheduleTransactionActivityReport(
        new ScheduleActivityReportParamBuilder(List.of(accountId), "2025-07-19T16:00:00.000Z")
            .frequency(ReportFrequency.ANNUALLY));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void scheduleProductDeltaActivityReport() {
    var futureResponse = service.scheduleProductDeltaActivityReport(
        new ScheduleActivityReportParamBuilder(List.of(accountId), "2025-07-19T16:00:00.000Z")
            .frequency(ReportFrequency.MONTHLY));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void scheduleProfitAndLossActivityReport() {
    var futureResponse = service.scheduleProfitAndLossActivityReport(
        new ScheduleActivityReportParamBuilder(List.of(accountId), "2025-07-19T16:00:00.000Z")
            .frequency(ReportFrequency.WEEKLY));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getUserReportTickets() {
    var futureResponse = service.getUserReportTickets(
        new GetUserReportTicketsParamBuilder(credentials.userId));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getUserReportTicketsByStatus() {
    var futureResponse = service.getUserReportTicketsByStatus(
        new GetUserReportTicketsByStatusParamBuilder(List.of(RequestStatus.IN_PROGRESS)));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getUserReportWriterResultRecords() {
    var futureResponse = service.getUserReportWriterResultRecords(
        new GetUserReportWriterResultRecordsParamBuilder(accountId));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void cancelUserReport() throws InterruptedException, ExecutionException {
    var reportSummary = service.generateTradeActivityReport(new GenerateActivityReportParamBuilder(
        List.of(accountId),
        "2025-06-10T16:00:00.000Z",
        "2025-07-15T16:00:00.000Z"));
    var futureResponse = service.cancelUserReport(
        new CancelUserReportParamBuilder(reportSummary.get().requestId));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void removeUserReportTicket() throws InterruptedException, ExecutionException {
    var reportSummary = service.generateTradeActivityReport(new GenerateActivityReportParamBuilder(
        List.of(accountId),
        "2025-06-10T16:00:00.000Z",
        "2025-07-15T16:00:00.000Z"));
    var futureResponse = service.removeUserReportTicket(
        new RemoveUserReportTicketParamBuilder(reportSummary.get().requestId));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void downloadDocument() throws InterruptedException, ExecutionException {
    var reportSummary = service.generateTradeActivityReport(new GenerateActivityReportParamBuilder(
        List.of(accountId),
        "2025-06-10T16:00:00.000Z",
        "2025-07-15T16:00:00.000Z"));
    var futureResponse = service.downloadDocument(
        new DownloadDocumentParamBuilder(reportSummary.get().requestId));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void downloadDocumentSlice() throws InterruptedException, ExecutionException {
    var reportSummary = service.generateTradeActivityReport(new GenerateActivityReportParamBuilder(
        List.of(accountId),
        "2025-06-10T16:00:00.000Z",
        "2025-07-15T16:00:00.000Z"));
    var documentDescriptor = service.downloadDocument(
        new DownloadDocumentParamBuilder(reportSummary.get().requestId));
    var futureResponse = service
        .downloadDocumentSlice(new DownloadDocumentSliceParamBuilder(documentDescriptor.get().descriptorId));
    TestHelper.checkNoError(futureResponse);
  }

}
