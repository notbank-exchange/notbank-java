package exchange.notbank.fee.paramBuilders;

import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;

public class GetOmsWithdrawFeesParamsBuilder implements ParamBuilder {
  private final Map<String, Object> params;
  private HttpConfiguration httpConfiguration;

  public GetOmsWithdrawFeesParamsBuilder() {
    this.httpConfiguration = HttpConfiguration.empty();
    this.params = new HashMap<>();
    this.params.put("OMSId", 1);
  }

  public GetOmsWithdrawFeesParamsBuilder productId(Integer value) {
    this.params.put("ProductId", value);
    return this;
  }

  public GetOmsWithdrawFeesParamsBuilder accountProviderId(Integer value) {
    this.params.put("AccountProviderId", value);
    return this;
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public GetOmsWithdrawFeesParamsBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
