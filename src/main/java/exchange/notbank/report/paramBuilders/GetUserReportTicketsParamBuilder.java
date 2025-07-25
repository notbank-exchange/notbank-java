package exchange.notbank.report.paramBuilders;

import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;

public class GetUserReportTicketsParamBuilder implements ParamBuilder {
  private final Map<String, Object> params;
  private HttpConfiguration httpConfiguration;

  public GetUserReportTicketsParamBuilder(Integer userId) {
    this.httpConfiguration = HttpConfiguration.empty();
    this.params = new HashMap<>();
    this.params.put("UserId", userId);
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public GetUserReportTicketsParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
