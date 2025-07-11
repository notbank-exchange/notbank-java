package exchange.notbank.trading.responses;

import java.math.BigDecimal;

import com.squareup.moshi.Json;

public class Level1Summary {
  @Json(name = "OMSId")
  public final Integer omsId;
  @Json(name = "InstrumentId")
  public final Integer instrumentId;
  @Json(name = "BestBid")
  public final BigDecimal bestBid;
  @Json(name = "BestOffer")
  public final BigDecimal bestOffer;
  @Json(name = "LastTradedPx")
  public final BigDecimal lastTradedPx;
  @Json(name = "LastTradedQty")
  public final BigDecimal lastTradedQty;
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
  public final BigDecimal currentDayPxChange;
  @Json(name = "CurrentNotional")
  public final BigDecimal currentNotional;
  @Json(name = "Rolling24HrNotional")
  public final BigDecimal rolling24HrNotional;
  @Json(name = "Rolling24HrVolume")
  public final BigDecimal rolling24HrVolume;
  @Json(name = "Rolling24NumTrades")
  public final BigDecimal rolling24NumTrades;
  @Json(name = "Rolling24HrPxChange")
  public final BigDecimal rolling24HrPxChange;
  @Json(name = "TimeStamp")
  public final String timeStamp;

  public Level1Summary(Integer omsId, Integer instrumentId, BigDecimal bestBid, BigDecimal bestOffer, BigDecimal lastTradedPx,
      BigDecimal lastTradedQty, Long lastTradeTime, BigDecimal sessionOpen, BigDecimal sessionHigh,
      BigDecimal sessionLow, BigDecimal sessionClose, BigDecimal volume, BigDecimal currentDayVolume,
      Integer currentDayNumTrades, BigDecimal currentDayPxChange, BigDecimal currentNotional,
      BigDecimal rolling24HrNotional, BigDecimal rolling24HrVolume, BigDecimal rolling24NumTrades,
      BigDecimal rolling24HrPxChange, String timeStamp) {
    this.omsId = omsId;
    this.instrumentId = instrumentId;
    this.bestBid = bestBid;
    this.bestOffer = bestOffer;
    this.lastTradedPx = lastTradedPx;
    this.lastTradedQty = lastTradedQty;
    this.lastTradeTime = lastTradeTime;
    this.sessionOpen = sessionOpen;
    this.sessionHigh = sessionHigh;
    this.sessionLow = sessionLow;
    this.sessionClose = sessionClose;
    this.volume = volume;
    this.currentDayVolume = currentDayVolume;
    this.currentDayNumTrades = currentDayNumTrades;
    this.currentDayPxChange = currentDayPxChange;
    this.currentNotional = currentNotional;
    this.rolling24HrNotional = rolling24HrNotional;
    this.rolling24HrVolume = rolling24HrVolume;
    this.rolling24NumTrades = rolling24NumTrades;
    this.rolling24HrPxChange = rolling24HrPxChange;
    this.timeStamp = timeStamp;
  }

  @Override
  public String toString() {
    return "Level1Summary [omsId=" + omsId + ", instrumentId=" + instrumentId + ", bestBid=" + bestBid + ", bestOffer="
        + bestOffer + ", lastTradedPx=" + lastTradedPx + ", lastTradedQty=" + lastTradedQty + ", lastTradeTime="
        + lastTradeTime + ", sessionOpen=" + sessionOpen + ", sessionHigh=" + sessionHigh + ", sessionLow=" + sessionLow
        + ", sessionClose=" + sessionClose + ", volume=" + volume + ", currentDayVolume=" + currentDayVolume
        + ", currentDayNumTrades=" + currentDayNumTrades + ", currentDayPxChange=" + currentDayPxChange
        + ", currentNotional=" + currentNotional + ", rolling24HrNotional=" + rolling24HrNotional
        + ", rolling24HrVolume=" + rolling24HrVolume + ", rolling24NumTrades=" + rolling24NumTrades
        + ", rolling24HrPxChange=" + rolling24HrPxChange + ", timeStamp=" + timeStamp + "]";
  }
}
