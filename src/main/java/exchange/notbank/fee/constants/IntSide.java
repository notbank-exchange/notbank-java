package exchange.notbank.fee.constants;

public enum IntSide {
  Buy(0),
  Sell(1),
  Short(2),
  Unknown(3);

  public final Integer value;

  IntSide(Integer value) {
    this.value = value;
  }
}
