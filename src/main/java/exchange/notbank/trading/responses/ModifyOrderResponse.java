package exchange.notbank.trading.responses;

import com.squareup.moshi.Json;

public class ModifyOrderResponse {
  @Json(name = "result")
  public final Boolean result;
  @Json(name = "errormsg")
  public final String errorMessage;
  @Json(name = "errorcode")
  public final Integer errorCode;
  @Json(name = "detail")
  public final String detail;

  public ModifyOrderResponse(Boolean result, String errorMessage, Integer errorCode, String detail) {
    this.result = result;
    this.errorMessage = errorMessage;
    this.errorCode = errorCode;
    this.detail = detail;
  }

  @Override
  public String toString() {
    return "ModifyOrder [result=" + result + ", errorMessage=" + errorMessage + ", errorCode=" + errorCode
        + ", detail=" + detail + "]";
  }
}
