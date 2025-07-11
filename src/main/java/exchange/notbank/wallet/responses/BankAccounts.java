package exchange.notbank.wallet.responses;

import java.util.List;

import com.squareup.moshi.Json;


public class BankAccounts {
  public final Integer total;
  @Json(name = "data")
  public final List<BankAccount> accounts;

  public BankAccounts(Integer total, List<BankAccount> accounts) {
    this.total = total;
    this.accounts = accounts;
  }

  @Override
  public String toString() {
    return "BankAccounts [total=" + total + ", accounts=" + accounts + "]";
  }
}
