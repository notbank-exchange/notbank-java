package exchange.notbank.report.paramBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamListBuilder;

public class UserReportTicketsByStatusParamBuilder implements ParamListBuilder {
  private final List<Map<String, Object>> params;
  private HttpConfiguration httpConfiguration;

  public UserReportTicketsByStatusParamBuilder(List<String> statuses) {
    this.httpConfiguration = HttpConfiguration.empty();
    this.params = new ArrayList<>();
    statuses.forEach(this::addStatus);
  }

  public UserReportTicketsByStatusParamBuilder addStatus(String status) {
    this.params.add(Map.of("RequestStatus", status));
    return this;
  }

  public List<Map<String, Object>> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public UserReportTicketsByStatusParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
