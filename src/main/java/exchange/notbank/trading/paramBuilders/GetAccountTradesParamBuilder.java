package exchange.notbank.trading.paramBuilders;

import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;

public class GetAccountTradesParamBuilder implements ParamBuilder {
  private final Map<String, Object> params;
  private HttpConfiguration httpConfiguration;

  public GetAccountTradesParamBuilder() {
    this.httpConfiguration = HttpConfiguration.empty();
    this.params = new HashMap<>();
    this.params.put("OMSId", 1);
  }

  public GetAccountTradesParamBuilder accountId(Integer value) {
    params.put("AccountId", value);
    return this;
  }

  public GetAccountTradesParamBuilder startIndex(Integer value) {
    params.put("StartIndex", value);
    return this;
  }

  public GetAccountTradesParamBuilder count(Integer value) {
    params.put("Count", value);
    return this;
  }

  public GetAccountTradesParamBuilder depth(Integer value) {
    params.put("Depth", value);
    return this;
  }

  public GetAccountTradesParamBuilder instrumentId(Integer value) {
    params.put("InstrumentId", value);
    return this;
  }

  public GetAccountTradesParamBuilder tradeId(Integer value) {
    params.put("TradeId", value);
    return this;
  }

  public GetAccountTradesParamBuilder orderId(Integer value) {
    params.put("OrderId", value);
    return this;
  }

  public GetAccountTradesParamBuilder startTimeStamp(Integer value) {
    params.put("StartTimeStamp", value);
    return this;
  }

  public GetAccountTradesParamBuilder endTimeStamp(Integer value) {
    params.put("EndTimeStamp", value);
    return this;
  }

  public GetAccountTradesParamBuilder executionId(Integer value) {
    params.put("ExecutionId", value);
    return this;
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public GetAccountTradesParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
