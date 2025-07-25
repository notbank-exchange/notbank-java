package exchange.notbank.report;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import exchange.notbank.core.ErrorHandler;
import exchange.notbank.core.NotbankException;
import exchange.notbank.report.responses.ActivityReportSummary;
import exchange.notbank.report.responses.DocumentDescriptor;
import exchange.notbank.report.responses.DocumentSlice;
import exchange.notbank.report.responses.ReportWriterResultRecord;
import io.vavr.control.Either;

public class ReportServiceResponseAdapter {
  private final ErrorHandler errorHandler;
  private final ReportErrorHandler reportErrorHandler;
  private final JsonAdapter<ActivityReportSummary> activityReportAdapter;
  private final JsonAdapter<List<ActivityReportSummary>> activityReportListAdapter;
  private final JsonAdapter<DocumentDescriptor> documentDescriptoAdapter;
  private final JsonAdapter<DocumentSlice> documentSliceAdapter;
  private final JsonAdapter<List<ReportWriterResultRecord>> reportWriterResultRecordListAdapter;

  public ReportServiceResponseAdapter(Moshi moshi) {
    errorHandler = ErrorHandler.Factory.createApErrorHandler(moshi);
    reportErrorHandler = ReportErrorHandler.Factory.create(moshi);
    activityReportAdapter = moshi.adapter(ActivityReportSummary.class);
    ParameterizedType ActivityReportListType = Types.newParameterizedType(List.class, ActivityReportSummary.class);
    activityReportListAdapter = moshi.adapter(ActivityReportListType);
    documentDescriptoAdapter = moshi.adapter(DocumentDescriptor.class);
    documentSliceAdapter = moshi.adapter(DocumentSlice.class);
    ParameterizedType ReportWriterResultRecordListType = Types.newParameterizedType(List.class,
        ReportWriterResultRecord.class);
    reportWriterResultRecordListAdapter = moshi.adapter(ReportWriterResultRecordListType);
  }

  public Either<NotbankException, Void> toNone(String jsonStr) {
    return errorHandler.toNone(jsonStr);
  }

  private <T> Either<NotbankException, T> handle(String jsonString, JsonAdapter<T> jsonAdapter) {
    return reportErrorHandler.handle(jsonString).flatMap(errorHandler.handleAndThen(jsonAdapter));
  }

  public Either<NotbankException, ActivityReportSummary> toActivityReport(String jsonStr) {
    return handle(jsonStr, activityReportAdapter);
  }

  public Either<NotbankException, List<ActivityReportSummary>> toActivityReportList(String jsonStr) {
    return handle(jsonStr, activityReportListAdapter);
  }

  public Either<NotbankException, DocumentDescriptor> toDocumentDescriptor(String jsonStr) {
    return handle(jsonStr, documentDescriptoAdapter);
  }

  public Either<NotbankException, DocumentSlice> toDocumentSlice(String jsonStr) {
    return handle(jsonStr, documentSliceAdapter);
  }

  Either<NotbankException, List<ReportWriterResultRecord>> toReportWriterResultRecordList(String jsonStr) {
    return handle(jsonStr, reportWriterResultRecordListAdapter);
  }
}
