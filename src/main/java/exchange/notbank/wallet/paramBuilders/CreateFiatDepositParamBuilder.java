package exchange.notbank.wallet.paramBuilders;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;
import exchange.notbank.wallet.constants.DepositPaymentMethod;

public class CreateFiatDepositParamBuilder implements ParamBuilder {
  protected final Map<String, Object> params;
  protected HttpConfiguration httpConfiguration;

  public CreateFiatDepositParamBuilder(Integer accountId, DepositPaymentMethod depositPaymentMethod, String currency,
      BigDecimal amount) {
    this(accountId.toString(), depositPaymentMethod, currency, amount);
  }

  public CreateFiatDepositParamBuilder(String accountId, DepositPaymentMethod depositPaymentMethod, String currency,
      BigDecimal amount) {
    this.httpConfiguration = new HttpConfiguration();
    this.params = new HashMap<>();
    this.params.put("account_id", accountId);
    this.params.put("payment_method", depositPaymentMethod);
    this.params.put("currency", currency);
    this.params.put("amount", amount.toPlainString());
  }

  public CreateFiatDepositParamBuilder bankAccountId(String value) {
    this.params.put("bank_account_id", value);
    return this;
  }

  public CreateFiatDepositParamBuilder bankAccountId(UUID value) {
    this.params.put("bank_account_id", value.toString());
    return this;
  }

  public CreateFiatDepositParamBuilder voucher(String value) {
    this.params.put("voucher", value);
    return this;
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public CreateFiatDepositParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
