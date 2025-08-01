package exchange.notbank.wallet.paramBuilders;

import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;
import exchange.notbank.wallet.constants.SortBy;

public class GetTransactionsParamBuilder implements ParamBuilder {
  protected final Map<String, Object> params;
  protected HttpConfiguration httpConfiguration;

  public GetTransactionsParamBuilder() {
    this.httpConfiguration = new HttpConfiguration();
    this.params = new HashMap<>();
  }

  public GetTransactionsParamBuilder fromDate(String value) {
    this.params.put("from_date", value);
    return this;
  }

  public GetTransactionsParamBuilder toDate(String value) {
    this.params.put("to_date", value);
    return this;
  }

  public GetTransactionsParamBuilder sort(SortBy value) {
    this.params.put("sort", value);
    return this;
  }

  public GetTransactionsParamBuilder currency(String value) {
    this.params.put("currency", value);
    return this;
  }

  public GetTransactionsParamBuilder page(Integer value) {
    this.params.put("page", value);
    return this;
  }

  public GetTransactionsParamBuilder pageSize(Integer value) {
    this.params.put("page_size", value);
    return this;
  }

  public GetTransactionsParamBuilder otp(String value) {
    this.params.put("otp", value);
    return this;
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public GetTransactionsParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }

}
