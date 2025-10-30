package exchange.notbank.wallet.paramBuilders;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;

public class TransferFundsParamBuilder implements ParamBuilder {
  protected final Map<String, Object> params;
  protected HttpConfiguration httpConfiguration;

  public TransferFundsParamBuilder(Integer senderAccountId, Integer receiverAccountId, String currencyName,
      BigDecimal amount) {
    this.httpConfiguration = new HttpConfiguration();
    this.params = new HashMap<>();
    this.params.put("sender_account_id", senderAccountId);
    this.params.put("receiver_account_id", receiverAccountId);
    this.params.put("currency_name", currencyName);
    this.params.put("amount", amount);
  }

  public TransferFundsParamBuilder notes(String value) {
    this.params.put("notes", value);
    return this;
  }

  public TransferFundsParamBuilder otp(String value) {
    this.params.put("otp", value);
    return this;
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public TransferFundsParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }

}
