package exchange.notbank.trading.responses;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

import exchange.notbank.trading.constants.ChangeReason;
import exchange.notbank.trading.constants.OrderFlag;
import exchange.notbank.trading.constants.OrderSide;
import exchange.notbank.trading.constants.OrderState;
import exchange.notbank.trading.constants.OrderType;
import com.squareup.moshi.Json;

public class Order {
  @Json(name = "Side")
  public final OrderSide side;
  @Json(name = "OrderId")
  public final Long orderId;
  @Json(name = "Price")
  public final BigDecimal price;
  @Json(name = "Quantity")
  public final BigDecimal quantity;
  @Json(name = "DisplayQuantity")
  public final BigDecimal displayQuantity;
  @Json(name = "Instrument")
  public final Integer instrument;
  @Json(name = "Account")
  public final Integer account;
  @Json(name = "AccountName")
  public final String accountName;
  @Json(name = "OrderType")
  public final OrderType orderType;
  @Json(name = "ClientOrderId")
  public final Long clientOrderId;
  @Json(name = "OrderState")
  public final OrderState orderState;
  @Json(name = "ReceiveTime")
  public final Long receiveTime;
  @Json(name = "ReceiveTimeTicks")
  public final Long receiveTimeTicks;
  @Json(name = "LastUpdatedTime")
  public final Long lastUpdatedTime;
  @Json(name = "LastUpdatedTimeTicks")
  public final Long lastUpdatedTimeTicks;
  @Json(name = "OrigQuantity")
  public final BigDecimal origQuantity;
  @Json(name = "QuantityExecuted")
  public final BigDecimal quantityExecuted;
  @Json(name = "GrossValueExecuted")
  public final BigDecimal grossValueExecuted;
  @Json(name = "ExecutableValue")
  public final BigDecimal executableValue;
  @Json(name = "AvgPrice")
  public final BigDecimal avgPrice;
  @Json(name = "CounterPartyId")
  public final Integer counterPartyId;
  @Json(name = "ChangeReason")
  public final ChangeReason changeReason;
  @Json(name = "OrigOrderId")
  public final Long originalOrderId;
  @Json(name = "OrigClOrdId")
  public final Long originalClientOrderId;
  @Json(name = "EnteredBy")
  public final Integer enteredBy;
  @Json(name = "UserName")
  public final String username;
  @Json(name = "IsQuote")
  public final Boolean isQuote;
  @Json(name = "InsideAsk")
  public final BigDecimal insideAsk;
  @Json(name = "InsideAskSize")
  public final BigDecimal insideAskSize;
  @Json(name = "InsideBid")
  public final BigDecimal insideBid;
  @Json(name = "InsideBidSize")
  public final BigDecimal insideBidSize;
  @Json(name = "LastTradePrice")
  public final BigDecimal lastTradePrice;
  @Json(name = "RejectReason")
  public final String rejectReason;
  @Json(name = "IsLockedIn")
  public final Boolean isLockedIn;
  @Json(name = "CancelReason")
  public final String cancelReason;
  @Json(name = "OrderFlag")
  public final List<OrderFlag> orderFlags;
  @Json(name = "UseMargin")
  public final Boolean useMargin;
  @Json(name = "StopPrice")
  public final BigDecimal stopPrice;
  @Json(name = "PegPriceType")
  public final String pegPriceType;
  @Json(name = "PegOffset")
  public final BigDecimal pegOffset;
  @Json(name = "PegLimitOffset")
  public final BigDecimal pegLimitOffset;
  @Json(name = "IpAddress")
  public final String ipAddress;
  @Json(name = "IPv6a")
  public final BigInteger ipV6a;
  @Json(name = "IPv6b")
  public final BigInteger ipV6b;
  @Json(name = "ClientOrderIdUuid")
  public final UUID clientOrderIdUuid;
  @Json(name = "OMSId")
  public final Integer omsId;

  public Order(OrderSide side, Long orderId, BigDecimal price, BigDecimal quantity, BigDecimal displayQuantity,
      Integer instrument, Integer account, String accountName, OrderType orderType, Long clientOrderId,
      OrderState orderState, Long receiveTime, Long receiveTimeTicks, Long lastUpdatedTime, Long lastUpdatedTimeTicks,
      BigDecimal origQuantity, BigDecimal quantityExecuted, BigDecimal grossValueExecuted, BigDecimal executableValue,
      BigDecimal avgPrice, Integer counterPartyId, ChangeReason changeReason, Long originalOrderId,
      Long originalClientOrderId, Integer enteredBy, String userName, Boolean isQuote, BigDecimal insideAsk,
      BigDecimal insideAskSize, BigDecimal insideBid, BigDecimal insideBidSize, BigDecimal lastTradePrice,
      String rejectReason, Boolean isLockedIn, String cancelReason, List<OrderFlag> orderFlags, Boolean useMargin,
      BigDecimal stopPrice, String pegPriceType, BigDecimal pegOffset, BigDecimal pegLimitOffset, String ipAddress,
      BigInteger ipV6a, BigInteger ipV6b, UUID clientOrderIdUuid, Integer omsId) {
    this.side = side;
    this.orderId = orderId;
    this.price = price;
    this.quantity = quantity;
    this.displayQuantity = displayQuantity;
    this.instrument = instrument;
    this.account = account;
    this.accountName = accountName;
    this.orderType = orderType;
    this.clientOrderId = clientOrderId;
    this.orderState = orderState;
    this.receiveTime = receiveTime;
    this.receiveTimeTicks = receiveTimeTicks;
    this.lastUpdatedTime = lastUpdatedTime;
    this.lastUpdatedTimeTicks = lastUpdatedTimeTicks;
    this.origQuantity = origQuantity;
    this.quantityExecuted = quantityExecuted;
    this.grossValueExecuted = grossValueExecuted;
    this.executableValue = executableValue;
    this.avgPrice = avgPrice;
    this.counterPartyId = counterPartyId;
    this.changeReason = changeReason;
    this.originalOrderId = originalOrderId;
    this.originalClientOrderId = originalClientOrderId;
    this.enteredBy = enteredBy;
    this.username = userName;
    this.isQuote = isQuote;
    this.insideAsk = insideAsk;
    this.insideAskSize = insideAskSize;
    this.insideBid = insideBid;
    this.insideBidSize = insideBidSize;
    this.lastTradePrice = lastTradePrice;
    this.rejectReason = rejectReason;
    this.isLockedIn = isLockedIn;
    this.cancelReason = cancelReason;
    this.orderFlags = orderFlags;
    this.useMargin = useMargin;
    this.stopPrice = stopPrice;
    this.pegPriceType = pegPriceType;
    this.pegOffset = pegOffset;
    this.pegLimitOffset = pegLimitOffset;
    this.ipAddress = ipAddress;
    this.ipV6a = ipV6a;
    this.ipV6b = ipV6b;
    this.clientOrderIdUuid = clientOrderIdUuid;
    this.omsId = omsId;
  }

  @Override
  public String toString() {
    return "Order [side=" + side + ", orderId=" + orderId + ", price=" + price + ", quantity=" + quantity
        + ", displayQuantity=" + displayQuantity + ", instrument=" + instrument + ", account=" + account
        + ", accountName=" + accountName + ", orderType=" + orderType + ", clientOrderId=" + clientOrderId
        + ", orderState=" + orderState + ", receiveTime=" + receiveTime + ", receiveTimeTicks=" + receiveTimeTicks
        + ", lastUpdatedTime=" + lastUpdatedTime + ", lastUpdatedTimeTicks=" + lastUpdatedTimeTicks + ", origQuantity="
        + origQuantity + ", quantityExecuted=" + quantityExecuted + ", grossValueExecuted=" + grossValueExecuted
        + ", executableValue=" + executableValue + ", avgPrice=" + avgPrice + ", counterPartyId=" + counterPartyId
        + ", changeReason=" + changeReason + ", originalOrderId=" + originalOrderId + ", originalClientOrderId="
        + originalClientOrderId + ", enteredBy=" + enteredBy + ", userName=" + username + ", isQuote=" + isQuote
        + ", insideAsk=" + insideAsk + ", insideAskSize=" + insideAskSize + ", insideBid=" + insideBid
        + ", insideBidSize=" + insideBidSize + ", lastTradePrice=" + lastTradePrice + ", rejectReason=" + rejectReason
        + ", isLockedIn=" + isLockedIn + ", cancelReason=" + cancelReason + ", orderFlags=" + orderFlags + ", useMargin="
        + useMargin + ", stopPrice=" + stopPrice + ", pegPriceType=" + pegPriceType + ", pegOffset=" + pegOffset
        + ", pegLimitOffset=" + pegLimitOffset + ", ipAddress=" + ipAddress + ", ipV6a=" + ipV6a + ", ipV6b=" + ipV6b
        + ", clientOrderIdUuid=" + clientOrderIdUuid + ", omsId=" + omsId + "]";
  }

}
