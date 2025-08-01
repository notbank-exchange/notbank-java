package exchange.notbank.quote.constants;

public enum QuoteType {
  BUY(1),
  SELL(2),
  CONVERSION(3);

  public final Integer value;

  QuoteType(Integer value) {
    this.value = value;
  }
}
