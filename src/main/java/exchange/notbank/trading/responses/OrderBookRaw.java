package exchange.notbank.trading.responses;

import java.math.BigDecimal;
import java.util.List;

public class OrderBookRaw {
  public final Long timestamp;
  public final List<List<BigDecimal>> bids;
  public final List<List<BigDecimal>> asks;

  public OrderBookRaw(Long timestamp, List<List<BigDecimal>> bids, List<List<BigDecimal>> asks) {
    this.timestamp = timestamp;
    this.bids = bids;
    this.asks = asks;
  }
}
