package exchange.notbank.product.paramBuilders;

import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;

public class GetProductsParamBuilder implements ParamBuilder {
  private final Map<String, Object> params;
  private HttpConfiguration httpConfiguration;

  public GetProductsParamBuilder() {
    this.httpConfiguration = HttpConfiguration.empty();
    this.params = new HashMap<>();
    this.params.put("OMSId", 1);
  }

  public GetProductsParamBuilder attribute(String value) {
    params.put("Attribute", value);
    return this;
  }

  public GetProductsParamBuilder attributeValue(String value) {
    params.put("AttributeValue", value);
    return this;
  }

  public GetProductsParamBuilder attributeValue(boolean value) {
    params.put("AttributeValue", value);
    return this;
  }

  public GetProductsParamBuilder getDisabled(Boolean value) {
    params.put("GetDisabled", value);
    return this;
  }

  public GetProductsParamBuilder depth(Integer value) {
    params.put("Depth", value);
    return this;
  }

  public GetProductsParamBuilder startIndex(Integer value) {
    params.put("StartIndex", value);
    return this;
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public GetProductsParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
