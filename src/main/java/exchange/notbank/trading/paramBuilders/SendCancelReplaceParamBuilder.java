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

public class SendCancelReplaceParamBuilder implements ParamBuilder {
  private final Map<String, Object> params;
  private HttpConfiguration httpConfiguration;

  public SendCancelReplaceParamBuilder(Long replaceOrderId, OrderType orderType, OrderSide side, 
      Integer accountId, Integer instrumentId, TimeInForce timeInForce, BigDecimal quantity) {
    this.httpConfiguration = HttpConfiguration.empty();
    this.params = new HashMap<>();
    this.params.put("OMSId", 1);
    this.params.put("ReplaceOrderId", replaceOrderId);
    this.params.put("OrderType", orderType);
    this.params.put("Side", side);
    this.params.put("AccountId", accountId);
    this.params.put("InstrumentId", instrumentId);
    this.params.put("TimeInForce", timeInForce);
    this.params.put("Quantity", quantity.toPlainString());
  }

  public SendCancelReplaceParamBuilder replaceClientOrderId(Long replaceClientOrderId) {
    this.params.put("ReplaceClientOrderId", replaceClientOrderId);
    return this;
  }

  public SendCancelReplaceParamBuilder clientOrderId(Long clientOrderId) {
    this.params.put("ClientOrderId", clientOrderId);
    return this;
  }

  public SendCancelReplaceParamBuilder useDisplayQuantity(Boolean useDisplayQuantity) {
    this.params.put("UseDisplayQuantity", useDisplayQuantity);
    return this;
  }

  public SendCancelReplaceParamBuilder displayQuantity(BigDecimal displayQuantity) {
    this.params.put("DisplayQuantity", displayQuantity.toPlainString());
    return this;
  }

  public SendCancelReplaceParamBuilder limitPrice(BigDecimal limitPrice) {
    this.params.put("LimitPrice", limitPrice.toPlainString());
    return this;
  }

  public SendCancelReplaceParamBuilder stopPrice(BigDecimal stopPrice) {
    this.params.put("StopPrice", stopPrice.toPlainString());
    return this;
  }

  public SendCancelReplaceParamBuilder referencePrice(BigDecimal referencePrice) {
    this.params.put("ReferencePrice", referencePrice.toPlainString());
    return this;
  }

  public SendCancelReplaceParamBuilder pegPriceType(PegPriceType pegPriceType) {
    this.params.put("PegPriceType", pegPriceType);
    return this;
  }

  public SendCancelReplaceParamBuilder orderIdOneCancelsOther(Long replaceClientOrderId) {
    this.params.put("OrderIdOCO", replaceClientOrderId);
    return this;
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public SendCancelReplaceParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
