package exchange.notbank.core.websocket;

import java.io.IOException;

import exchange.notbank.core.NotbankException;
import exchange.notbank.core.constants.MessageType;
import exchange.notbank.core.responses.MessageFrame;
import exchange.notbank.core.responses.StandardResponse;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.Moshi;

import io.vavr.control.Either;

public class PayloadGetter {
  private final JsonAdapter<StandardResponse> standardResponseJsonAdapter;

  public PayloadGetter(JsonAdapter<StandardResponse> standardResponseJsonAdapter) {
    this.standardResponseJsonAdapter = standardResponseJsonAdapter;
  }

  public static class Factory {
    public static PayloadGetter create(Moshi moshi) {
      return new PayloadGetter(moshi.adapter(StandardResponse.class));
    }
  }

  public Either<NotbankException, String> get(MessageFrame messageFrame) {
    try {
      var standardError = standardResponseJsonAdapter.fromJson(messageFrame.payload);
      if (standardError.result != null && standardError.result == false) {
        return Either.left(NotbankException.Factory.create(standardError));
      }
    } catch (IOException | JsonDataException e) {
      // do nothing
    }
    var isErrorType = messageFrame.messageType.equals(MessageType.ERROR);
    if (isErrorType) {
      return Either.left(NotbankException.Factory.create(NotbankException.ErrorType.RESPONSE_ERROR, messageFrame.payload));
    }
    return Either.right(messageFrame.payload);
  }
}
