package exchange.notbank.instrument.responses;

import java.math.BigDecimal;

import com.squareup.moshi.Json;

public class InstrumentConfiguration {
  @Json(name = "VerificationLevel")
  public final Integer verificationLevel;
  @Json(name = "VerificationLevelName")
  public final String verificationLevelName;
  @Json(name = "OMSId")
  public final Integer omsId;
  @Json(name = "InstrumentId")
  public final Integer instrumentId;
  @Json(name = "InstrumentName")
  public final String instrumentName;
  @Json(name = "OrderBuyLimit")
  public final BigDecimal orderBuyLimit;
  @Json(name = "OrderSellLimit")
  public final BigDecimal orderSellLimit;
  @Json(name = "DailyBuyLimit")
  public final BigDecimal dailyBuyLimit;
  @Json(name = "DailySellLimit")
  public final BigDecimal dailySellLimit;
  @Json(name = "MonthlyBuyLimit")
  public final BigDecimal monthlyBuyLimit;
  @Json(name = "MonthlySellLimit")
  public final BigDecimal monthlySellLimit;
  @Json(name = "NotionalProductId")
  public final Integer notionalProductId;
  @Json(name = "OrderNotionalLimit")
  public final BigDecimal orderNotionalLimit;
  @Json(name = "DailyNotionalLimit")
  public final BigDecimal dailyNotionalLimit;
  @Json(name = "MonthlyNotionalLimit")
  public final BigDecimal monthlyNotionalLimit;
  @Json(name = "YearlyNotionalLimit")
  public final BigDecimal yearlyNotionalLimit;

  public InstrumentConfiguration(Integer verificationLevel, String verificationLevelName, Integer omsId,
      Integer instrumentId,
      String instrumentName, BigDecimal orderBuyLimit, BigDecimal orderSellLimit, BigDecimal dailyBuyLimit,
      BigDecimal dailySellLimit, BigDecimal monthlyBuyLimit, BigDecimal monthlySellLimit, Integer notionalProductId,
      BigDecimal orderNotionalLimit, BigDecimal dailyNotionalLimit, BigDecimal monthlyNotionalLimit,
      BigDecimal yearlyNotionalLimit) {
    this.verificationLevel = verificationLevel;
    this.verificationLevelName = verificationLevelName;
    this.omsId = omsId;
    this.instrumentId = instrumentId;
    this.instrumentName = instrumentName;
    this.orderBuyLimit = orderBuyLimit;
    this.orderSellLimit = orderSellLimit;
    this.dailyBuyLimit = dailyBuyLimit;
    this.dailySellLimit = dailySellLimit;
    this.monthlyBuyLimit = monthlyBuyLimit;
    this.monthlySellLimit = monthlySellLimit;
    this.notionalProductId = notionalProductId;
    this.orderNotionalLimit = orderNotionalLimit;
    this.dailyNotionalLimit = dailyNotionalLimit;
    this.monthlyNotionalLimit = monthlyNotionalLimit;
    this.yearlyNotionalLimit = yearlyNotionalLimit;
  }

  @Override
  public String toString() {
    return "Instrument [instrumentId=" + instrumentId + ", instrumentName=" + instrumentName + ", orderBuyLimit="
        + orderBuyLimit + ", orderSellLimit=" + orderSellLimit + ", dailyBuyLimit=" + dailyBuyLimit
        + ", dailySellLimit=" + dailySellLimit + ", monthlyBuyLimit=" + monthlyBuyLimit + ", monthlySellLimit="
        + monthlySellLimit + ", notionalProductId=" + notionalProductId + ", orderNotionalLimit=" + orderNotionalLimit
        + ", dailyNotionalLimit=" + dailyNotionalLimit + ", monthlyNotionalLimit=" + monthlyNotionalLimit
        + ", yearlyNotionalLimit=" + yearlyNotionalLimit + "]";
  }
}
