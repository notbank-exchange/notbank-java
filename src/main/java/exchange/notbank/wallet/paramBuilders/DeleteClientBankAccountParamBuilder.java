package exchange.notbank.wallet.paramBuilders;

import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;

public class DeleteClientBankAccountParamBuilder implements ParamBuilder {
  protected final Map<String, Object> params;
  protected HttpConfiguration httpConfiguration;
  private String bankAccountId;

  public DeleteClientBankAccountParamBuilder(String bankAccountId) {
    this.httpConfiguration = new HttpConfiguration();
    this.params = new HashMap<>();
    this.bankAccountId = bankAccountId;
  }

  public String getBankAccountId() {
    return bankAccountId;
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public DeleteClientBankAccountParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
