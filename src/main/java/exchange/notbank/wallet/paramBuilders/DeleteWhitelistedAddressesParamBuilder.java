package exchange.notbank.wallet.paramBuilders;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;

public class DeleteWhitelistedAddressesParamBuilder implements ParamBuilder {
  protected final Map<String, Object> params;
  protected HttpConfiguration httpConfiguration;
  private String whitelistAddressId;

  public DeleteWhitelistedAddressesParamBuilder(Integer accountId, UUID whitelistAddressId, String otp) {
    this(accountId, whitelistAddressId.toString(), otp);
  }

  public DeleteWhitelistedAddressesParamBuilder(Integer accountId, String whitelistAddressId, String otp) {
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
