package exchange.notbank.report;

import java.io.IOException;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.Moshi;

import exchange.notbank.core.NotbankException;
import exchange.notbank.core.NotbankException.ErrorType;
import io.vavr.control.Either;

public class ReportErrorHandler {
  private final JsonAdapter<StandardReportResponse> adapter;

  public ReportErrorHandler(JsonAdapter<StandardReportResponse> adapter) {
    this.adapter = adapter;
  }

  public static class Factory {
    public static ReportErrorHandler create(Moshi moshi) {
      return new ReportErrorHandler(moshi.adapter(StandardReportResponse.class));
    }
  }

  public <T> Either<NotbankException, String> handle(String jsonStr) {
    try {
      var errorResponse = adapter.fromJson(jsonStr);
      if (errorResponse.accepted == null && errorResponse.rejectMessage == null) {
        return Either.right(jsonStr);
      }
      var errMessage = errorResponse.rejectMessage;
      return Either.left(NotbankException.Factory.create(ErrorType.RESPONSE_ERROR, errMessage));
    } catch (IOException | JsonDataException e) {
      return Either.right(jsonStr);
    }
  }
}
