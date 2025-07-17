package exchange.notbank.report;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;

import exchange.notbank.core.EndpointCategory;
import exchange.notbank.core.NotbankConnection;
import exchange.notbank.core.NotbankException;
import exchange.notbank.core.ParamBuilder;
import exchange.notbank.report.constants.Endpoints;
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

  public CompletableFuture<ActivityReport> generateTradeActivityReport(ParamBuilder paramBuilder) {
    return this.requestPost(Endpoints.GENERATE_TRADE_ACTIVITY_REPORT, paramBuilder, responseAdapter::toActivityReport);
  }

  public CompletableFuture<ActivityReport> generateTransactionActivityReport(ParamBuilder paramBuilder) {
    return this.requestPost(Endpoints.GENERATE_TRANSACTION_ACTIVITY_REPORT, paramBuilder,
        responseAdapter::toActivityReport);
  }

  public CompletableFuture<ActivityReport> generateProductDeltaActivityReport(ParamBuilder paramBuilder) {
    return this.requestPost(Endpoints.GENERATE_PRODUCT_DELTA_ACTIVITY_REPORT, paramBuilder,
        responseAdapter::toActivityReport);
  }

  public CompletableFuture<ActivityReport> generatePnLActivityReport(ParamBuilder paramBuilder) {
    return this.requestPost(Endpoints.GENERATE_PN_L_ACTIVITY_REPORT, paramBuilder, responseAdapter::toActivityReport);
  }

  public CompletableFuture<ActivityReport> scheduleTradeActivityReport(ParamBuilder paramBuilder) {
    return this.requestPost(Endpoints.SCHEDULE_TRADE_ACTIVITY_REPORT, paramBuilder, responseAdapter::toActivityReport);
  }

  public CompletableFuture<ActivityReport> scheduleTransactionActivityReport(ParamBuilder paramBuilder) {
    return this.requestPost(Endpoints.SCHEDULE_TRANSACTION_ACTIVITY_REPORT, paramBuilder,
        responseAdapter::toActivityReport);
  }

  public CompletableFuture<ActivityReport> scheduleProductDeltaActivityReport(ParamBuilder paramBuilder) {
    return this.requestPost(Endpoints.SCHEDULE_PRODUCT_DELTA_ACTIVITY_REPORT, paramBuilder,
        responseAdapter::toActivityReport);
  }

  public CompletableFuture<ActivityReport> scheduleProfitAndLossActivityReport(ParamBuilder paramBuilder) {
    return this.requestPost(Endpoints.SCHEDULE_PROFIT_AND_LOSS_ACTIVITY_REPORT, paramBuilder,
        responseAdapter::toActivityReport);
  }

  public CompletableFuture<Void> cancelUserReport(ParamBuilder paramBuilder) {
    return this.requestPost(Endpoints.CANCEL_USER_REPORT, paramBuilder, responseAdapter::toNone);
  }

  public CompletableFuture<List<ReportWriterResultRecord>> getUserReportWriterResultRecords(ParamBuilder paramBuilder) {
    return this.requestPost(Endpoints.GET_USER_REPORT_WRITER_RESULT_RECORDS, paramBuilder,
        responseAdapter::toReportWriterResultRecordList);
  }

  public CompletableFuture<List<ActivityReport>> getUserReportTickets(ParamBuilder paramBuilder) {
    return this.requestPost(Endpoints.GET_USER_REPORT_TICKETS, paramBuilder, responseAdapter::toActivityReportList);
  }

  public CompletableFuture<Void> removeUserReportTicket(ParamBuilder paramBuilder) {
    return this.requestPost(Endpoints.REMOVE_USER_REPORT_TICKET, paramBuilder, responseAdapter::toNone);
  }

  public CompletableFuture<List<ActivityReport>> getUserReportTicketsByStatus(ParamBuilder paramBuilder) {
    return this.requestPost(Endpoints.GET_USER_REPORT_TICKETS_BY_STATUS, paramBuilder,
        responseAdapter::toActivityReportList);
  }

  public CompletableFuture<DocumentDescriptor> downloadDocument(ParamBuilder paramBuilder) {
    return this.requestPost(Endpoints.DOWNLOAD_DOCUMENT, paramBuilder, responseAdapter::toDocumentDescriptor);
  }

  public CompletableFuture<DocumentSlice> downloadDocumentSlice(ParamBuilder paramBuilder) {
    return this.requestPost(Endpoints.DOWNLOAD_DOCUMENT_SLICE, paramBuilder, responseAdapter::toDocumentSlice);
  }
}
