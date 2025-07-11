package exchange.notbank.trading.adapters;

import java.math.BigDecimal;
import java.util.List;

import exchange.notbank.trading.responses.Ticker;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

public class TickerAdapter {
  @ToJson
  List<String> toJson(Ticker ticker) {
    return List.of(ticker.endDateTime.toString(), ticker.high.toString(), ticker.low.toString(),
        ticker.open.toString(), ticker.close.toString(), ticker.volume.toString(), ticker.bid.toString(), ticker.ask.toString(), 
        ticker.instrumentId.toString(), ticker.beginDateTime.toString());
  }

  @FromJson
  Ticker fromJson(List<String> values) {
    return new Ticker(
        Long.parseLong(values.get(0)),
        new BigDecimal(values.get(1)),
        new BigDecimal(values.get(2)),
        new BigDecimal(values.get(3)),
        new BigDecimal(values.get(4)),
        new BigDecimal(values.get(5)),
        new BigDecimal(values.get(6)),
        new BigDecimal(values.get(7)),
        Integer.parseInt(values.get(8)),
        Long.parseLong(values.get(9)));
  }
}
