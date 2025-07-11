package exchange.notbank.core.websocket;

import java.util.function.Function;

import exchange.notbank.core.JsonDeserializer;
import exchange.notbank.core.NotbankException;
import exchange.notbank.core.responses.MessageFrame;
import com.squareup.moshi.JsonAdapter;

import io.vavr.control.Either;

public class PayloadDeserializer {
  public static <T> Function<MessageFrame, Either<NotbankException, T>> newDeserializer(PayloadGetter payloadGetter,
      JsonAdapter<T> jsonAdapter) {
    return messageFrame -> {
      var payload = payloadGetter.get(messageFrame);
      if (payload.isLeft()) {
        return Either.left(payload.getLeft());
      }
      return JsonDeserializer.deserialize(payload.get(), jsonAdapter);
    };
  }
}
