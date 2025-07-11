package exchange.notbank.core;

import java.util.Map;

public class EmptyParamBuilder implements ParamBuilder {

  @Override
  public Map<String, Object> getParams() {
    return Map.of();
  }

  @Override
  public HttpConfiguration getHttpConfiguration() {
    return HttpConfiguration.empty();
  }

}
