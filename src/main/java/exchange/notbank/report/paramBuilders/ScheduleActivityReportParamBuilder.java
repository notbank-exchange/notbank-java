package exchange.notbank.report.paramBuilders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;

public class ScheduleActivityReportParamBuilder implements ParamBuilder {
  private final Map<String, Object> params;
  private HttpConfiguration httpConfiguration;

  public ScheduleActivityReportParamBuilder(List<Integer> accountIds, String beginTime) {
    this.httpConfiguration = HttpConfiguration.empty();
    this.params = new HashMap<>();
    this.params.put("OMSId", 1);
    this.params.put("accountIdList", accountIds);
    this.params.put("beginTime", beginTime);
  }

  public ScheduleActivityReportParamBuilder frequency(String value) {
    this.params.put("Frequency", value);
    return this;
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public ScheduleActivityReportParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
