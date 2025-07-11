package exchange.notbank.core.websocket;

import java.io.IOException;

import exchange.notbank.core.JsonDeserializer;
import exchange.notbank.core.NotbankException;
import exchange.notbank.core.constants.MessageType;
import exchange.notbank.core.responses.MessageFrame;
import exchange.notbank.core.responses.StandardResponse;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonDataException;

import io.vavr.control.Either;

public class MessageFrameAdapter {
  private final JsonAdapter<MessageFrame> messageFrameJsonAdapter;
  private final JsonAdapter<StandardResponse> standardResponseJsonAdapter;

  public MessageFrameAdapter(JsonAdapter<MessageFrame> messageFrameJsonAdapter,
      JsonAdapter<StandardResponse> standardResponseJsonAdapter) {
    this.messageFrameJsonAdapter = messageFrameJsonAdapter;
    this.standardResponseJsonAdapter = standardResponseJsonAdapter;
  }

  public Either<NotbankException, MessageFrame> fromJson(String message) {
    var messageFrame = getMessageFrame(message);
    if (messageFrame.isRight()) {
      return messageFrame;
    }
    var standardError = getStandardError(message);
    if (standardError.isLeft()) {
      return Either.left(standardError.getLeft());
    }
    var error = NotbankException.Factory.create(NotbankException.ErrorType.JSON_FORMAT,
        "failed to parse message from from message " + message);
    return Either.left(error);
  }

  private Either<NotbankException, MessageFrame> getMessageFrame(String message) {
    return JsonDeserializer.deserialize(message, messageFrameJsonAdapter);
  }

  private Either<NotbankException, Void> getStandardError(String message) {
    try {
      var standardResponse = standardResponseJsonAdapter.fromJson(message);
      return Either.left(NotbankException.Factory.create(standardResponse));
    } catch (JsonDataException | IOException e) {
      return Either.right(null);
    }
  }

  public Either<NotbankException, String> getMessageFramePayload(MessageFrame messageFrame) {
    if (messageFrame.messageType.equals(MessageType.ERROR)) {
      return Either.left(NotbankException.Factory.create(NotbankException.ErrorType.RESPONSE_ERROR, messageFrame.payload));
    }
    var standardErrorPayload = getStandardError(messageFrame.payload);
    if (standardErrorPayload.isLeft()) {
      return Either.left(standardErrorPayload.getLeft());
    }
    return Either.right(messageFrame.payload);
  }
}
