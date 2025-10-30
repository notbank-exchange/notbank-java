package exchange.notbank.trading.paramBuilders;

import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;


public class GetTradesParamBuilder implements ParamBuilder {
  private final Map<String, Object> params;
  private HttpConfiguration httpConfiguration;

  public GetTradesParamBuilder(String marketPair) {
    this.httpConfiguration = HttpConfiguration.empty();
    this.params = new HashMap<>();
    this.params.put("market_pair", marketPair);
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public GetTradesParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
