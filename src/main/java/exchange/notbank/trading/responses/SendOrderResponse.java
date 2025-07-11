package exchange.notbank.trading.responses;

import exchange.notbank.trading.constants.SendOrderResponseStatus;
import com.squareup.moshi.Json;

public class SendOrderResponse {
  public final SendOrderResponseStatus status;
  @Json(name = "errormsg")
  public final String errorMessage;
  @Json(name = "OrderId")
  public final Long orderId;

  public SendOrderResponse(SendOrderResponseStatus status, String errorMessage, Long orderId) {
    this.status = status;
    this.errorMessage = errorMessage;
    this.orderId = orderId;
  }

  @Override
  public String toString() {
    return "SendOrderResponse [status=" + status + ", errorMessage=" + errorMessage + ", orderId=" + orderId + "]";
  }
}
