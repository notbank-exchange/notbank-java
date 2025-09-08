package exchange.notbank.trading.paramBuilders;

import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;

public class CancelAllOrdersParamBuilder implements ParamBuilder {
  private final Map<String, Object> params;
  private HttpConfiguration httpConfiguration;

  public CancelAllOrdersParamBuilder(Integer instrumentId, Integer accountId) {
    this.httpConfiguration = HttpConfiguration.empty();
    this.params = new HashMap<>();
    this.params.put("OMSId", 1);
    this.params.put("InstrumentId", instrumentId);
    this.params.put("AccountId", accountId);
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public CancelAllOrdersParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
