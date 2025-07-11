package exchange.notbank.account.paramBuilders;

import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;

public class UserInfoParamBuilder implements ParamBuilder {
  private final Map<String, Object> params;
  private HttpConfiguration httpConfiguration;

  public UserInfoParamBuilder(
      String username,
      String email) {
    this.httpConfiguration = HttpConfiguration.empty();
    this.params = new HashMap<>();
    this.params.put("Username", username);
    this.params.put("Email", email);
    this.params.put("OMSId", 1);
  }

  public UserInfoParamBuilder passwordHash(String value) {
    params.put("PasswordHash", value);
    return this;
  }

  public UserInfoParamBuilder accountId(Integer value) {
    params.put("AccountId", value);
    return this;
  }

  public UserInfoParamBuilder emailVerified(Boolean value) {
    params.put("EmailVerified", value);
    return this;
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public UserInfoParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
