package exchange.notbank.account.paramBuilders;

import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;

public class GetAccountInstrumentStatisticsParamBuilder implements ParamBuilder {
  protected final Map<String, Object> params;
  protected HttpConfiguration httpConfiguration;

  public GetAccountInstrumentStatisticsParamBuilder() {
    this.httpConfiguration = new HttpConfiguration();
    this.params = new HashMap<>();
  }

  public GetAccountInstrumentStatisticsParamBuilder accountId(Integer accountId) {
    this.params.put("AccountId", accountId);
    return this;
  }

  public GetAccountInstrumentStatisticsParamBuilder omsId(Integer omsId) {
    this.params.put("OMSId", omsId);
    return this;
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public GetAccountInstrumentStatisticsParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
