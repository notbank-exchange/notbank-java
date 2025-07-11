package exchange.notbank.trading.paramBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamListBuilder;

public class SendCancelReplaceListParamBuilder implements ParamListBuilder {
  private final List<Map<String, Object>> params;
  private HttpConfiguration httpConfiguration;

  public SendCancelReplaceListParamBuilder(List<SendCancelReplaceParamBuilder> cancelReplaceOrdersList) {
    this.httpConfiguration = HttpConfiguration.empty();
    this.params = new ArrayList<>();
    cancelReplaceOrdersList.forEach(this::addCancelReplaceOrder);
  }

  public SendCancelReplaceListParamBuilder addCancelReplaceOrder(SendCancelReplaceParamBuilder cancelReplaceOrder) {
    this.params.add(cancelReplaceOrder.getParams());
    return this;
  }

  public List<Map<String, Object>> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public SendCancelReplaceListParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
