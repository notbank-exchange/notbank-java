package exchange.notbank.trading.responses;

import java.math.BigDecimal;

public class SocketTrade {
  public final Integer tradeId;
  public final Integer instrumentId;
  public final BigDecimal quantity;
  public final BigDecimal price;
  public final Integer order1Id;
  public final Integer order2Id;
  public final Long tradetime;
  public final Integer direction;
  public final Integer tikerSide;
  public final Boolean blockTrade;
  public final Integer orderClientId;

  public SocketTrade(Integer tradeId, Integer instrumentId, BigDecimal quantity, BigDecimal price, Integer order1Id,
      Integer order2Id, Long tradetime, Integer direction, Integer tikerSide, Boolean blockTrade,
      Integer orderClientId) {
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
        + price + ", order1Id=" + order1Id + ", order2Id=" + order2Id + ", tradetime=" + tradetime + ", direction="
        + direction
        + ", tikerSide=" + tikerSide + ", blockTrade=" + blockTrade + ", orderClientId=" + orderClientId + "]";
  }
}
