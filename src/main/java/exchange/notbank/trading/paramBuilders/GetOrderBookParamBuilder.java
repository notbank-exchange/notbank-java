package exchange.notbank.trading.paramBuilders;

import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;
import exchange.notbank.trading.constants.DepthType;

public class GetOrderBookParamBuilder implements ParamBuilder {
  private final Map<String, Object> params;
  private HttpConfiguration httpConfiguration;

  public GetOrderBookParamBuilder(String marketPair) {
    this.httpConfiguration = HttpConfiguration.empty();
    this.params = new HashMap<>();
    params.put("Market_Pair", marketPair);
  }

  public GetOrderBookParamBuilder depth(Integer value) {
    params.put("Depth", value);
    return this;
  }

  public GetOrderBookParamBuilder depthType(DepthType value) {
    params.put("Level", value.value);
    return this;
  }

  public GetOrderBookParamBuilder level(Integer value) {
    params.put("Level", value);
    return this;
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public GetOrderBookParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
