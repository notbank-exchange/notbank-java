package exchange.notbank.wallet.constants;

public enum Direction {
  OTHER(0),
  IN(1),
  OUT(2);

  public final Integer value;

  Direction(Integer value) {
    this.value = value;
  }
}
