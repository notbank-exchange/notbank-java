package exchange.notbank.system;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import exchange.notbank.core.ErrorHandler;
import exchange.notbank.core.NotbankException;
import exchange.notbank.system.responses.HealthCheck;
import exchange.notbank.system.responses.Pong;
import io.vavr.control.Either;

public class SystemServiceResponseAdapter {
  private final ErrorHandler errorHandler;
  private final JsonAdapter<Pong> pingJsonAdapter;
  private final JsonAdapter<HealthCheck> healthCheckJsonAdapter;

  public SystemServiceResponseAdapter(Moshi moshi) {
    this.errorHandler = ErrorHandler.Factory.createApErrorHandler(moshi);
    this.pingJsonAdapter = moshi.adapter(Pong.class);
    this.healthCheckJsonAdapter = moshi.adapter(HealthCheck.class);
  }

  public Either<NotbankException, Void> toNone(String jsonStr) {
    return errorHandler.toNone(jsonStr);
  }

  private <T> Either<NotbankException, T> handle(String jsonString, JsonAdapter<T> jsonAdapter) {
    return errorHandler.handleAndThen(jsonAdapter).apply(jsonString);
  }

  public Either<NotbankException, Pong> toPong(String jsonStr) {
    return handle(jsonStr, pingJsonAdapter);
  }

  public Either<NotbankException, HealthCheck> toHealthCheck(String jsonStr) {
    return handle(jsonStr, healthCheckJsonAdapter);
  }
}
