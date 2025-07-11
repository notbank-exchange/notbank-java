package exchange.notbank.subscription.paramBuilders;

import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;

public class SubscribeTickerParamBuilder implements ParamBuilder {
  private final Map<String, Object> params;
  private final Integer instrumentId;
  private HttpConfiguration httpConfiguration;

  public SubscribeTickerParamBuilder(Integer instrumentId, Integer interval, Integer includeLastCount) {
    this.httpConfiguration = HttpConfiguration.empty();
    this.params = new HashMap<>();
    this.instrumentId = instrumentId;
    this.params.put("OMSId", 1);
    this.params.put("InstrumentId", instrumentId);
    this.params.put("Interval", interval);
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

  public SubscribeTickerParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
