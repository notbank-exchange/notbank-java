package exchange.notbank.core;

import java.util.function.Function;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import io.vavr.control.Either;

public class ErrorHandler {
  Function<String, Either<NotbankException, String>> handleErrorFn;

  public ErrorHandler(
      Function<String, Either<NotbankException, String>> handleErrorFn) {
    this.handleErrorFn = handleErrorFn;
  }

  public static class Factory {
    public static ErrorHandler createApErrorHandler(Moshi moshi) {
      var standardApErrorHandler = StandardApResponseHandler.Factory.create(moshi);
      return new ErrorHandler(standardApErrorHandler::handle);
    }

    public static ErrorHandler createNbErrorHandler(Moshi moshi) {
      var standardNbErrorHandler = StandardNbResponseHandler.Factory.create(moshi);
      return new ErrorHandler(standardNbErrorHandler::handle);
    }
  }

  public Either<NotbankException, Void> toNone(String jsonStr) {
    return handleErrorFn.apply(jsonStr).map(o -> null);
  }

  public <T> Function<String, Either<NotbankException, T>> handleAndThen(JsonAdapter<T> jsonAdapter) {
    return jsonString -> handleErrorFn.apply(jsonString)
        .flatMap(jsonStr -> JsonDeserializer.deserialize(jsonStr, jsonAdapter));
  }
}