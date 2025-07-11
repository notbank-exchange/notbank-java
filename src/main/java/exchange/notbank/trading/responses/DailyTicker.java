package exchange.notbank.trading.responses;

import java.util.Map;

public class DailyTicker {
    public Map<String, InstrumentTicker> tickers;

    public DailyTicker(Map<String, InstrumentTicker> tickers) {
        this.tickers = tickers;
        for (Map.Entry<String, InstrumentTicker> entry : tickers.entrySet()) {
            entry.getValue().instrumentId = entry.getKey();
        }
    }
}
