package exchange.notbank.wallet.constants;

public enum TransactionType {
  OTHER(0),
  DEPOSIT(1),
  WITHDRAW(2),
  TRANSFER(3),
  TRADE(4),
  PAYMENT(5),
  RECTIFICATION(6);

  public final Integer value;

  TransactionType(Integer value) {
    this.value = value;
  }
}