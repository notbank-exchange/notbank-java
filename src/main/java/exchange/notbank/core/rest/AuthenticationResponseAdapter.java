package exchange.notbank.core.rest;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import exchange.notbank.core.ErrorHandler;
import exchange.notbank.core.NotbankException;
import exchange.notbank.users.responses.AuthenticateUserResponse;
import io.vavr.control.Either;

public class AuthenticationResponseAdapter {
  private final ErrorHandler errorHandler;
  private final JsonAdapter<AuthenticateUserResponse> authenticateUserResponseJsonAdapter;

  public AuthenticationResponseAdapter(Moshi moshi) {
    this.errorHandler = ErrorHandler.Factory.createApErrorHandler(moshi);
    this.authenticateUserResponseJsonAdapter = moshi.adapter(AuthenticateUserResponse.class);
  }

  public Either<NotbankException, Void> toNone(String jsonStr) {
    return errorHandler.toNone(jsonStr);
  }

  private <T> Either<NotbankException, T> handle(String jsonString, JsonAdapter<T> jsonAdapter) {
    return errorHandler.handleAndThen(jsonAdapter).apply(jsonString);
  }

  public Either<NotbankException, AuthenticateUserResponse> toResponse(String jsonStr) {
    return handle(jsonStr, authenticateUserResponseJsonAdapter);
  }

}
