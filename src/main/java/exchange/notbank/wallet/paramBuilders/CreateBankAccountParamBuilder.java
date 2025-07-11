package exchange.notbank.wallet.paramBuilders;

import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;

public class CreateBankAccountParamBuilder implements ParamBuilder {
  protected final Map<String, Object> params;
  protected HttpConfiguration httpConfiguration;

  public CreateBankAccountParamBuilder(String country, String bank, String number, String kind) {
    this.httpConfiguration = new HttpConfiguration();
    this.params = new HashMap<>();
    this.params.put("country", country);
    this.params.put("bank", bank);
    this.params.put("number", number);
    this.params.put("kind", kind);
  }

  public CreateBankAccountParamBuilder pixType(String value) {
    this.params.put("pix_type", value);
    return this;
  }

  public CreateBankAccountParamBuilder agency(String value) {
    this.params.put("agency", value);
    return this;
  }

  public CreateBankAccountParamBuilder dv(String value) {
    this.params.put("dv", value);
    return this;
  }

  public CreateBankAccountParamBuilder province(String value) {
    this.params.put("province", value);
    return this;
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public CreateBankAccountParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
