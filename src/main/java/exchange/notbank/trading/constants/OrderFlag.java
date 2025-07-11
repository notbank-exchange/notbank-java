package exchange.notbank.trading.constants;

public enum OrderFlag {
  NO_ACCOUNT_RISK_CHECK("NoAccountRiskCheck", 1),
  ADDED_TO_BOOK("AddedToBook", 2),
  REMOVED_FROM_BOOK("RemovedFromBook", 4),
  POST_ONLY("PostOnly", 8),
  LIQUIDATION("Liquidation", 16),
  REVERSE_MARGIN_POSITION("ReverseMarginPosition", 32);

  public final String name;
  public final Integer code;

  OrderFlag(String name, Integer code) {
    this.name = name;
    this.code = code;
  }

  @Override
  public String toString() {
    return this.name;
  }
}
