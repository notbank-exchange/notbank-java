package exchange.notbank.fee.paramBuilders;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;
import exchange.notbank.fee.constants.IntOrderType;
import exchange.notbank.fee.constants.IntSide;
import exchange.notbank.fee.constants.MakerTaker;

public class GetOrderFeeParamBuilder implements ParamBuilder {
  private final Map<String, Object> params;
  private HttpConfiguration httpConfiguration;

  public GetOrderFeeParamBuilder(Integer accountId, Integer instrumentId, BigDecimal quantity,
      BigDecimal price, IntOrderType orderType, MakerTaker makerTaker, IntSide side) {
    this.httpConfiguration = HttpConfiguration.empty();
    this.params = new HashMap<>();
    this.params.put("OMSId", 1);
    this.params.put("AccountId", accountId);
    this.params.put("InstrumentId", instrumentId);
    this.params.put("Quantity", quantity.toPlainString());
    this.params.put("Price", price.toPlainString());
    this.params.put("OrderType", orderType.value);
    this.params.put("MakerTaker", makerTaker.value);
    this.params.put("Side", side.value);
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public GetOrderFeeParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
