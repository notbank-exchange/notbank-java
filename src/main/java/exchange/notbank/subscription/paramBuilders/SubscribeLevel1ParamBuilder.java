package exchange.notbank.subscription.paramBuilders;

import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;

public class SubscribeLevel1ParamBuilder implements ParamBuilder {
  private final Integer instrumentId;
  private final Map<String, Object> params;
  private HttpConfiguration httpConfiguration;

  public SubscribeLevel1ParamBuilder(Integer instrumentId) {
    this.instrumentId = instrumentId;
    this.httpConfiguration = HttpConfiguration.empty();
    this.params = new HashMap<>();
    this.params.put("OMSId", 1);
    this.params.put("InstrumentId", instrumentId);
  }

  public Integer getInstrumentId() {
    return instrumentId;
  }
  // public SubscribeLevel1ParamBuilder(Integer omsId, String symbol) {
  //   this.httpConfiguration = HttpConfiguration.empty();
  //   this.params = new HashMap<>();
  //   params.put("OMSId", omsId);
  //   params.put("Symbol", symbol);
  // }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public SubscribeLevel1ParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
