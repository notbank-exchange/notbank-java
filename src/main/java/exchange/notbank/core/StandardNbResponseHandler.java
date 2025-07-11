package exchange.notbank.core;

import java.io.IOException;
import java.util.Optional;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.Moshi;

import exchange.notbank.core.responses.NbResponse;
import io.vavr.control.Either;

public class StandardNbResponseHandler {
  private final JsonAdapter<NbResponse> responseJsonAdapter;

  public StandardNbResponseHandler(
      JsonAdapter<NbResponse> responseJsonAdapter) {
    this.responseJsonAdapter = responseJsonAdapter;
  }

  public static class Factory {
    public static StandardNbResponseHandler create(Moshi moshi) {
      return new StandardNbResponseHandler(moshi.adapter(NbResponse.class));
    }
  }

  public Either<NotbankException, String> handle(String jsonStr) {
    var nbResponse = getError(jsonStr);
    if (nbResponse.isEmpty()) {
      // means is not this kind of error, is another structure instead
      return Either.right(jsonStr);
    }
    var error = NotbankException.Factory.create(nbResponse.get());
    return Either.left(error);
  }

  private Optional<NbResponse> getError(String jsonError) {
    try {
      var standardResponse = Optional.ofNullable(responseJsonAdapter.fromJson(jsonError));
      if (standardResponse.isPresent() && standardResponse.get().status != null
          && standardResponse.get().status.equals("success")) {
        return Optional.empty();
      }
      return standardResponse;
    } catch (IOException | JsonDataException e) {
      return Optional.empty();
    }
  }
}
