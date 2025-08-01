package exchange.notbank.wallet.constants;

public enum DepositPaymentMethod {
  BANK_TRANSFER(1),
  WEB_PAY(2);

  public final Integer value;

  DepositPaymentMethod(Integer value) {
    this.value = value;
  }
}
