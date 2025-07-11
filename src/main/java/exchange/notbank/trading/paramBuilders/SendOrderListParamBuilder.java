package exchange.notbank.trading.paramBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamListBuilder;

public class SendOrderListParamBuilder implements ParamListBuilder {
  private final List<Map<String, Object>> params;
  private HttpConfiguration httpConfiguration;

  public SendOrderListParamBuilder(List<SendOrderParamBuilder> ordersList) {
    this.httpConfiguration = HttpConfiguration.empty();
    this.params = new ArrayList<>();
    ordersList.forEach(this::addOrder);
  }

  public SendOrderListParamBuilder addOrder(SendOrderParamBuilder order) {
    this.params.add(order.getParams());
    return this;
  }

  public List<Map<String, Object>> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public SendOrderListParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
