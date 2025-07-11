package exchange.notbank.subscription.adapters;

import java.math.BigDecimal;
import java.util.List;

import exchange.notbank.trading.responses.Level2Ticker;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

public class Level2Adapter {
  @ToJson
  List<String> toJson(Level2Ticker level2) {
    return List.of(level2.marketDataUpdateId.toString(), level2.numberOfAccounts.toString(), level2.actionDateTime.toString(),
        level2.actionType.toString(), level2.lastTradePrice.toString(), level2.numberOfOrders.toString(),
        level2.price.toString(), level2.productPairCode.toString(), level2.quantity.toString(), level2.side.toString());
  }

  @FromJson
  Level2Ticker fromJson(List<String> values) {
    return new Level2Ticker(
        Long.parseLong(values.get(0)),
        Integer.parseInt(values.get(1)),
        Long.parseLong(values.get(2)),
        Integer.parseInt(values.get(3)),
        new BigDecimal(values.get(4)),
        Long.parseLong(values.get(5)),
        new BigDecimal(values.get(6)),
        Integer.parseInt(values.get(7)),
        new BigDecimal(values.get(8)),
        Integer.parseInt(values.get(9)));
  }
}
