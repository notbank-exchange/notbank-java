package exchange.notbank.core.responses;

import com.squareup.moshi.Json;

public class StandardResponse {
  public final Boolean result;
  @Json(name = "errormsg")
  public final String errorMessage;
  @Json(name = "errorcode")
  public final Integer errorCode;
  public final String detail;

  public StandardResponse(Boolean result, String errorMessage, Integer errorCode, String detail) {
    this.result = result;
    this.errorMessage = errorMessage;
    this.errorCode = errorCode;
    this.detail = detail;
  }

  @Override
  public String toString() {
    return "StandardResponse [result=" + result + ", errorMessage=" + errorMessage + ", errorCode=" + errorCode
        + ", detail=" + detail + "]";
  }
}