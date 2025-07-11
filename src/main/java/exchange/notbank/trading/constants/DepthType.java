package exchange.notbank.trading.constants;

public enum DepthType {
  ONLY_BESTS(1),
  DEPTH(2);

  public final Integer value;

  DepthType(Integer value) {
    this.value = value;
  }
}
