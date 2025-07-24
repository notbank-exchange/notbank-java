package exchange.notbank.wallet.paramBuilders;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;
import exchange.notbank.wallet.constants.DepositPaymentMethod;

public class CreateFiatWithdrawParamBuilder implements ParamBuilder {
  protected final Map<String, Object> params;
  protected HttpConfiguration httpConfiguration;

  public CreateFiatWithdrawParamBuilder(Integer accountId, DepositPaymentMethod paymentMethod, String currency,
      BigDecimal amount) {
    this(accountId.toString(), paymentMethod, currency, amount);
  }

  public CreateFiatWithdrawParamBuilder(String accountId, DepositPaymentMethod paymentMethod, String currency,
      BigDecimal amount) {
    this.httpConfiguration = new HttpConfiguration();
    this.params = new HashMap<>();
    this.params.put("account_id", accountId);
    this.params.put("payment_method", paymentMethod);
    this.params.put("currency", currency);
    this.params.put("amount", amount.toPlainString());
  }

  public CreateFiatWithdrawParamBuilder bankAccountId(String value) {
    this.params.put("bank_account_id", value);
    return this;
  }

  public CreateFiatWithdrawParamBuilder cbu(String value) {
    this.params.put("cbu", value);
    return this;
  }

  public CreateFiatWithdrawParamBuilder personType(String value) {
    this.params.put("person_type", value);
    return this;
  }

  public CreateFiatWithdrawParamBuilder cuit(String value) {
    this.params.put("cuit", value);
    return this;
  }

  public CreateFiatWithdrawParamBuilder name(String value) {
    this.params.put("name", value);
    return this;
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public CreateFiatWithdrawParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
