package exchange.notbank.subscription.paramBuilders;

import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;

public class UnsubscribeAccountEventsParamBuilder implements ParamBuilder {
  public final Integer accountId;
  private final Map<String, Object> params;
  private HttpConfiguration httpConfiguration;

  public UnsubscribeAccountEventsParamBuilder(Integer accountId) {
    this.accountId = accountId;
    this.httpConfiguration = HttpConfiguration.empty();
    this.params = new HashMap<>();
    this.params.put("OMSId", 1);
    this.params.put("AccountId", accountId);
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public UnsubscribeAccountEventsParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
