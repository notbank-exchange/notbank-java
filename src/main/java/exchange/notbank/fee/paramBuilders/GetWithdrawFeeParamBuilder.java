package exchange.notbank.fee.paramBuilders;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;

public class GetWithdrawFeeParamBuilder implements ParamBuilder {
  private final Map<String, Object> params;
  private HttpConfiguration httpConfiguration;

  public GetWithdrawFeeParamBuilder(Integer accountId, Integer productId, BigDecimal amount) {
    this.httpConfiguration = HttpConfiguration.empty();
    this.params = new HashMap<>();
    this.params.put("OMSId", 1);
    this.params.put("AccountId", accountId);
    this.params.put("ProductId", productId);
    this.params.put("Amount", amount.toPlainString());
  }

  public GetWithdrawFeeParamBuilder accountProviderId(Integer value) {
    this.params.put("AccountProviderId", value);
    return this;
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public GetWithdrawFeeParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
