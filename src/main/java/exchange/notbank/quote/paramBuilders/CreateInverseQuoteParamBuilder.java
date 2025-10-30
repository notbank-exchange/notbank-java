package exchange.notbank.quote.paramBuilders;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;

public class CreateInverseQuoteParamBuilder implements ParamBuilder {
  private final Map<String, Object> params;
  private HttpConfiguration httpConfiguration;

  public CreateInverseQuoteParamBuilder(Integer accountId, String fromCurrency, BigDecimal toAmount,
      String toCurrency) {
    this.httpConfiguration = HttpConfiguration.empty();
    this.params = new HashMap<>();
    this.params.put("account_id", accountId);
    this.params.put("from_currency", fromCurrency);
    this.params.put(("to_currency"), toCurrency);
    this.params.put("to_amount", toAmount);
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public CreateInverseQuoteParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
