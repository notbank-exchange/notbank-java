package exchange.notbank.trading.paramBuilders;

import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;

public class GetOrdersHistoryByOrderIdParamBuilder implements ParamBuilder {
  private final Map<String, Object> params;
  private HttpConfiguration httpConfiguration;

  public GetOrdersHistoryByOrderIdParamBuilder(Long orderId) {
    this.httpConfiguration = HttpConfiguration.empty();
    this.params = new HashMap<>();
    this.params.put("OMSId", 1);
    this.params.put("OrderId", orderId);
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public GetOrdersHistoryByOrderIdParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
