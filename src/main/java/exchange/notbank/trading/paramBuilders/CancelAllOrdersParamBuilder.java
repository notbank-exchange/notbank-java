package exchange.notbank.trading.paramBuilders;

import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;

public class CancelAllOrdersParamBuilder implements ParamBuilder {
  private final Map<String, Object> params;
  private HttpConfiguration httpConfiguration;

  // According to API, accountId and instrumentId params could be ommited
  // but here are required because it could lead to unexpected results as cancelling existings orders for all instruments or all accounts
  // if cancelling all instruments on an account is a desire effect, instrumentId should be equals to 0
  public CancelAllOrdersParamBuilder(Integer instrumentId, Integer accountId) throws Exception {
    this.httpConfiguration = HttpConfiguration.empty();
    this.params = new HashMap<>();
    this.params.put("OMSId", 1);
    this.params.put("InstrumentId", instrumentId);
    // This not allows accountId = 0 because It will cancel all existing orders of all accounts on an OMS
    if (accountId == 0) {
      throw new Exception("Invalid Parameter Value: 'accountId' must be greater than 0");
    }
    this.params.put("AccountId", accountId);
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public CancelAllOrdersParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
