package exchange.notbank.trading.paramBuilders;
import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;
import exchange.notbank.trading.constants.OrdersState;
public class GetOrdersParamBuilder implements ParamBuilder {
    private final Map<String, Object> params;
    private HttpConfiguration httpConfiguration;
  
    public GetOrdersParamBuilder(
        Integer accountId
    ) {
      this.httpConfiguration = HttpConfiguration.empty();
      this.params = new HashMap<>();
      this.params.put("OMSId", 1);
      this.params.put("AccountId", accountId);
    }
  
    public GetOrdersParamBuilder instrumentId(Integer instrumentId) {
      this.params.put("InstrumentId", instrumentId);
      return this;
    }

    public GetOrdersParamBuilder orderState(OrdersState orderState){
        this.params.put("OrderState", orderState.toString());
        return this;
    }

    public GetOrdersParamBuilder orderId(Long orderId){
      this.params.put("OrderId", orderId);
      return this;
    }

    public GetOrdersParamBuilder clientOrderId(Long clientOrderId){
      this.params.put("ClientOrderId", clientOrderId);
      return this;
    }
    
    public GetOrdersParamBuilder originalOrderId(Long originalOrderId){
      this.params.put("OriginalOrderId", originalOrderId);
      return this;
    }

    public GetOrdersParamBuilder originalClientOrderId(Long originalClientOrderId){
      this.params.put("OriginalClientOrderId", originalClientOrderId);
      return this;
    }

    public GetOrdersParamBuilder userId(Integer userId){
      this.params.put("UserId", userId);
      return this;
    }

    public GetOrdersParamBuilder instrumentId(Long instrumentId){
      this.params.put("InstrumentId", instrumentId);
      return this;
    }

    public GetOrdersParamBuilder startTimestamp(Long startTimestamp){
      this.params.put("StartTimestamp", startTimestamp);
      return this;
    }

    public GetOrdersParamBuilder endTimeStamp(Long endTimeStamp){
      this.params.put("EndTimestamp", endTimeStamp);
      return this;
    }

    public GetOrdersParamBuilder depth(Integer depth){
      this.params.put("Depth", depth);
      return this;
    }

    public GetOrdersParamBuilder limit(Integer limit){
      this.params.put("Limit", limit);
      return this;
    }

    public GetOrdersParamBuilder startIndex(Integer startIndex){
      this.params.put("StartIndex", startIndex);
      return this;
    }
  
    public Map<String, Object> getParams() {
      return params;
    }
  
    public HttpConfiguration getHttpConfiguration() {
      return httpConfiguration;
    }
  
    public GetOrdersParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
      this.httpConfiguration = httpConfiguration;
      return this;
    }
}
