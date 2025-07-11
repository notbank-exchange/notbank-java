package exchange.notbank.trading.paramBuilders;

import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;
import exchange.notbank.trading.constants.OrderState;

public class GetOrdersHistoryParamBuilder implements ParamBuilder {
  private final Map<String, Object> params;
  private HttpConfiguration httpConfiguration;

  public GetOrdersHistoryParamBuilder() {
    this.httpConfiguration = HttpConfiguration.empty();
    this.params = new HashMap<>();
    this.params.put("OMSId", 1);
  }

  public GetOrdersHistoryParamBuilder accountId(Integer value) {
    this.params.put("AccountId", value);
    return this;
  }

  public GetOrdersHistoryParamBuilder orderState(OrderState value) {
    this.params.put("OrderState", value);
    return this;
  }

  public GetOrdersHistoryParamBuilder orderId(Long value) {
    this.params.put("OrderId", value);
    return this;
  }

  public GetOrdersHistoryParamBuilder clientOrderId(Long value) {
    this.params.put("ClientOrderId", value);
    return this;
  }

  public GetOrdersHistoryParamBuilder originalOrderId(Long value) {
    this.params.put("OriginalOrderId", value);
    return this;
  }

  public GetOrdersHistoryParamBuilder originalClientOrderId(Long value) {
    this.params.put("OriginalClientOrderId", value);
    return this;
  }

  public GetOrdersHistoryParamBuilder userId(Integer value) {
    this.params.put("UserId", value);
    return this;
  }

  public GetOrdersHistoryParamBuilder intrumentId(Long value) {
    this.params.put("InstrumentId", value);
    return this;
  }

  public GetOrdersHistoryParamBuilder startTimestamp(Long value) {
    this.params.put("StartTimestamp", value);
    return this;
  }

  public GetOrdersHistoryParamBuilder endTimestamp(Long value) {
    this.params.put("EndTimestamp", value);
    return this;
  }

  public GetOrdersHistoryParamBuilder depth(Integer value) {
    this.params.put("Depth", value);
    return this;
  }

  public GetOrdersHistoryParamBuilder limit(Integer value) {
    this.params.put("Limit", value);
    return this;
  }

  public GetOrdersHistoryParamBuilder startIndex(Long value) {
    this.params.put("StartIndex", value);
    return this;
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public GetOrdersHistoryParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}