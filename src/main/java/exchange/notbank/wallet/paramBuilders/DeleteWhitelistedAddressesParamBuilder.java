package exchange.notbank.wallet.paramBuilders;

import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;

public class DeleteWhitelistedAddressesParamBuilder implements ParamBuilder {
  protected final Map<String, Object> params;
  protected HttpConfiguration httpConfiguration;
  private String whitelistAddressId;

  public DeleteWhitelistedAddressesParamBuilder(String whitelistAddressId, Integer accountId, String otp) {
    this(whitelistAddressId, accountId.toString(), otp);
  }

  public DeleteWhitelistedAddressesParamBuilder(String whitelistAddressId, String accountId, String otp) {
    this.httpConfiguration = new HttpConfiguration();
    this.params = new HashMap<>();
    this.params.put("account_id", accountId);
    this.params.put("otp", otp);
    this.whitelistAddressId = whitelistAddressId;
  }

  public String getWhitelistAddressId() {
    return whitelistAddressId;
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public DeleteWhitelistedAddressesParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
