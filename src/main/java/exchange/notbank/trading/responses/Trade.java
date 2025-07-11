package exchange.notbank.trading.responses;

import java.math.BigDecimal;

import exchange.notbank.trading.constants.OrderSide;
import exchange.notbank.trading.constants.OrderType;
import exchange.notbank.subscription.responses.AccountEvent;
import exchange.notbank.trading.constants.Direction;
import exchange.notbank.trading.constants.LiquidityProviderType;
import com.squareup.moshi.Json;

public class Trade implements AccountEvent {
  @Json(name = "OMSId")
  public final Integer omsId;
  @Json(name = "ExecutionId")
  public final Integer executionId;
  @Json(name = "TradeId")
  public final Long tradeId;
  @Json(name = "OrderId")
  public final Long orderId;
  @Json(name = "AccountId")
  public final Integer accountId;
  @Json(name = "AccountName")
  public final String accountName;
  @Json(name = "SubAccountId")
  public final Integer subAccountId;
  @Json(name = "ClientOrderId")
  public final Long clientOrderId;
  @Json(name = "InstrumentId")
  public final Integer instrumentId;
  @Json(name = "Side")
  public final OrderSide side;
  @Json(name = "OrderType")
  public final OrderType orderType;
  @Json(name = "Quantity")
  public final BigDecimal quantity;
  @Json(name = "RemainingQuantity")
  public final BigDecimal remainingQuantity;
  @Json(name = "Price")
  public final BigDecimal price;
  @Json(name = "Value")
  public final BigDecimal value;
  @Json(name = "CounterParty")
  public final String counterParty;
  @Json(name = "OrderTradeRevision")
  public final Integer orderTradeRevision;
  @Json(name = "Direction")
  public final Direction direction;
  @Json(name = "IsBlockTrade")
  public final Boolean isBlockTrade;
  @Json(name = "Fee")
  public final BigDecimal fee;
  @Json(name = "FeeProductId")
  public final Integer feeProductId;
  @Json(name = "OrderOriginator")
  public final Integer orderOriginator;
  @Json(name = "UserName")
  public final String userName;
  @Json(name = "TradeTimeMS")
  public final Long tradeTimeMs;
  @Json(name = "MakerTaker")
  public final LiquidityProviderType makerTaker;
  @Json(name = "AdapterTradeId")
  public final Integer adapterTradeId;
  @Json(name = "InsideBid")
  public final BigDecimal insideBid;
  @Json(name = "InsideBidSize")
  public final BigDecimal insideBidSize;
  @Json(name = "InsideAsk")
  public final BigDecimal insideAsk;
  @Json(name = "InsideAskSize")
  public final BigDecimal insideAskSize;
  @Json(name = "IsQuote")
  public final Boolean isQuote;
  @Json(name = "CounterPartyClientUserId")
  public final Integer counterPartyClientUserId;
  @Json(name = "NotionalProductId")
  public final Integer notionalProductId;
  @Json(name = "NotionalRate")
  public final BigDecimal notionalRate;
  @Json(name = "NotionalValue")
  public final BigDecimal notionalValue;
  @Json(name = "NotionalHoldAmount")
  public final Integer notionalHoldAmount;
  @Json(name = "TradeTime")
  public final Long tradeTime;

  public Trade(Integer omsId, Integer executionId, Long tradeId, Long orderId, Integer accountId, String accountName,
      Integer subAccountId, Long clientOrderId, Integer instrumentId, OrderSide side, OrderType orderType,
      BigDecimal quantity, BigDecimal remainingQuantity, BigDecimal price, BigDecimal value, String counterParty,
      Integer orderTradeRevision, Direction direction, Boolean isBlockTrade, BigDecimal fee, Integer feeProductId,
      Integer orderOriginator, String userName, Long tradeTimeMs, LiquidityProviderType makerTaker,
      Integer adapterTradeId, BigDecimal insideBid, BigDecimal insideBidSize, BigDecimal insideAsk,
      BigDecimal insideAskSize, Boolean isQuote, Integer counterPartyClientUserId, Integer notionalProductId,
      BigDecimal notionalRate, BigDecimal notionalValue, Integer notionalHoldAmount, Long tradeTime) {
    this.omsId = omsId;
    this.executionId = executionId;
    this.tradeId = tradeId;
    this.orderId = orderId;
    this.accountId = accountId;
    this.accountName = accountName;
    this.subAccountId = subAccountId;
    this.clientOrderId = clientOrderId;
    this.instrumentId = instrumentId;
    this.side = side;
    this.orderType = orderType;
    this.quantity = quantity;
    this.remainingQuantity = remainingQuantity;
    this.price = price;
    this.value = value;
    this.counterParty = counterParty;
    this.orderTradeRevision = orderTradeRevision;
    this.direction = direction;
    this.isBlockTrade = isBlockTrade;
    this.fee = fee;
    this.feeProductId = feeProductId;
    this.orderOriginator = orderOriginator;
    this.userName = userName;
    this.tradeTimeMs = tradeTimeMs;
    this.makerTaker = makerTaker;
    this.adapterTradeId = adapterTradeId;
    this.insideBid = insideBid;
    this.insideBidSize = insideBidSize;
    this.insideAsk = insideAsk;
    this.insideAskSize = insideAskSize;
    this.isQuote = isQuote;
    this.counterPartyClientUserId = counterPartyClientUserId;
    this.notionalProductId = notionalProductId;
    this.notionalRate = notionalRate;
    this.notionalValue = notionalValue;
    this.notionalHoldAmount = notionalHoldAmount;
    this.tradeTime = tradeTime;
  }

  @Override
  public String toString() {
    return "Trade [omsId=" + omsId + ", executionId=" + executionId + ", tradeId=" + tradeId + ", orderId=" + orderId
        + ", accountId=" + accountId + ", accountName=" + accountName + ", subAccountId=" + subAccountId
        + ", clientOrderId=" + clientOrderId + ", instrumentId=" + instrumentId + ", side=" + side + ", orderType="
        + orderType + ", quantity=" + quantity.toPlainString() + ", remainingQuantity=" + remainingQuantity + ", price="
        + price.toPlainString()
        + ", value=" + value.toPlainString() + ", counterParty=" + counterParty + ", orderTradeRevision="
        + orderTradeRevision
        + ", direction=" + direction + ", isBlockTrade=" + isBlockTrade + ", fee=" + fee.toPlainString()
        + ", feeProductId="
        + feeProductId + ", orderOriginator=" + orderOriginator + ", userName=" + userName + ", tradeTimeMs="
        + tradeTimeMs + ", makerTaker=" + makerTaker + ", adapterTradeId=" + adapterTradeId + ", insideBid="
        + insideBid.toPlainString()
        + ", insideBidSize=" + insideBidSize.toPlainString() + ", insideAsk=" + insideAsk.toPlainString()
        + ", insideAskSize=" + insideAskSize.toPlainString()
        + ", isQuote=" + isQuote + ", counterPartyClientUserId=" + counterPartyClientUserId + ", notionalProductId="
        + notionalProductId + ", notionalRate=" + notionalRate.toPlainString() + ", notionalValue="
        + notionalValue.toPlainString()
        + ", notionalHoldAmount=" + notionalHoldAmount + ", tradeTime=" + tradeTime + "]";
  }

}
