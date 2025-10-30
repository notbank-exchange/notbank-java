package exchange.notbank.trading.paramBuilders;

import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;

public class GetSummaryParamBuilder implements ParamBuilder {
  private final Map<String, Object> params;
  private HttpConfiguration httpConfiguration;

  public GetSummaryParamBuilder() {
    this.httpConfiguration = HttpConfiguration.empty();
    this.params = new HashMap<>();

  }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public GetSummaryParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
