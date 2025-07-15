package exchange.notbank.subscription.adapters;

import java.math.BigDecimal;
import java.util.List;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import exchange.notbank.subscription.responses.SocketTrade;

public class SocketTradeAdapter {
  @ToJson
  List<String> toJson(SocketTrade ticker) {
    return List.of(
        ticker.tradeId.toString(),
        ticker.instrumentId.toString(),
        ticker.quantity.toString(),
        ticker.price.toString(),
        ticker.order1Id.toString(),
        ticker.order2Id.toString(),
        ticker.tradetime.toString(),
        ticker.direction.toString(),
        ticker.tikerSide.toString(),
        ticker.blockTrade.toString(),
        ticker.orderClientId.toString());
  }

  @FromJson
  SocketTrade fromJson(List<String> values) {
    return new SocketTrade(
        Integer.valueOf(values.get(0)),
        Integer.valueOf(values.get(1)),
        new BigDecimal(values.get(2)),
        new BigDecimal(values.get(3)),
        Integer.valueOf(values.get(4)),
        Integer.valueOf(values.get(5)),
        Long.valueOf(values.get(6)),
        Integer.valueOf(values.get(7)),
        Integer.valueOf(values.get(8)),
        Boolean.valueOf(values.get(9)),
        Integer.valueOf(values.get(10)));
  }
}
