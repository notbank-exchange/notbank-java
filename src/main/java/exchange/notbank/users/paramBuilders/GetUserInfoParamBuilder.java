package exchange.notbank.users.paramBuilders;

import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;

public class GetUserInfoParamBuilder implements ParamBuilder {
  private final Map<String, Object> params;
  private HttpConfiguration httpConfiguration;

  public GetUserInfoParamBuilder() {
    this.httpConfiguration = HttpConfiguration.empty();
    this.params = new HashMap<>();
    this.params.put("OMSId", 1);
  }

  public GetUserInfoParamBuilder userId(Integer userId) {
    this.params.put("UserId", userId);
    return this;
  }

  public GetUserInfoParamBuilder email(String email) {
    this.params.put("Email", email);
    return this;
  }

  public GetUserInfoParamBuilder username(String username) {
    this.params.put("Username", username);
    return this;
  }

  public GetUserInfoParamBuilder affiliateId(Integer affiliateId) {
    this.params.put("AffiliateId", affiliateId);
    return this;
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public GetUserInfoParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
