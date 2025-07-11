package exchange.notbank.trading.adapters;

import java.math.BigDecimal;
import java.util.List;

import exchange.notbank.trading.responses.SocketTrade;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

public class SocketTradeAdapter {
  @ToJson
  List<String> toJson(SocketTrade trade) {
    return List.of(
        trade.tradeId.toString(),
        trade.instrumentId.toString(),
        trade.quantity.toString(),
        trade.price.toString(),
        trade.order1Id.toString(),
        trade.order2Id.toString(),
        trade.tradetime.toString(),
        trade.direction.toString(),
        trade.tikerSide.toString(),
        trade.blockTrade.toString(),
        trade.orderClientId.toString());
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
