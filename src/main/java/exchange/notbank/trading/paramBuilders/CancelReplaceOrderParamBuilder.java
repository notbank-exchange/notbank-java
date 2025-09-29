package exchange.notbank.trading.paramBuilders;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;
import exchange.notbank.trading.constants.OrderSide;
import exchange.notbank.trading.constants.OrderType;
import exchange.notbank.trading.constants.PegPriceType;
import exchange.notbank.trading.constants.TimeInForce;

public class CancelReplaceOrderParamBuilder implements ParamBuilder {
  private final Map<String, Object> params;
  private HttpConfiguration httpConfiguration;

  public CancelReplaceOrderParamBuilder(Integer accountId, Integer instrumentId, BigDecimal quantity,
      Long orderIdToReplace, OrderType orderType,
      OrderSide side, TimeInForce timeInForce) {
    this.httpConfiguration = HttpConfiguration.empty();
    this.params = new HashMap<>();
    this.params.put("OMSId", 1);
    this.params.put("AccountId", accountId);
    this.params.put("InstrumentId", instrumentId);
    this.params.put("Quantity", quantity.toPlainString());
    this.params.put("OrderIdToReplace", orderIdToReplace);
    this.params.put("OrderType", orderType);
    this.params.put("Side", side);
    this.params.put("TimeInForce", timeInForce);
  }

  public CancelReplaceOrderParamBuilder clientOrderId(Long value) {
    this.params.put("ClientOrderId", value);
    return this;
  }

  public CancelReplaceOrderParamBuilder useDisplayQuantity(Boolean value) {
    this.params.put("UseDisplayQuantity", value);
    return this;
  }

  public CancelReplaceOrderParamBuilder displayQuantity(BigDecimal value) {
    this.params.put("DisplayQuantity", value.toPlainString());
    return this;
  }

  public CancelReplaceOrderParamBuilder limitPrice(BigDecimal value) {
    this.params.put("LimitPrice", value.toPlainString());
    return this;
  }

  public CancelReplaceOrderParamBuilder stopPrice(BigDecimal value) {
    this.params.put("StopPrice", value.toPlainString());
    return this;
  }

  public CancelReplaceOrderParamBuilder referencePrice(BigDecimal value) {
    this.params.put("ReferencePrice", value.toPlainString());
    return this;
  }

  public CancelReplaceOrderParamBuilder pegPriceType(PegPriceType value) {
    this.params.put("PegPriceType", value);
    return this;
  }

  public CancelReplaceOrderParamBuilder orderIdOCO(Long value) {
    this.params.put("OrderIdOCO", value);
    return this;
  }

  public CancelReplaceOrderParamBuilder postOnly(Boolean value) {
    this.params.put("PostOnly", value);
    return this;
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public CancelReplaceOrderParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
