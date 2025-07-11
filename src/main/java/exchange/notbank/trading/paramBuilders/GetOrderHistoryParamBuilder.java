package exchange.notbank.trading.paramBuilders;

import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;
import exchange.notbank.trading.constants.OrderState;

public class GetOrderHistoryParamBuilder implements ParamBuilder {
  private final Map<String, Object> params;
  private HttpConfiguration httpConfiguration;

  public GetOrderHistoryParamBuilder(Integer accountId) {
    this.httpConfiguration = HttpConfiguration.empty();
    this.params = new HashMap<>();
    this.params.put("OMSId", 1);
    this.params.put("AccountId", accountId);
  }

  public GetOrderHistoryParamBuilder orderState(OrderState value) {
    this.params.put("OrderState", value);
    return this;
  }

  public GetOrderHistoryParamBuilder orderId(Long value) {
    this.params.put("OrderId", value);
    return this;
  }

  public GetOrderHistoryParamBuilder clientOrderId(Long value) {
    this.params.put("ClientOrderId", value);
    return this;
  }

  public GetOrderHistoryParamBuilder originalOrderId(Long value) {
    this.params.put("OriginalOrderId", value);
    return this;
  }

  public GetOrderHistoryParamBuilder originalClientOrderId(Long value) {
    this.params.put("OriginalClientOrderId", value);
    return this;
  }

  public GetOrderHistoryParamBuilder userId(Integer value) {
    this.params.put("UserId", value);
    return this;
  }

  public GetOrderHistoryParamBuilder intrumentId(Long value) {
    this.params.put("InstrumentId", value);
    return this;
  }

  public GetOrderHistoryParamBuilder startTimestamp(Long value) {
    this.params.put("StartTimestamp", value);
    return this;
  }

  public GetOrderHistoryParamBuilder endTimestamp(Long value) {
    this.params.put("EndTimestamp", value);
    return this;
  }

  public GetOrderHistoryParamBuilder depth(Integer value) {
    this.params.put("Depth", value);
    return this;
  }

  public GetOrderHistoryParamBuilder limit(Integer value) {
    this.params.put("Limit", value);
    return this;
  }

  public GetOrderHistoryParamBuilder startIndex(Long value) {
    this.params.put("StartIndex", value);
    return this;
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public GetOrderHistoryParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}