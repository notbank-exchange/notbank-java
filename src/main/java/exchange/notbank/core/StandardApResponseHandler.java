package exchange.notbank.core;

import java.io.IOException;
import java.util.Optional;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.Moshi;

import exchange.notbank.core.responses.StandardResponse;
import io.vavr.control.Either;

public class StandardApResponseHandler {
  private final JsonAdapter<StandardResponse> standardResponseJsonAdapter;

  public StandardApResponseHandler(
      JsonAdapter<StandardResponse> standardResponseJsonAdapter) {
    this.standardResponseJsonAdapter = standardResponseJsonAdapter;
  }

  public static class Factory {
    public static StandardApResponseHandler create(Moshi moshi) {
      return new StandardApResponseHandler(moshi.adapter(StandardResponse.class));
    }
  }

  public Either<NotbankException, String> handle(String jsonStr) {
    var standardResponse = parseStandardResponse(jsonStr);
    if (standardResponse.isEmpty()) {
      return Either.right(jsonStr);
    }
    var error = NotbankException.Factory.create(standardResponse.get());
    return Either.left(error);
  }

  private Optional<StandardResponse> parseStandardResponse(String jsonError) {
    try {
      var errorResponse = standardResponseJsonAdapter.fromJson(jsonError);
      if (errorResponse.result != null
          && errorResponse.result == false
          && errorResponse.errorCode != null
          && errorResponse.errorCode != 0) {
        return Optional.of(errorResponse);
      }
    } catch (IOException | JsonDataException e) {
      // means is not this kind of error, is another structure instead
    }
    return Optional.empty();
  }
}
