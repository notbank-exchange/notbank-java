package exchange.notbank.trading.responses;

import java.math.BigDecimal;

public class Ticker {
  public final Long endDateTime; // in UTC and POSIX format
  public final BigDecimal high;
  public final BigDecimal low;
  public final BigDecimal open;
  public final BigDecimal close;
  public final BigDecimal volume;
  public final BigDecimal bid;
  public final BigDecimal ask;
  public final Integer instrumentId;
  public final Long beginDateTime; // in UTC and POSIX format

  public Ticker(Long endDateTime, BigDecimal high, BigDecimal low, BigDecimal open, BigDecimal close, BigDecimal volume,
      BigDecimal bid, BigDecimal ask, Integer instrumentId, Long beginDateTime) {
    this.endDateTime = endDateTime;
    this.high = high;
    this.low = low;
    this.open = open;
    this.close = close;
    this.volume = volume;
    this.bid = bid;
    this.ask = ask;
    this.instrumentId = instrumentId;
    this.beginDateTime = beginDateTime;
  }

  @Override
  public String toString() {
    return "Ticker [endDateTime=" + endDateTime + ", high=" + high + ", low=" + low + ", open=" + open + ", close="
        + close + ", volume=" + volume + ", bid=" + bid + ", ask=" + ask + ", instrumentId=" + instrumentId
        + ", beginDateTime=" + beginDateTime + "]";
  }
}
