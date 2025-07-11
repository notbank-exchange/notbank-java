package exchange.notbank.trading.responses;

import com.squareup.moshi.Json;

public class CancelReplaceOrderResponse {
  @Json(name = "replacementOrderId")
  public final Integer replacementOrderId;
  @Json(name = "replacementClOrdId")
  public final Integer replacementClientOrderId;
  @Json(name = "origOrderId")
  public final Integer originalOrderId;
  @Json(name = "origClOrdId")
  public final Integer originalClientOrderId;

  public CancelReplaceOrderResponse(Integer replacementOrderId, Integer replacementClientOrderId,
      Integer originalOrderId, Integer originalClientOrderId) {
    this.replacementOrderId = replacementOrderId;
    this.replacementClientOrderId = replacementClientOrderId;
    this.originalOrderId = originalOrderId;
    this.originalClientOrderId = originalClientOrderId;
  }

  @Override
  public String toString() {
    return "CancelReplaceOrderResponse [replacementOrderId=" + replacementOrderId + ", replacementClientOrderId="
        + replacementClientOrderId + ", originalOrderId=" + originalOrderId + ", originalClientOrderId="
        + originalClientOrderId + "]";
  }
}
