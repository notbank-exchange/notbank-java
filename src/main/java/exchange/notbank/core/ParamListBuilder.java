package exchange.notbank.core;

import java.util.List;
import java.util.Map;

public interface ParamListBuilder {
  public List<Map<String, Object>> getParams();

  public HttpConfiguration getHttpConfiguration();

  public static ParamListBuilder EMPTY = new EmptyParamListBuilder();
}
