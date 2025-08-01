package exchange.notbank.quote.constants;

public enum QuoteState {
  PENDING(0),
  EXECUTED(1),
  INSUFFICIENT_BALANCE(3),
  NOT_EXECUTED(4),
  OTHER(5),
  EXPIRED(6);

  public final Integer value;

  QuoteState(Integer value) {
    this.value = value;
  }
}
