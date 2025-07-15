package exchange.notbank.subscription.responses;

import java.math.BigDecimal;

public class Level2 {
  public final Long marketDataUpdateId;
  public final Integer numberOfAccounts;
  public final Long actionDateTime; // in Posix format X 1000
  public final Integer actionType; // 0 (New), 1 (Update), 2 (Delete)
  public final BigDecimal lastTradePrice;
  public final Long numberOfOrders;
  public final BigDecimal price;
  public final Integer productPairCode;
  public final BigDecimal quantity;
  public final Integer side;

  public Level2(Long marketDataUpdateId, Integer numberOfAccounts, Long actionDateTime, Integer actionType,
      BigDecimal lastTradePrice, Long numberOfOrders, BigDecimal price, Integer productPairCode, BigDecimal quantity,
      Integer side) {
    this.marketDataUpdateId = marketDataUpdateId;
    this.numberOfAccounts = numberOfAccounts;
    this.actionDateTime = actionDateTime;
    this.actionType = actionType;
    this.lastTradePrice = lastTradePrice;
    this.numberOfOrders = numberOfOrders;
    this.price = price;
    this.productPairCode = productPairCode;
    this.quantity = quantity;
    this.side = side;
  }

  @Override
  public String toString() {
    return "Level2 [marketDataUpdateId=" + marketDataUpdateId + ", numberOfAccounts=" + numberOfAccounts
        + ", actionDateTime=" + actionDateTime + ", actionType=" + actionType + ", lastTradePrice=" + lastTradePrice
        + ", numberOfOrders=" + numberOfOrders + ", price=" + price + ", productPairCode=" + productPairCode
        + ", quantity=" + quantity + ", side=" + side + "]";
  }
}
