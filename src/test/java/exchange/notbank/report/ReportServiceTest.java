package exchange.notbank.report;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import exchange.notbank.CredentialsLoader.UserCredentials;
import exchange.notbank.NotbankClient;
import exchange.notbank.StringResponseNotbankConnection;
import exchange.notbank.TestHelper;
import exchange.notbank.report.constants.ReportFrequency;
import exchange.notbank.report.constants.ReportRequestStatus;
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
  private static StringResponseNotbankConnection connection;
  private static NotbankClient client;

  @BeforeAll
  public static void beforeAll() throws InterruptedException, ExecutionException {
    connection = new StringResponseNotbankConnection();
    // var client = NotbankClientFactory.create(connection, conn -> CompletableFuture.completedFuture(conn));
    client = TestHelper.newRestClient();
    credentials = TestHelper.getUserCredentials();
    accountId = credentials.accountId;
  }

  @Test
  public void generateTradeActivityReport() {
    var response = """
        {"RequestingUser":9,"OMSId":1,"reportFlavor":"TradeActivity","createTime":"2025-10-02T04:13:05.515Z","initialRunTime":"2025-10-02T04:13:05.515Z","intervalStartTime":"2025-06-10T16:00:00.000Z","intervalEndTime":"2025-07-15T16:00:00.000Z","RequestStatus":"Submitted","ReportFrequency":"onDemand","intervalDuration":30240000000000,"RequestId":"ad2d5627-7840-1aa0-2968-308f7e771e21","lastInstanceId":"00000000-0000-0000-0000-000000000000","accountIds":[235]}
        """;
    connection.setJsonResponse(response);
    var futureResponse = client.getReportService().generateTradeActivityReport(new GenerateActivityReportParamBuilder(
        List.of(accountId),
        "2025-06-10T16:00:00.000Z",
        "2025-07-15T16:00:00.000Z"));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void generateTransactionActivityReport() {
    var futureResponse = client.getReportService()
        .generateTransactionActivityReport(new GenerateActivityReportParamBuilder(
            List.of(accountId),
            "2025-06-10T16:00:00.000Z",
            "2025-07-15T16:00:00.000Z"));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void generatePnLActivityReport() {
    var futureResponse = client.getReportService().generatePnLActivityReport(new GenerateActivityReportParamBuilder(
        List.of(accountId),
        "2025-06-10T16:00:00.000Z",
        "2025-07-15T16:00:00.000Z"));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void generateProductDeltaActivityReport() {
    var futureResponse = client.getReportService()
        .generateProductDeltaActivityReport(new GenerateActivityReportParamBuilder(
            List.of(accountId),
            "2025-06-10T16:00:00.000Z",
            "2025-07-15T16:00:00.000Z"));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void scheduleTradeActivityReport() {
    var futureResponse = client.getReportService().scheduleTradeActivityReport(
        new ScheduleActivityReportParamBuilder(List.of(accountId), "2025-07-19T16:00:00.000Z")
            .frequency(ReportFrequency.WEEKLY));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void scheduleTransactionActivityReport() {
    var futureResponse = client.getReportService().scheduleTransactionActivityReport(
        new ScheduleActivityReportParamBuilder(List.of(accountId), "2025-07-19T16:00:00.000Z")
            .frequency(ReportFrequency.WEEKLY));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void scheduleProductDeltaActivityReport() {
    var futureResponse = client.getReportService().scheduleProductDeltaActivityReport(
        new ScheduleActivityReportParamBuilder(List.of(accountId), "2025-07-19T16:00:00.000Z")
            .frequency(ReportFrequency.WEEKLY));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void scheduleProfitAndLossActivityReport() {
    var futureResponse = client.getReportService().scheduleProfitAndLossActivityReport(
        new ScheduleActivityReportParamBuilder(List.of(accountId), "2025-07-19T16:00:00.000Z")
            .frequency(ReportFrequency.WEEKLY));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getUserReportTickets() {
    var futureResponse = client.getReportService().getUserReportTickets(
        new GetUserReportTicketsParamBuilder(credentials.userId));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getUserReportTicketsByStatus() {
    var futureResponse = client.getReportService().getUserReportTicketsByStatus(
        new GetUserReportTicketsByStatusParamBuilder(
            List.of(ReportRequestStatus.IN_PROGRESS, ReportRequestStatus.SCHEDULED)));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getUserReportWriterResultRecords() {
    var futureResponse = client.getReportService().getUserReportWriterResultRecords(
        new GetUserReportWriterResultRecordsParamBuilder(credentials.userId));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void cancelUserReport() throws InterruptedException, ExecutionException {
    var reportSummary = client.getReportService().generateTradeActivityReport(new GenerateActivityReportParamBuilder(
        List.of(accountId),
        "2025-06-10T16:00:00.000Z",
        "2025-07-15T16:00:00.000Z"));
    var futureResponse = client.getReportService().cancelUserReport(
        new CancelUserReportParamBuilder(reportSummary.get().requestId));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void removeUserReportTicket() throws InterruptedException, ExecutionException, TimeoutException {
    var reportSummary = client.getReportService().generateTradeActivityReport(new GenerateActivityReportParamBuilder(
        List.of(accountId),
        "2025-06-10T16:00:00.000Z",
        "2025-07-15T16:00:00.000Z"));
    TimeUnit.SECONDS.sleep(5);
    var futureResponse = client.getReportService().removeUserReportTicket(
        new RemoveUserReportTicketParamBuilder(reportSummary.get(1, TimeUnit.MINUTES).requestId));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void downloadDocument() throws InterruptedException, ExecutionException {
    var futureResponse = client.getReportService().downloadDocument(
        new DownloadDocumentParamBuilder(UUID.fromString("3108e502-ba32-f2b0-73b3-ffb0bd997390")));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void downloadDocumentSlice() throws InterruptedException, ExecutionException {
    var documentDescriptor = client.getReportService().downloadDocument(
        new DownloadDocumentParamBuilder(UUID.fromString("3108e502-ba32-f2b0-73b3-ffb0bd997390")));
    var futureResponse = client.getReportService().downloadDocumentSlice(
        new DownloadDocumentSliceParamBuilder(documentDescriptor.get().descriptorId).sliceNum(0));
    TestHelper.checkNoError(futureResponse);
  }
}
