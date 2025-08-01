package exchange.notbank.wallet.constants;

public enum TransactionSubType {
  OTHER(0),
  PAYOUT(1),
  PAYIN(2),
  DEPOSIT(3),
  WITHDRAW(4),
  BANK_TO_EXCHANGE(5),
  EXCHANGE_TO_BANK(6),
  TRADE(7),
  PAYMENT(8),
  SIMPLE(9),
  RECTIFICATION(10);

  public final Integer value;

  TransactionSubType(Integer value) {
    this.value = value;
  }
}
