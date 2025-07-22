package exchange.notbank.wallet.paramBuilders;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;

public class ConfirmWhitelistedAddressesParamBuilder implements ParamBuilder {
  protected final Map<String, Object> params;
  protected HttpConfiguration httpConfiguration;
  private String whitelistAddressId;

  public ConfirmWhitelistedAddressesParamBuilder(Integer accountId, UUID whitelistAddressId, String code) {
    this(accountId, whitelistAddressId.toString(), code);
  }

  public ConfirmWhitelistedAddressesParamBuilder(Integer accountId, String whitelistAddressId, String code) {
    this.httpConfiguration = new HttpConfiguration();
    this.params = new HashMap<>();
    this.params.put("accountId", accountId);
    this.params.put("code", code);
    this.whitelistAddressId = whitelistAddressId.toString();
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

  public ConfirmWhitelistedAddressesParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
