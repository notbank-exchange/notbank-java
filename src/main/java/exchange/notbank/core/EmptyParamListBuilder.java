package exchange.notbank.core;

import java.util.List;
import java.util.Map;

public class EmptyParamListBuilder implements ParamListBuilder {
  @Override
  public List<Map<String, Object>> getParams() {
    return List.of();
  }

  @Override
  public HttpConfiguration getHttpConfiguration() {
    return HttpConfiguration.empty();
  }
}
