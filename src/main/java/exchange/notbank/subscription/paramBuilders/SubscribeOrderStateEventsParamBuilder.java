package exchange.notbank.subscription.paramBuilders;

import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;

public class SubscribeOrderStateEventsParamBuilder implements ParamBuilder {
  private final Integer accountId;
  private final Map<String, Object> params;
  private HttpConfiguration httpConfiguration;

  public SubscribeOrderStateEventsParamBuilder(Integer accountId) {
    this.accountId = accountId;
    this.httpConfiguration = HttpConfiguration.empty();
    this.params = new HashMap<>();
    this.params.put("OMSId", 1);
    this.params.put("AccountId", accountId);
  }

  public SubscribeOrderStateEventsParamBuilder instrumentId(Integer instrumentId) {
    this.params.put("InstrumentId", instrumentId);
    return this;
  }

  public Integer getAccountId() {
    return accountId;
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public SubscribeOrderStateEventsParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
