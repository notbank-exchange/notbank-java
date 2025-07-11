package exchange.notbank.account.paramBuilders;

import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;

public class GetAccountPositionsParamBuilder implements ParamBuilder {
  private HttpConfiguration httpConfiguration;
  private final Map<String, Object> params;

  public GetAccountPositionsParamBuilder(Integer accountId) {
    httpConfiguration = HttpConfiguration.empty();
    this.params = new HashMap<>();
    this.params.put("OMSId", 1);
    this.params.put("AccountId", accountId);
  }

  public GetAccountPositionsParamBuilder includePending(Boolean value) {
    params.put("IncludePending", value);
    return this;
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public GetAccountPositionsParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }

}
