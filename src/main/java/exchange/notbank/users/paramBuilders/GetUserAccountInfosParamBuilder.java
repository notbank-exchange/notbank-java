package exchange.notbank.users.paramBuilders;

import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;

public class GetUserAccountInfosParamBuilder implements ParamBuilder {
  private final Map<String, Object> params;
  private HttpConfiguration httpConfiguration;

  public GetUserAccountInfosParamBuilder() {
    this.httpConfiguration = HttpConfiguration.empty();
    this.params = new HashMap<>();
  }

  public GetUserAccountInfosParamBuilder omsId(Integer omsId) {
    this.params.put("OMSId", omsId);
    return this;
  }

  public GetUserAccountInfosParamBuilder userId(Integer userId) {
    this.params.put("UserId", userId);
    return this;
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public GetUserAccountInfosParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}