package exchange.notbank.report.paramBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamListBuilder;
import exchange.notbank.report.constants.ReportRequestStatus;

public class GetUserReportTicketsByStatusParamBuilder implements ParamListBuilder {
  private final List<Map<String, Object>> params;
  private HttpConfiguration httpConfiguration;

  public GetUserReportTicketsByStatusParamBuilder(List<ReportRequestStatus> statuses) {
    this.httpConfiguration = HttpConfiguration.empty();
    this.params = new ArrayList<>();
    statuses.forEach(this::addStatus);
  }

  public GetUserReportTicketsByStatusParamBuilder addStatus(ReportRequestStatus status) {
    this.params.add(Map.of("RequestStatus", status));
    return this;
  }

  public List<Map<String, Object>> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public GetUserReportTicketsByStatusParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
