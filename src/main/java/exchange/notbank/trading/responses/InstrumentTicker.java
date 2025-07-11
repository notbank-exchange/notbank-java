package exchange.notbank.trading.responses;

import java.math.BigDecimal;
import com.squareup.moshi.Json;

public class InstrumentTicker {
    @Json(name = "base_id")
    public final Integer baseId;
    @Json(name = "quote_id")
    public final Integer quoteId;
    @Json(name = "last_price")
    public final BigDecimal lastPrice;
    @Json(name = "base_volume")
    public final BigDecimal baseVolume;
    @Json(name = "quote_volume")
    public final BigDecimal quoteVolume;

    public String instrumentId;

    public InstrumentTicker(
            String instrumentId,
            Integer baseId,
            Integer quoteId,
            BigDecimal lastPrice,
            BigDecimal baseVolume,
            BigDecimal quoteVolume) {
        this.instrumentId = instrumentId;
        this.baseId = baseId;
        this.quoteId = quoteId;
        this.lastPrice = lastPrice;
        this.baseVolume = baseVolume;
        this.quoteVolume = quoteVolume;
    }

    @Override
    public String toString() {
        return "InstrumentTicker [id=" + instrumentId + ", baseId=" + baseId + ", quoteId=" + quoteId + ", lastPrice="
                + lastPrice + ", baseVolume=" + baseVolume + ", quoteVolume="
                + quoteVolume + "]";
    }
}
