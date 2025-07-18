package exchange.notbank.wallet.constants;

public enum TransactionStatus {
  OTHER(0),
  CREATED(1),
  PENDING(2),
  SUCCESS(3),
  FAILED(4),
  ROLLED_BACK(5);

  public final Integer value;

  TransactionStatus(Integer value) {
    this.value = value;
  }
}
