package exchange.notbank.report.paramBuilders;

import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;

public class UserReportWriterResultRecordsParamBuilder implements ParamBuilder {
  private final Map<String, Object> params;
  private HttpConfiguration httpConfiguration;

  public UserReportWriterResultRecordsParamBuilder(Integer userId) {
    this.httpConfiguration = HttpConfiguration.empty();
    this.params = new HashMap<>();
    this.params.put("UserId", userId);
  }

  public UserReportWriterResultRecordsParamBuilder depth(Integer value) {
    this.params.put("Depth", value);
    return this;
  }

  public UserReportWriterResultRecordsParamBuilder startIndex(Integer value) {
    this.params.put("StartIndex", value);
    return this;
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public UserReportWriterResultRecordsParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
