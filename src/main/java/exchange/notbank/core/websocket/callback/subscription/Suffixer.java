package exchange.notbank.core.websocket.callback.subscription;

import java.util.Optional;

import io.vavr.control.Either;

public class Suffixer {
  public static final String EMPTY_SUFFIX = "";

  public static String toSuffix(Optional<String> optSuffix) {
    return optSuffix.isPresent()
        ? toSuffix(optSuffix.get())
        : EMPTY_SUFFIX;
  }

  public static String toSuffix(Either<String, String> optSuffix) {
    return optSuffix.isRight()
        ? toSuffix(optSuffix.get())
        : EMPTY_SUFFIX;
  }

  public static String toSuffix(String suffixPart) {
    return "_" + suffixPart;
  }
}
