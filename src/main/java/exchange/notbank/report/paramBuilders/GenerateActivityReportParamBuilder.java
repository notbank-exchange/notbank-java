package exchange.notbank.report.paramBuilders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;

public class GenerateActivityReportParamBuilder implements ParamBuilder {
    private final Map<String, Object> params;
    private HttpConfiguration httpConfiguration;
  
    public GenerateActivityReportParamBuilder(List<Integer> accountIds, String startTime, String endTime) {
      this.httpConfiguration = HttpConfiguration.empty();
      this.params = new HashMap<>();
      this.params.put("OMSId", 1);
      this.params.put("accountIdList", accountIds);
      this.params.put("startTime", startTime);
      this.params.put("endTime", endTime);
    }
  
    public Map<String, Object> getParams() {
      return params;
    }
  
    public HttpConfiguration getHttpConfiguration() {
      return httpConfiguration;
    }
  
    public GenerateActivityReportParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
      this.httpConfiguration = httpConfiguration;
      return this;
    }
  }
