package exchange.notbank.trading.responses;

import java.math.BigDecimal;

import com.squareup.moshi.Json;

public class Level1Ticker {
  @Json(name = "OMSId")
  public final Integer omsId;
  @Json(name = "InstrumentId")
  public final Integer instrumentId;
  @Json(name = "BestBid")
  public final BigDecimal bestBid;
  @Json(name = "BestOffer")
  public final BigDecimal bestOffer;
  @Json(name = "LastTradedPx")
  public final BigDecimal lastTradedPrice;
  @Json(name = "LastTradedQty")
  public final BigDecimal lastTradedQuantity;
  @Json(name = "LastTradeTime")
  public final Long lastTradeTime;
  @Json(name = "SessionOpen")
  public final BigDecimal sessionOpen;
  @Json(name = "SessionHigh")
  public final BigDecimal sessionHigh;
  @Json(name = "SessionLow")
  public final BigDecimal sessionLow;
  @Json(name = "SessionClose")
  public final BigDecimal sessionClose;
  @Json(name = "Volume")
  public final BigDecimal volume;
  @Json(name = "CurrentDayVolume")
  public final BigDecimal currentDayVolume;
  @Json(name = "CurrentDayNumTrades")
  public final Integer currentDayNumTrades;
  @Json(name = "CurrentDayPxChange")
  public final BigDecimal currentDayPriceChange;
  @Json(name = "CurrentNotional")
  public final BigDecimal currentNotional;
  @Json(name = "Rolling24HrNotional")
  public final BigDecimal rolling24HrNotional;
  @Json(name = "Rolling24HrVolume")
  public final BigDecimal rolling24HrVolume;
  @Json(name = "Rolling24NumTrades")
  public final BigDecimal rolling24NumTrades;
  @Json(name = "Rolling24HrPxChange")
  public final BigDecimal rolling24HrPriceChange;
  @Json(name = "TimeStamp")
  public final String timeStamp;
  @Json(name = "BidQty")
  public final BigDecimal bidQuantity;
  @Json(name = "AskQty")
  public final BigDecimal askQuantity;
  @Json(name = "BidOrderCt")
  public final Integer bidOrderCount;
  @Json(name = "AskOrderCt")
  public final Integer askOrderCount;
  @Json(name = "Rolling24HrPxChangePercent")
  public final BigDecimal rolling24HrPriceChangePercent;

  public Level1Ticker(Integer omsId, Integer instrumentId, BigDecimal bestBid, BigDecimal bestOffer,
      BigDecimal lastTradedPrice, BigDecimal lastTradedQuantity, Long lastTradeTime, BigDecimal sessionOpen,
      BigDecimal sessionHigh, BigDecimal sessionLow, BigDecimal sessionClose, BigDecimal volume,
      BigDecimal currentDayVolume, Integer currentDayNumTrades, BigDecimal currentDayPriceChange,
      BigDecimal currentNotional, BigDecimal rolling24HrNotional, BigDecimal rolling24HrVolume,
      BigDecimal rolling24NumTrades, BigDecimal rolling24HrPriceChange, String timeStamp, BigDecimal bidQuantity,
      BigDecimal askQuantity, Integer bidOrderCount, Integer askOrderCount, BigDecimal rolling24HrPriceChangePercent) {
    this.omsId = omsId;
    this.instrumentId = instrumentId;
    this.bestBid = bestBid;
    this.bestOffer = bestOffer;
    this.lastTradedPrice = lastTradedPrice;
    this.lastTradedQuantity = lastTradedQuantity;
    this.lastTradeTime = lastTradeTime;
    this.sessionOpen = sessionOpen;
    this.sessionHigh = sessionHigh;
    this.sessionLow = sessionLow;
    this.sessionClose = sessionClose;
    this.volume = volume;
    this.currentDayVolume = currentDayVolume;
    this.currentDayNumTrades = currentDayNumTrades;
    this.currentDayPriceChange = currentDayPriceChange;
    this.currentNotional = currentNotional;
    this.rolling24HrNotional = rolling24HrNotional;
    this.rolling24HrVolume = rolling24HrVolume;
    this.rolling24NumTrades = rolling24NumTrades;
    this.rolling24HrPriceChange = rolling24HrPriceChange;
    this.timeStamp = timeStamp;
    this.bidQuantity = bidQuantity;
    this.askQuantity = askQuantity;
    this.bidOrderCount = bidOrderCount;
    this.askOrderCount = askOrderCount;
    this.rolling24HrPriceChangePercent = rolling24HrPriceChangePercent;
  }

  @Override
  public String toString() {
    return "Level1 [omsId=" + omsId + ", instrumentId=" + instrumentId + ", bestBid=" + bestBid + ", bestOffer="
        + bestOffer + ", lastTradedPrice=" + lastTradedPrice + ", lastTradedQuantity=" + lastTradedQuantity
        + ", lastTradeTime=" + lastTradeTime + ", sessionOpen=" + sessionOpen + ", sessionHigh=" + sessionHigh
        + ", sessionLow=" + sessionLow + ", sessionClose=" + sessionClose + ", volume=" + volume + ", currentDayVolume="
        + currentDayVolume + ", currentDayNumTrades=" + currentDayNumTrades + ", currentDayPriceChange="
        + currentDayPriceChange + ", currentNotional=" + currentNotional + ", rolling24HrNotional="
        + rolling24HrNotional + ", rolling24HrVolume=" + rolling24HrVolume + ", rolling24NumTrades="
        + rolling24NumTrades + ", rolling24HrPriceChange=" + rolling24HrPriceChange + ", timeStamp=" + timeStamp
        + ", bidQuantity=" + bidQuantity + ", askQuantity=" + askQuantity + ", bidOrderCount=" + bidOrderCount
        + ", askOrderCount=" + askOrderCount + ", rolling24HrPriceChangePercent=" + rolling24HrPriceChangePercent + "]";
  }
}
