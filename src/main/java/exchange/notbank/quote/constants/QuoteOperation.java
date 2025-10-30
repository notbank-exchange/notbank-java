package exchange.notbank.quote.constants;

public enum QuoteOperation {
  BUY(1),
  SELL(2),
  CONVERSION(3);

  public final Integer value;

  QuoteOperation(Integer value) {
    this.value = value;
  }
}
