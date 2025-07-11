package exchange.notbank.trading.paramBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamListBuilder;

public class SendCancelListParamBuilder implements ParamListBuilder {
    private final List<Map<String, Object>> params;
  private HttpConfiguration httpConfiguration;

  public SendCancelListParamBuilder(List<SendCancelParamBuilder> cancelOrdersList) {
    this.httpConfiguration = HttpConfiguration.empty();
    this.params = new ArrayList<>();
    cancelOrdersList.forEach(this::addCancelOrder);
  }

  public SendCancelListParamBuilder addCancelOrder(SendCancelParamBuilder cancelOrder) {
    this.params.add(cancelOrder.getParams());
    return this;
  }

  public List<Map<String, Object>> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public SendCancelListParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
