package exchange.notbank.wallet.constants;

public enum WithdrawalPaymentMethod {
  BANK_TRANSFER(1);

  public final Integer value;

  WithdrawalPaymentMethod(Integer value) {
    this.value = value;
  }
}
