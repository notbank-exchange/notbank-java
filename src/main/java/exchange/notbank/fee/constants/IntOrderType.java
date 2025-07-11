package exchange.notbank.fee.constants;

public enum IntOrderType {
  UNKNOWN(0),
  MARKET(1),
  LIMIT(2),
  STOP_MARKET(3),
  STOP_LIMIT(4),
  TRAILING_STOP_MARKET(5),
  TRAILING_STOP_LIMIT(6),
  BLOCK_TRADE(7);

  public final Integer value;

  IntOrderType(Integer value) {
    this.value = value;
  }
}
