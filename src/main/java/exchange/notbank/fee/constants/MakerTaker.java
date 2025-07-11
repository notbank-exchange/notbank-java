package exchange.notbank.fee.constants;

public enum MakerTaker {
  Unknown(0),
  Maker(1),
  Taker(2);

  public final Integer value;

  MakerTaker(Integer value) {
    this.value = value;
  }
}
