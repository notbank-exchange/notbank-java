package exchange.notbank.trading.adapters;

import java.math.BigDecimal;
import java.util.List;

import exchange.notbank.trading.responses.LastTrade;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

public class LastTradeAdapter {
  @ToJson
  List<String> toJson(LastTrade trade) {
    return List.of(trade.tradeId.toString(), trade.instrumentId.toString(), trade.quantity.toString(), trade.price.toString(), 
        trade.order1Id.toString(), trade.order2Id.toString(), trade.tradetime.toString(), trade.direction.toString(), 
        trade.takerSide.toString(), trade.blockTrade.toString(), trade.orderClientId.toString());
  }

  @FromJson
  LastTrade fromJson(List<String> values) {
    return new LastTrade(
        Long.parseLong(values.get(0)),
        Long.parseLong(values.get(1)),
        new BigDecimal(values.get(2)),
        new BigDecimal(values.get(3)),
        Long.parseLong(values.get(4)),
        Long.parseLong(values.get(5)),
        Long.parseLong(values.get(6)),
        Integer.parseInt(values.get(7)),
        Integer.parseInt(values.get(8)),
        Integer.parseInt(values.get(9)) != 0,
        Long.parseLong(values.get(10))
    );
  }
}
