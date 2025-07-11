package exchange.notbank.trading.responses;

import java.math.BigDecimal;

import com.squareup.moshi.Json;

public class Summary {
  @Json(name = "trading_pairs")
  public final String tradingPairs;
  @Json(name = "last_price")
  public final BigDecimal lastPrice;
  @Json(name = "lowest_ask")
  public final BigDecimal lowestAsk;
  @Json(name = "highest_bid")
  public final BigDecimal highestBid;
  @Json(name = "base_volume")
  public final BigDecimal baseVolume;
  @Json(name = "quote_volume")
  public final BigDecimal quoteVolume;
  @Json(name = "price_change_percent_24h")
  public final BigDecimal priceChangePercent24h;
  @Json(name = "highest_price_24h")
  public final BigDecimal highestPrice24h;
  @Json(name = "lowest_price_24h")
  public final BigDecimal lowestPrice24h;

  public Summary(String tradingPairs, BigDecimal lastPrice, BigDecimal lowestAsk, BigDecimal highestBid,
      BigDecimal baseVolume, BigDecimal quoteVolume, BigDecimal priceChangePercent24h, BigDecimal highestPrice24h,
      BigDecimal lowestPrice24h) {
    this.tradingPairs = tradingPairs;
    this.lastPrice = lastPrice;
    this.lowestAsk = lowestAsk;
    this.highestBid = highestBid;
    this.baseVolume = baseVolume;
    this.quoteVolume = quoteVolume;
    this.priceChangePercent24h = priceChangePercent24h;
    this.highestPrice24h = highestPrice24h;
    this.lowestPrice24h = lowestPrice24h;
  }

  @Override
  public String toString() {
    return "Summary [tradingPairs=" + tradingPairs + ", lastPrice=" + lastPrice + ", lowestAsk=" + lowestAsk
        + ", highestBid=" + highestBid + ", baseVolume=" + baseVolume + ", quoteVolume=" + quoteVolume
        + ", priceChangePercent24h=" + priceChangePercent24h + ", highestPrice24h=" + highestPrice24h
        + ", lowestPrice24h=" + lowestPrice24h + "]";
  }
}
