package exchange.notbank.trading.responses;

import java.math.BigDecimal;

import com.squareup.moshi.Json;

public class Trade2 {
  @Json(name = "0")
  public final Integer tradeId;
  @Json(name = "1")
  public final Integer instrumentId;
  @Json(name = "2")
  public final BigDecimal quantity;
  @Json(name = "3")
  public final BigDecimal price;
  @Json(name = "4")
  public final Integer order1Id;
  @Json(name = "5")
  public final Integer order2Id;
  @Json(name = "6")
  public final Long tradetime;
  @Json(name = "7")
  public final Integer direction;
  @Json(name = "8")
  public final Integer tikerSide;
  @Json(name = "9")
  public final Boolean blockTrade;
  @Json(name = "10")
  public final Integer orderClientId; // The client-supplied order ID for the trade. Internal logic determines whether the program reports the order1ClientId or the order2ClientId.

  public Trade2(Integer tradeId, Integer instrumentId, BigDecimal quantity, BigDecimal price, Integer order1Id,
      Integer order2Id, Long tradetime, Integer direction, Integer tikerSide, Boolean blockTrade, Integer orderClientId) {
    this.tradeId = tradeId;
    this.instrumentId = instrumentId;
    this.quantity = quantity;
    this.price = price;
    this.order1Id = order1Id;
    this.order2Id = order2Id;
    this.tradetime = tradetime;
    this.direction = direction;
    this.tikerSide = tikerSide;
    this.blockTrade = blockTrade;
    this.orderClientId = orderClientId;
  }

  @Override
  public String toString() {
    return "Trade [tradeId=" + tradeId + ", instrumentId=" + instrumentId + ", quantity=" + quantity + ", price="
        + price + ", order1Id=" + order1Id + ", order2Id=" + order2Id + ", tradetime=" + tradetime + ", direction=" + direction
        + ", tikerSide=" + tikerSide + ", blockTrade=" + blockTrade + ", orderClientId=" + orderClientId + "]";
  }
}
