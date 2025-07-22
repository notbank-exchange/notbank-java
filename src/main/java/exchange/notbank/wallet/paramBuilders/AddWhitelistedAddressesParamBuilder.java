package exchange.notbank.wallet.paramBuilders;

import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;

public class AddWhitelistedAddressesParamBuilder implements ParamBuilder {
  protected final Map<String, Object> params;
  protected HttpConfiguration httpConfiguration;

  public AddWhitelistedAddressesParamBuilder(Integer accountId, String currency, String network, String address,
      String label, Integer otp) {
    this.httpConfiguration = new HttpConfiguration();
    this.params = new HashMap<>();
    this.params.put("account_id", accountId);
    this.params.put("currency", currency);
    this.params.put("network", network);
    this.params.put("address", address);
    this.params.put("label", label);
    this.params.put("otp", otp.toString());
  }

  public AddWhitelistedAddressesParamBuilder memoOrTag(String value) {
    this.params.put("memo_or_tag", value);
    return this;
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public AddWhitelistedAddressesParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
