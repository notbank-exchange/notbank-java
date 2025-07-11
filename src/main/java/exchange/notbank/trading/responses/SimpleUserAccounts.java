package exchange.notbank.trading.responses;

import java.util.List;

public class SimpleUserAccounts {
  public final List<SimpleUserAccount> accounts;

  public SimpleUserAccounts(List<SimpleUserAccount> accounts) {
    this.accounts = accounts;
  }
}
