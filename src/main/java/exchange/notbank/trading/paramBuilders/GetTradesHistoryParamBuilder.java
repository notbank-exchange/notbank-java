package exchange.notbank.trading.paramBuilders;

import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;

public class GetTradesHistoryParamBuilder implements ParamBuilder {
  private final Map<String, Object> params;
  private HttpConfiguration httpConfiguration;

  public GetTradesHistoryParamBuilder() {
    this.httpConfiguration = HttpConfiguration.empty();
    this.params = new HashMap<>();
    this.params.put("OMSId", 1);
  }

  public GetTradesHistoryParamBuilder accountId(Integer accountId) {
    this.params.put("AccountId", accountId);
    return this;
  }

  public GetTradesHistoryParamBuilder instrumentId(Integer instrumentId) {
    this.params.put("InstrumentId", instrumentId);
    return this;
  }

  public GetTradesHistoryParamBuilder tradeId(Long tradeId) {
    this.params.put("TradeId", tradeId);
    return this;
  }

  public GetTradesHistoryParamBuilder orderId(Long orderId) {
    this.params.put("OrderId", orderId);
    return this;
  }

  public GetTradesHistoryParamBuilder userId(Integer userId) {
    this.params.put("UserId", userId);
    return this;
  }

  public GetTradesHistoryParamBuilder startTimeStamp(Long startTimeStamp) {
    this.params.put("StartTimeStamp", startTimeStamp);
    return this;
  }

  public GetTradesHistoryParamBuilder endTimeStamp(Long endTimeStamp) {
    this.params.put("EndTimeStamp", endTimeStamp);
    return this;
  }

  public GetTradesHistoryParamBuilder depth(Integer depth) {
    this.params.put("Depth", depth);
    return this;
  }

  public GetTradesHistoryParamBuilder startIndex(Integer startIndex) {
    this.params.put("StartIndex", startIndex);
    return this;
  }

  public GetTradesHistoryParamBuilder executionId(Integer executionId) {
    this.params.put("ExecutionId", executionId);
    return this;
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public GetTradesHistoryParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
