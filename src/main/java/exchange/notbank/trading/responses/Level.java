package exchange.notbank.trading.responses;

import java.math.BigDecimal;

public class Level {
  public final BigDecimal quantity;
  public final BigDecimal price;

  public Level(BigDecimal quantity, BigDecimal price) {
    this.quantity = quantity;
    this.price = price;
  }

  @Override
  public String toString() {
    return "Level [quantity=" + quantity + ", price=" + price + "]";
  }
}
