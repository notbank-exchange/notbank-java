package exchange.notbank.subscription.paramBuilders;

import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;

public class SubscribeLevel2ParamBuilder implements ParamBuilder {
  private final Integer instrumentId;
  private final Map<String, Object> params;
  private HttpConfiguration httpConfiguration;

  public SubscribeLevel2ParamBuilder(Integer instrumentId, Integer depth) {
    this.instrumentId = instrumentId;
    this.httpConfiguration = HttpConfiguration.empty();
    this.params = new HashMap<>();
    this.params.put("OMSId", 1);
    this.params.put("InstrumentId", instrumentId);
    this.params.put("Depth", depth);
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

  public SubscribeLevel2ParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
