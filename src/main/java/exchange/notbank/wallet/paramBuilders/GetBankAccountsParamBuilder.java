package exchange.notbank.wallet.paramBuilders;

import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;

public class GetBankAccountsParamBuilder implements ParamBuilder {
  protected final Map<String, Object> params;
  protected HttpConfiguration httpConfiguration;

  public GetBankAccountsParamBuilder() {
    this.httpConfiguration = new HttpConfiguration();
    this.params = new HashMap<>();

  }

  public GetBankAccountsParamBuilder page(Integer value) {
    this.params.put("page", value);
    return this;
  }

  public GetBankAccountsParamBuilder pageSize(Integer value) {
    this.params.put("page_size", value);
    return this;
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public GetBankAccountsParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
