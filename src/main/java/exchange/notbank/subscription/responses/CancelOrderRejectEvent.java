package exchange.notbank.subscription.responses;

import exchange.notbank.trading.constants.OrderType;
import com.squareup.moshi.Json;

public class CancelOrderRejectEvent {
  @Json(name = "OMSId")
  public final Integer omsId;
  @Json(name = "AccountId")
  public final Integer accountId;
  @Json(name = "OrderId")
  public final Integer orderId;
  @Json(name = "OrderRevision")
  public final Integer orderRevision;
  @Json(name = "OrderType")
  OrderType orderType;
  @Json(name = "InstrumentId")
  public final Integer instrumentId;
  @Json(name = "Status")
  public final String status;
  @Json(name = "RejectReason")
  public final String rejectReason;

  public CancelOrderRejectEvent(Integer omsId, Integer accountId, Integer orderId, Integer orderRevision,
      OrderType orderType, Integer instrumentId, String status, String rejectReason) {
    this.omsId = omsId;
    this.accountId = accountId;
    this.orderId = orderId;
    this.orderRevision = orderRevision;
    this.orderType = orderType;
    this.instrumentId = instrumentId;
    this.status = status;
    this.rejectReason = rejectReason;
  }
}
