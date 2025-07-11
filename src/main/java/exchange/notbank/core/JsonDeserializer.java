package exchange.notbank.core;

import java.io.IOException;
import java.util.function.Function;

import com.squareup.moshi.JsonAdapter;

import io.vavr.control.Either;

public class JsonDeserializer {
  public static final <T> Either<NotbankException, T> deserialize(String jsonStr, JsonAdapter<T> adapter) {
    try {
      var object = adapter.fromJson(jsonStr);
      return Either.right(object);
    } catch (IOException e) {
      return Either
          .left(NotbankException.Factory.create(NotbankException.ErrorType.JSON_FORMAT, "invalid format of json " + jsonStr, e));
    }
  }

  public static final <T> Either<NotbankException, T> deserialize(Either<NotbankException, String> jsonStr,
      Function<String, Either<NotbankException, T>> deserializeFn) {
    if (jsonStr.isLeft()) {
      return Either.left(jsonStr.getLeft());
    }
    return deserializeFn.apply(jsonStr.get());
  }
}
