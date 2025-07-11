package exchange.notbank.core;

import java.util.Map;

public interface ParamBuilder {
  public Map<String, Object> getParams();

  public HttpConfiguration getHttpConfiguration();

  public static ParamBuilder EMPTY = new EmptyParamBuilder();
}
