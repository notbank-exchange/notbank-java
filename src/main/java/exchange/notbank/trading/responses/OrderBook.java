package exchange.notbank.trading.responses;

import java.util.List;

public class OrderBook {
  public final Long timestamp;
  public final List<Level> bids;
  public final List<Level> asks;

  public OrderBook(Long timestamp, List<Level> bids, List<Level> asks) {
    this.timestamp = timestamp;
    this.bids = bids;
    this.asks = asks;
  }

  @Override
  public String toString() {
    return "OrderBook [timestamp=" + timestamp + ", bids=" + bids + ", asks=" + asks + "]";
  }
}
