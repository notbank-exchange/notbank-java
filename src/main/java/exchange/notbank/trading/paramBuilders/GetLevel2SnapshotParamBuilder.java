package exchange.notbank.trading.paramBuilders;

import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;

public class GetLevel2SnapshotParamBuilder implements ParamBuilder {
    private final Map<String, Object> params;
    private HttpConfiguration httpConfiguration;
  
    public GetLevel2SnapshotParamBuilder(Integer instrumentId, Integer depth) {
      this.httpConfiguration = HttpConfiguration.empty();
      this.params = new HashMap<>();
      this.params.put("OMSId", 1);
      this.params.put("InstrumentId", instrumentId);
      this.params.put("Depth", depth);
    }
  
    public Map<String, Object> getParams() {
      return params;
    }
  
    public HttpConfiguration getHttpConfiguration() {
      return httpConfiguration;
    }
  
    public GetLevel2SnapshotParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
      this.httpConfiguration = httpConfiguration;
      return this;
    }
  }
