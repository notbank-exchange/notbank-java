package exchange.notbank.trading.paramBuilders;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;
import exchange.notbank.core.Truncator;
import exchange.notbank.instrument.responses.Instrument;
import exchange.notbank.trading.constants.OrderSide;
import exchange.notbank.trading.constants.OrderType;
import exchange.notbank.trading.constants.PegPriceType;
import exchange.notbank.trading.constants.TimeInForce;

public class SendOrderParamBuilder implements ParamBuilder {
  private final Map<String, Object> params;
  private final Instrument instrument;
  private HttpConfiguration httpConfiguration;

  public SendOrderParamBuilder(Instrument instrument, Integer accountId, TimeInForce timeInForce,
      OrderSide side, OrderType orderType, BigDecimal quantity) {
    this.instrument = instrument;
    this.httpConfiguration = HttpConfiguration.empty();
    this.params = new HashMap<>();
    this.params.put("OMSId", 1);
    this.params.put("InstrumentId", instrument.instrumentId);
    this.params.put("AccountId", accountId);
    this.params.put("TimeInForce", timeInForce);
    this.params.put("Side", side);
    this.params.put("OrderType", orderType);
    var effectiveQuantity = Truncator.truncate(quantity, instrument.quantityIncrement);
    this.params.put("Quantity", effectiveQuantity.toPlainString());
  }

  public SendOrderParamBuilder clientOrderId(Long value) {
    this.params.put("ClientOrderId", value);
    return this;
  }

  public SendOrderParamBuilder orderIdOCO(Long value) {
    this.params.put("OrderIdOCO", value);
    return this;
  }

  public SendOrderParamBuilder useDisplayQuantity(Boolean value) {
    this.params.put("UseDisplayQuantity", value);
    return this;
  }

  public SendOrderParamBuilder pegPriceType(PegPriceType value) {
    this.params.put("PegPriceType", value);
    return this;
  }

  public SendOrderParamBuilder limitPrice(BigDecimal value) {
    var effectiveQuantity = Truncator.truncate(value, instrument.priceIncrement);
    this.params.put("LimitPrice", effectiveQuantity.toPlainString());
    return this;
  }

  public SendOrderParamBuilder stopPrice(BigDecimal value) {
    var effectiveQuantity = Truncator.truncate(value, instrument.priceIncrement);
    this.params.put("StopPrice", effectiveQuantity.toPlainString());
    return this;
  }

  public SendOrderParamBuilder trailingAmount(BigDecimal value) {
    this.params.put("TrailingAmount", value.toPlainString());
    return this;
  }

  public SendOrderParamBuilder limitOffset(BigDecimal value) {
    this.params.put("LimitOffset", value.toPlainString());
    return this;
  }

  public SendOrderParamBuilder displayQuantity(Integer value) {
    this.params.put("DisplayQuantity", value);
    return this;
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public SendOrderParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
