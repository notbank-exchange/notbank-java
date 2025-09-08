package exchange.notbank.wallet.paramBuilders;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;

public class ResendVerificationCodeWhitelistedAddresParamBuilder implements ParamBuilder {
  protected final Map<String, Object> params;
  protected HttpConfiguration httpConfiguration;
  private String whitelistAddressId;

  public ResendVerificationCodeWhitelistedAddresParamBuilder(Integer accountId, UUID whitelistAddressId) {
    this(accountId, whitelistAddressId.toString());
  }

  public ResendVerificationCodeWhitelistedAddresParamBuilder(Integer accountId, String whitelistAddressId) {
    this.httpConfiguration = new HttpConfiguration();
    this.params = new HashMap<>();
    this.params.put("account_id", accountId);
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

  public ResendVerificationCodeWhitelistedAddresParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
