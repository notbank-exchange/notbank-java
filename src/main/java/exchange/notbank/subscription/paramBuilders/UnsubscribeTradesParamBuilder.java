package exchange.notbank.subscription.paramBuilders;

import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;

public class UnsubscribeTradesParamBuilder implements ParamBuilder {
  public final Integer instrumentId;
  private final Map<String, Object> params;
  private HttpConfiguration httpConfiguration;

  public UnsubscribeTradesParamBuilder(Integer instrumentId) {
    this.instrumentId = instrumentId;
    this.httpConfiguration = HttpConfiguration.empty();
    this.params = new HashMap<>();
    this.params.put("OMSId", 1);
    this.params.put("InstrumentId", instrumentId);
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public UnsubscribeTradesParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
