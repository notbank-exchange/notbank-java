package exchange.notbank.trading.responses;

import java.math.BigDecimal;

public class LastTrade {
  public final Long tradeId;
  public final Long instrumentId;
  public final BigDecimal quantity;
  public final BigDecimal price;
  public final Long order1Id;
  public final Long order2Id;
  public final Long tradetime;
  public final Integer direction;
  public final Integer takerSide;
  public final Boolean blockTrade;
  public final Long orderClientId;

  public LastTrade(Long tradeId, Long instrumentId, BigDecimal quantity, BigDecimal price, Long order1Id,
  Long order2Id, Long tradetime, Integer direction, Integer takerSide, Boolean blockTrade, Long orderClientId) {
    this.tradeId = tradeId;
    this.instrumentId = instrumentId;
    this.quantity = quantity;
    this.price = price;
    this.order1Id = order1Id;
    this.order2Id = order2Id;
    this.tradetime = tradetime;
    this.direction = direction;
    this.takerSide = takerSide;
    this.blockTrade = blockTrade;
    this.orderClientId = orderClientId;
  }

  @Override
  public String toString() {
    return "LastTrade [tradeId=" + tradeId + ", instrumentId=" + instrumentId + ", quantity=" + quantity + ", price="
        + price + ", order1Id=" + order1Id + ", order2Id=" + order2Id + ", tradetime=" + tradetime + ", direction=" + direction
        + ", takerSide=" + takerSide + ", blockTrade=" + blockTrade + ", orderClientId=" + orderClientId + "]";
  }
}
