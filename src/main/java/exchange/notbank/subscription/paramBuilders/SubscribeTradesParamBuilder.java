package exchange.notbank.subscription.paramBuilders;

import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;

public class SubscribeTradesParamBuilder implements ParamBuilder {
  private final Map<String, Object> params;
  private final Integer instrumentId;
  private HttpConfiguration httpConfiguration;

  public SubscribeTradesParamBuilder(Integer instrumentId, Integer includeLastCount) {
    this.instrumentId = instrumentId;
    this.httpConfiguration = HttpConfiguration.empty();
    this.params = new HashMap<>();
    this.params.put("OMSId", 1);
    this.params.put("InstrumentId", instrumentId);
    this.params.put("IncludeLastCount", includeLastCount);
  }

  public Integer getInstrumentId() {
    return instrumentId;
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public SubscribeTradesParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
