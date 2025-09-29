package exchange.notbank.trading.responses;

import com.squareup.moshi.Json;

public class CancelReplaceOrderResponse {
  @Json(name = "ReplacementOrderId")
  public final Long replacementOrderId;
  @Json(name = "ReplacementClOrdId")
  public final Long replacementClientOrderId;
  @Json(name = "OrigOrderId")
  public final Long originalOrderId;
  @Json(name = "OrigClOrdId")
  public final Long originalClientOrderId;

  public CancelReplaceOrderResponse(Long replacementOrderId, Long replacementClientOrderId,
      Long originalOrderId, Long originalClientOrderId) {
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
