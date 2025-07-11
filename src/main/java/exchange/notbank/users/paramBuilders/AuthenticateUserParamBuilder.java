package exchange.notbank.users.paramBuilders;

import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;

public class AuthenticateUserParamBuilder implements ParamBuilder {
  public final String apiKey;
  public final String signature;
  public final String userId;
  public final String nonce;
  private HttpConfiguration httpConfiguration;

  public AuthenticateUserParamBuilder(String apiKey, String signature, String userId, String nonce) {
    this.httpConfiguration = HttpConfiguration.empty();
    this.apiKey = apiKey;
    this.signature = signature;
    this.userId = userId;
    this.nonce = nonce;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public AuthenticateUserParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }

  @Override
  public Map<String, Object> getParams() {
    return new HashMap<>();
  }
}
