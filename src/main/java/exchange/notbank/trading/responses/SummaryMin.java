package exchange.notbank.trading.responses;

import java.math.BigDecimal;

import com.squareup.moshi.Json;

public class SummaryMin {
  @Json(name = "InstrumentId")
  public final Integer instrumentId;
  @Json(name = "InstrumentSymbol")
  public final String instrumentSymbol;
  @Json(name = "LastTradedPX")
  public final BigDecimal lastTradedPX;
  @Json(name = "Rolling24HrVolume")
  public final BigDecimal rolling24HrVolume;
  @Json(name = "Rolling24HrPxChange")
  public final BigDecimal rolling24HrPxChange;
  @Json(name = "Rolling24HrPxChangePercent")
  public final BigDecimal rolling24HrPxChangePercent;

  public SummaryMin(
      Integer InstrumentId,
      String InstrumentSymbol,
      BigDecimal LastTradedPX,
      BigDecimal Rolling24HrVolume,
      BigDecimal Rolling24HrPxChange,
      BigDecimal Rolling24HrPxChangePercent) {
    this.instrumentId = InstrumentId;
    this.instrumentSymbol = InstrumentSymbol;
    this.lastTradedPX = LastTradedPX;
    this.rolling24HrVolume = Rolling24HrVolume;
    this.rolling24HrPxChange = Rolling24HrPxChange;
    this.rolling24HrPxChangePercent = Rolling24HrPxChangePercent;
  }

  @Override
  public String toString() {
    return "SummaryMin [instrumentId=" + instrumentId + ", instrumentSymbol=" + instrumentSymbol + ", lastTradedPX="
        + lastTradedPX + ", rolling24HrVolume=" + rolling24HrVolume + ", rolling24HrPxChange=" + rolling24HrPxChange
        + ", rolling24HrPxChangePercent=" + rolling24HrPxChangePercent + "]";
  }

}
