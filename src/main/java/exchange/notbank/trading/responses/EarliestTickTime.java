package exchange.notbank.trading.responses;


public class EarliestTickTime {
  public final Long timestamp;

  public EarliestTickTime(Long timestamp) {
    this.timestamp = timestamp;
  }

  @Override
  public String toString() {
    return "EarliestTickTime [timestamp=" + timestamp + "]";
  }
}
