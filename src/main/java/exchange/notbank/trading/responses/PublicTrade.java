package exchange.notbank.trading.responses;

import java.math.BigDecimal;

import exchange.notbank.trading.constants.TradeType;
import com.squareup.moshi.Json;

public class PublicTrade {
  @Json(name = "trade_id")
  public final Integer tradeId;
  @Json(name = "price")
  public final BigDecimal price;
  @Json(name = "base_volume")
  public final BigDecimal baseVolume;
  @Json(name = "quote_volume")
  public final BigDecimal quoteVolume;
  @Json(name = "timestamp")
  public final String timestamp;
  @Json(name = "type")
  public final TradeType type;

  public PublicTrade(Integer tradeId, BigDecimal price, BigDecimal baseVolume, BigDecimal quoteVolume, String timestamp,
      TradeType type) {
    this.tradeId = tradeId;
    this.price = price;
    this.baseVolume = baseVolume;
    this.quoteVolume = quoteVolume;
    this.timestamp = timestamp;
    this.type = type;
  }

  @Override
  public String toString() {
    return "PublicTrade [tradeId=" + tradeId + ", price=" + price + ", baseVolume=" + baseVolume + ", quoteVolume="
        + quoteVolume + ", timestamp=" + timestamp + ", type=" + type + "]";
  }
}
