package exchange.notbank.wallet.paramBuilders;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;

public class CreateCryptoWithdrawParamBuilder implements ParamBuilder {
  protected final Map<String, Object> params;
  protected HttpConfiguration httpConfiguration;

  public CreateCryptoWithdrawParamBuilder(Integer accountId, String currency, String network, String address,
      BigDecimal amount) {
    this(accountId.toString(), currency, network, address, amount);
  }

  public CreateCryptoWithdrawParamBuilder(String accountId, String currency, String network, String address,
      BigDecimal amount) {
    this.httpConfiguration = new HttpConfiguration();
    this.params = new HashMap<>();
    this.params.put("account_id", accountId);
    this.params.put("currency", currency);
    this.params.put("network", network);
    this.params.put("address", address);
    this.params.put("amount", amount.toPlainString());
  }

  public CreateCryptoWithdrawParamBuilder memoOrTag(String value) {
    this.params.put("memo_or_tag", value);
    return this;
  }

  public CreateCryptoWithdrawParamBuilder otp(String value) {
    this.params.put("otp", value);
    return this;
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public CreateCryptoWithdrawParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
