package exchange.notbank.fee.paramBuilders;

import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;

public class GetOmsDepositFeesParamsBuilder implements ParamBuilder {
  private final Map<String, Object> params;
  private HttpConfiguration httpConfiguration;

  public GetOmsDepositFeesParamsBuilder() {
    this.httpConfiguration = HttpConfiguration.empty();
    this.params = new HashMap<>();
    this.params.put("OMSId", 1);
  }

  public GetOmsDepositFeesParamsBuilder productId(Integer value) {
    this.params.put("ProductId", value);
    return this;
  }

  public GetOmsDepositFeesParamsBuilder accountProviderId(Integer value) {
    this.params.put("AccountProviderId", value);
    return this;
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public GetOmsDepositFeesParamsBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
