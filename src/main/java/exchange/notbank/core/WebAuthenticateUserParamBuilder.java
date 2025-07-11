package exchange.notbank.core;

import java.util.HashMap;
import java.util.Map;

public class WebAuthenticateUserParamBuilder implements ParamBuilder {
  private final Map<String, Object> params;
  private HttpConfiguration httpConfiguration;

  public WebAuthenticateUserParamBuilder() {
    this.params = new HashMap<>();
    this.httpConfiguration = HttpConfiguration.empty();
  }

  public WebAuthenticateUserParamBuilder jwtToken(String value) {
    this.params.put("JWTToken", value);
    return this;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public WebAuthenticateUserParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }

  @Override
  public Map<String, Object> getParams() {
    return params;
  }
}
