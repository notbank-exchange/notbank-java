package exchange.notbank.account.paramBuilders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;
import exchange.notbank.users.constants.ReferenceType;
import exchange.notbank.users.constants.TransactionType;

public class GetAccountTransactionsParamBuilder implements ParamBuilder {
  private final Map<String, Object> params;
  private HttpConfiguration httpConfiguration;

  public GetAccountTransactionsParamBuilder() {
    this.httpConfiguration = HttpConfiguration.empty();
    this.params = new HashMap<>();
    this.params.put("OMSId", 1);
  }

  public GetAccountTransactionsParamBuilder accountId(Integer value) {
    params.put("AccountId", value);
    return this;
  }

  public GetAccountTransactionsParamBuilder depth(Integer value) {
    params.put("Depth", value);
    return this;
  }

  public GetAccountTransactionsParamBuilder productId(Integer value) {
    params.put("ProductId", value);
    return this;
  }

  public GetAccountTransactionsParamBuilder transactionId(Integer value) {
    params.put("TransactionId", value);
    return this;
  }

  public GetAccountTransactionsParamBuilder referenceId(Integer value) {
    params.put("ReferenceId", value);
    return this;
  }

  public GetAccountTransactionsParamBuilder transactionTypes(List<TransactionType> value) {
    params.put("TransactionTypes", value);
    return this;
  }

  public GetAccountTransactionsParamBuilder transactionReferenceTypes(List<ReferenceType> value) {
    params.put("TransactionReferenceTypes", value);
    return this;
  }

  public GetAccountTransactionsParamBuilder startTimestamp(Long value) {
    params.put("StartTimestamp", value);
    return this;
  }

  public GetAccountTransactionsParamBuilder endTimeStamp(Long value) {
    params.put("EndTimeStamp", value);
    return this;
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public GetAccountTransactionsParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
