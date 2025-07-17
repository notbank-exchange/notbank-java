package exchange.notbank.report;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import exchange.notbank.core.ErrorHandler;
import exchange.notbank.core.NotbankException;
import exchange.notbank.report.responses.ActivityReport;
import exchange.notbank.report.responses.DocumentDescriptor;
import exchange.notbank.report.responses.DocumentSlice;
import exchange.notbank.report.responses.ReportWriterResultRecord;
import io.vavr.control.Either;

public class ReportServiceResponseAdapter {
  private final ErrorHandler errorHandler;
  private final JsonAdapter<ActivityReport> activityReportAdapter;
  private final JsonAdapter<List<ActivityReport>> activityReportListAdapter;
  private final JsonAdapter<DocumentDescriptor> documentDescriptoAdapter;
  private final JsonAdapter<DocumentSlice> documentSliceAdapter;
  private final JsonAdapter<List<ReportWriterResultRecord>> reportWriterResultRecordListAdapter;

  public ReportServiceResponseAdapter(Moshi moshi) {
    errorHandler = ErrorHandler.Factory.createApErrorHandler(moshi);
    activityReportAdapter = moshi.adapter(ActivityReport.class);
    ParameterizedType ActivityReportListType = Types.newParameterizedType(List.class, ActivityReport.class);
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
    return errorHandler.handleAndThen(jsonAdapter).apply(jsonString);
  }

  public Either<NotbankException, ActivityReport> toActivityReport(String jsonStr) {
    return handle(jsonStr, activityReportAdapter);
  }

  public Either<NotbankException, List<ActivityReport>> toActivityReportList(String jsonStr) {
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
