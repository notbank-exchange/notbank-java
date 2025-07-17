package exchange.notbank.report;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;

import exchange.notbank.core.EndpointCategory;
import exchange.notbank.core.NotbankConnection;
import exchange.notbank.core.NotbankException;
import exchange.notbank.core.ParamBuilder;
import exchange.notbank.core.ParamListBuilder;
import exchange.notbank.report.constants.Endpoints;
import exchange.notbank.report.paramBuilders.CancelUserReportParamBuilder;
import exchange.notbank.report.paramBuilders.DownloadDocumentParamBuilder;
import exchange.notbank.report.paramBuilders.DownloadDocumentSliceParamBuilder;
import exchange.notbank.report.paramBuilders.GenerateActivityReportParamBuilder;
import exchange.notbank.report.paramBuilders.RemoveUserReportTicketParamBuilder;
import exchange.notbank.report.paramBuilders.ScheduleActivityReportParamBuilder;
import exchange.notbank.report.paramBuilders.UserReportTicketsByStatusParamBuilder;
import exchange.notbank.report.paramBuilders.UserReportTicketsParamBuilder;
import exchange.notbank.report.paramBuilders.UserReportWriterResultRecordsParamBuilder;
import exchange.notbank.report.responses.ActivityReport;
import exchange.notbank.report.responses.DocumentDescriptor;
import exchange.notbank.report.responses.DocumentSlice;
import exchange.notbank.report.responses.ReportWriterResultRecord;
import io.vavr.control.Either;

public class ReportService {
  private final Supplier<CompletableFuture<NotbankConnection>> getNotbankConnection;
  private final ReportServiceResponseAdapter responseAdapter;

  public ReportService(Supplier<CompletableFuture<NotbankConnection>> getNotbankConnection,
      ReportServiceResponseAdapter responseAdapter) {
    this.getNotbankConnection = getNotbankConnection;
    this.responseAdapter = responseAdapter;
  }

  private <T> CompletableFuture<T> requestPost(String endpoint, ParamBuilder paramBuilder,
      Function<String, Either<NotbankException, T>> deserializeFn) {
    return getNotbankConnection.get()
        .thenCompose(
            connection -> connection.requestPost(EndpointCategory.AP, endpoint, paramBuilder, deserializeFn));
  }

  private <T> CompletableFuture<T> requestPostByText(String endpoint, String text, ParamBuilder paramBuilder,
      Function<String, Either<NotbankException, T>> deserializeFn) {
    return getNotbankConnection.get()
        .thenCompose(
            connection -> connection.requestPostByText(EndpointCategory.AP, endpoint, text,
                paramBuilder.getHttpConfiguration(), deserializeFn));
  }

  private <T> CompletableFuture<T> requestPostWithParamList(String endpoint, ParamListBuilder paramBuilder,
      Function<String, Either<NotbankException, T>> deserializeFn) {
    return getNotbankConnection.get()
        .thenCompose(connection -> connection.requestPost(EndpointCategory.AP, endpoint, paramBuilder, deserializeFn));
  }

  public CompletableFuture<ActivityReport> generateTradeActivityReport(
      GenerateActivityReportParamBuilder paramBuilder) {
    return this.requestPost(Endpoints.GENERATE_TRADE_ACTIVITY_REPORT, paramBuilder, responseAdapter::toActivityReport);
  }

  public CompletableFuture<ActivityReport> generateTransactionActivityReport(
      GenerateActivityReportParamBuilder paramBuilder) {
    return this.requestPost(Endpoints.GENERATE_TRANSACTION_ACTIVITY_REPORT, paramBuilder,
        responseAdapter::toActivityReport);
  }

  public CompletableFuture<ActivityReport> generateProductDeltaActivityReport(
      GenerateActivityReportParamBuilder paramBuilder) {
    return this.requestPost(Endpoints.GENERATE_PRODUCT_DELTA_ACTIVITY_REPORT, paramBuilder,
        responseAdapter::toActivityReport);
  }

  public CompletableFuture<ActivityReport> generatePnLActivityReport(GenerateActivityReportParamBuilder paramBuilder) {
    return this.requestPost(Endpoints.GENERATE_PN_L_ACTIVITY_REPORT, paramBuilder, responseAdapter::toActivityReport);
  }

  public CompletableFuture<ActivityReport> scheduleTradeActivityReport(
      ScheduleActivityReportParamBuilder paramBuilder) {
    return this.requestPost(Endpoints.SCHEDULE_TRADE_ACTIVITY_REPORT, paramBuilder, responseAdapter::toActivityReport);
  }

  public CompletableFuture<ActivityReport> scheduleTransactionActivityReport(
      ScheduleActivityReportParamBuilder paramBuilder) {
    return this.requestPost(Endpoints.SCHEDULE_TRANSACTION_ACTIVITY_REPORT, paramBuilder,
        responseAdapter::toActivityReport);
  }

  public CompletableFuture<ActivityReport> scheduleProductDeltaActivityReport(
      ScheduleActivityReportParamBuilder paramBuilder) {
    return this.requestPost(Endpoints.SCHEDULE_PRODUCT_DELTA_ACTIVITY_REPORT, paramBuilder,
        responseAdapter::toActivityReport);
  }

  public CompletableFuture<ActivityReport> scheduleProfitAndLossActivityReport(
      ScheduleActivityReportParamBuilder paramBuilder) {
    return this.requestPost(Endpoints.SCHEDULE_PROFIT_AND_LOSS_ACTIVITY_REPORT, paramBuilder,
        responseAdapter::toActivityReport);
  }

  public CompletableFuture<Void> cancelUserReport(CancelUserReportParamBuilder paramBuilder) {
    return this.requestPost(Endpoints.CANCEL_USER_REPORT, paramBuilder, responseAdapter::toNone);
  }

  public CompletableFuture<List<ReportWriterResultRecord>> getUserReportWriterResultRecords(
      UserReportWriterResultRecordsParamBuilder paramBuilder) {
    return this.requestPost(Endpoints.GET_USER_REPORT_WRITER_RESULT_RECORDS, paramBuilder,
        responseAdapter::toReportWriterResultRecordList);
  }

  public CompletableFuture<List<ActivityReport>> getUserReportTickets(UserReportTicketsParamBuilder paramBuilder) {
    return this.requestPost(Endpoints.GET_USER_REPORT_TICKETS, paramBuilder, responseAdapter::toActivityReportList);
  }

  public CompletableFuture<Void> removeUserReportTicket(RemoveUserReportTicketParamBuilder paramBuilder) {
    String requestBody = "{" + paramBuilder.getUserReportTicketId() + "}";
    return this.requestPostByText(Endpoints.REMOVE_USER_REPORT_TICKET, requestBody, paramBuilder,
        responseAdapter::toNone);
  }

  public CompletableFuture<List<ActivityReport>> getUserReportTicketsByStatus(
      UserReportTicketsByStatusParamBuilder paramBuilder) {
    return this.requestPostWithParamList(Endpoints.GET_USER_REPORT_TICKETS_BY_STATUS, paramBuilder,
        responseAdapter::toActivityReportList);
  }

  public CompletableFuture<DocumentDescriptor> downloadDocument(DownloadDocumentParamBuilder paramBuilder) {
    return this.requestPost(Endpoints.DOWNLOAD_DOCUMENT, paramBuilder, responseAdapter::toDocumentDescriptor);
  }

  public CompletableFuture<DocumentSlice> downloadDocumentSlice(DownloadDocumentSliceParamBuilder paramBuilder) {
    return this.requestPost(Endpoints.DOWNLOAD_DOCUMENT_SLICE, paramBuilder, responseAdapter::toDocumentSlice);
  }
}
