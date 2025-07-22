package exchange.notbank.wallet.constants;

public enum WithdrawPaymentMethod {
  BANK_TRANSFER(1);

  public final Integer value;

  WithdrawPaymentMethod(Integer value) {
    this.value = value;
  }
}
