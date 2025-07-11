package exchange.notbank.core.websocket;

import java.util.Optional;
import java.util.function.Function;

import exchange.notbank.core.NotbankException;

import io.vavr.control.Either;

public class RequestHandler {
  public final String eventName;
  public final Function<Either<NotbankException, String>, Optional<String>> eventHandler;

  public RequestHandler(String eventName, Function<Either<NotbankException, String>, Optional<String>> eventHandler) {
    this.eventName = eventName;
    this.eventHandler = eventHandler;
  }
}
