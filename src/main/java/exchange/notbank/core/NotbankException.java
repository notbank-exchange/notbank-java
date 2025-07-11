package exchange.notbank.core;

import java.util.Optional;

import exchange.notbank.core.responses.NbResponse;
import exchange.notbank.core.responses.StandardResponse;

public class NotbankException extends Exception {
  public final ErrorType errorType;
  public final Optional<Integer> code;
  public final Optional<String> detail;

  public NotbankException(
      ErrorType errorType,
      String message,
      Optional<Integer> code,
      Optional<String> detail

  ) {
    super(message);
    this.errorType = errorType;
    this.code = code;
    this.detail = detail;
  }

  public NotbankException(
      ErrorType errorType,
      String message,
      Throwable cause,
      Optional<Integer> code,
      Optional<String> detail

  ) {
    super(message, cause);
    this.errorType = errorType;
    this.code = code;
    this.detail = detail;
  }

  public static class Factory {
    public static NotbankException create(ErrorType errorType, String message) {
      return new NotbankException(errorType, message, Optional.empty(), Optional.empty());
    }

    public static NotbankException create(ErrorType errorType, String message, Throwable cause) {
      return new NotbankException(errorType, message, cause, Optional.empty(), Optional.empty());
    }

    public static NotbankException create(ErrorType errorType, String message, Integer code) {
      return new NotbankException(errorType, message, Optional.ofNullable(code), Optional.empty());
    }

    public static NotbankException create(ErrorType errorType, String message, Integer code, String detail) {
      return new NotbankException(errorType, message, Optional.ofNullable(code), Optional.ofNullable(detail));
    }

    public static NotbankException create(NbResponse nbResponse) {
      if (nbResponse.message != null) {
        return new NotbankException(
            ErrorType.RESPONSE_ERROR,
            nbResponse.message,
            Optional.empty(),
            Optional.ofNullable(nbResponse.detail));
      }
      return new NotbankException(
          ErrorType.RESPONSE_ERROR,
          Optional.ofNullable(nbResponse.detail).orElse("<no message>"),
          Optional.empty(),
          Optional.empty());
    }

    public static NotbankException create(StandardResponse standardResponse) {
      return new NotbankException(
          ErrorType.RESPONSE_ERROR,
          Optional.ofNullable(standardResponse.errorMessage).orElse("<no message>"),
          Optional.ofNullable(standardResponse.errorCode),
          Optional.ofNullable(standardResponse.detail));
    }
  }

  @Override
  public String toString() {
    var errMsgBuilder = new StringBuilder();
    errMsgBuilder.append("Notbank error ");
    errMsgBuilder.append("(type=");
    errMsgBuilder.append(errorType.value);
    errMsgBuilder.append(")");
    if (code.isPresent()) {
      errMsgBuilder.append(" (code=");
      errMsgBuilder.append(code.get());
      errMsgBuilder.append(")");
    }
    var message = Optional.ofNullable(this.getMessage());
    if (message.isPresent()) {
      errMsgBuilder.append(" ");
      errMsgBuilder.append(message.get());
      if (!message.get().endsWith(".")) {
        errMsgBuilder.append(".");
      }
    }
    if (detail.isPresent()) {
      errMsgBuilder.append(" ");
      errMsgBuilder.append(detail.get());
      if (!detail.get().endsWith(".")) {
        errMsgBuilder.append(".");
      }
    }
    return errMsgBuilder.toString();
  }

  public static enum ErrorType {
    UNKNOWN("unknown"),
    CONFIGURATION_ERROR("configuration error"),
    TIMED_OUT("timed out error"),
    INTERRUPTED("interrupted error"),
    API_CONNECTION("api connection error"),
    HTTP_ERROR("http error"),
    JSON_FORMAT("json format error"),
    RESPONSE_ERROR("server response error"),
    REQUEST_ERROR("request error"),
    EXECUTION_ERROR("execution error");

    public final String value;

    ErrorType(String value) {
      this.value = value;
    }
  }
}
