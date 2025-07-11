package exchange.notbank.account.responses;

import java.math.BigDecimal;

import com.squareup.moshi.Json;

public class AccountInstrumentStatistic {
  @Json(name = "OMSID")
  public final Integer omsId;
  @Json(name = "AccountId")
  public final Integer accountId;
  @Json(name = "InstrumentId")
  public final Integer instrumentId;
  @Json(name = "InstrumentSymbol")
  public final String instrumentSymbol;
  @Json(name = "QuantityBought")
  public final BigDecimal quantityBought;
  @Json(name = "QuantitySold")
  public final BigDecimal quantitySold;
  @Json(name = "NotionalBought")
  public final BigDecimal notionalBought;
  @Json(name = "NotionalSold")
  public final BigDecimal notionalSold;
  @Json(name = "MonthlyQuantityBought")
  public final BigDecimal monthlyQuantityBought;
  @Json(name = "MonthlyQuantitySold")
  public final BigDecimal monthlyQuantitySold;
  @Json(name = "MonthlyNotionalBought")
  public final BigDecimal monthlyNotionalBought;
  @Json(name = "MonthlyNotionalSold")
  public final BigDecimal monthlyNotionalSold;
  @Json(name = "TradeVolume")
  public final BigDecimal tradeVolume;
  @Json(name = "MonthlyTradeVolume")
  public final BigDecimal monthlyTradeVolume;
  @Json(name = "TotalDayBuys")
  public final Integer totalDayBuys;
  @Json(name = "TotalDaySells")
  public final Integer totalDaySells;
  @Json(name = "TotalMonthBuys")
  public final Integer totalMonthBuys;
  @Json(name = "TotalMonthSells")
  public final Integer totalMonthSells;
  @Json(name = "NotionalConversionRate")
  public final BigDecimal notionalConversionRate;
  @Json(name = "NotionalConversionSymbol")
  public final String notionalConversionSymbol;
  @Json(name = "RollingMonthlyStartDate")
  public final Integer rollingMonthlyStartDate;
  @Json(name = "LastTradeId")
  public final Integer lastTradeId;
  @Json(name = "NotionalProductId")
  public final Integer notionalProductId;
  @Json(name = "DailyNotionalTradeVolume")
  public final BigDecimal dailyNotionalTradeVolume;
  @Json(name = "MonthlyNotionalTradeVolume")
  public final BigDecimal monthlyNotionalTradeVolume;
  @Json(name = "YearlyNotionalTradeVolume")
  public final BigDecimal yearlyNotionalTradeVolume;

  public AccountInstrumentStatistic(Integer omsId, Integer accountId, Integer instrumentId, String instrumentSymbol,
      BigDecimal quantityBought, BigDecimal quantitySold, BigDecimal notionalBought, BigDecimal notionalSold,
      BigDecimal monthlyQuantityBought, BigDecimal monthlyQuantitySold, BigDecimal monthlyNotionalBought,
      BigDecimal monthlyNotionalSold, BigDecimal tradeVolume, BigDecimal monthlyTradeVolume, Integer totalDayBuys,
      Integer totalDaySells, Integer totalMonthBuys, Integer totalMonthSells, BigDecimal notionalConversionRate,
      String notionalConversionSymbol, Integer rollingMonthlyStartDate, Integer lastTradeId, Integer notionalProductId,
      BigDecimal dailyNotionalTradeVolume, BigDecimal monthlyNotionalTradeVolume,
      BigDecimal yearlyNotionalTradeVolume) {
    this.omsId = omsId;
    this.accountId = accountId;
    this.instrumentId = instrumentId;
    this.instrumentSymbol = instrumentSymbol;
    this.quantityBought = quantityBought;
    this.quantitySold = quantitySold;
    this.notionalBought = notionalBought;
    this.notionalSold = notionalSold;
    this.monthlyQuantityBought = monthlyQuantityBought;
    this.monthlyQuantitySold = monthlyQuantitySold;
    this.monthlyNotionalBought = monthlyNotionalBought;
    this.monthlyNotionalSold = monthlyNotionalSold;
    this.tradeVolume = tradeVolume;
    this.monthlyTradeVolume = monthlyTradeVolume;
    this.totalDayBuys = totalDayBuys;
    this.totalDaySells = totalDaySells;
    this.totalMonthBuys = totalMonthBuys;
    this.totalMonthSells = totalMonthSells;
    this.notionalConversionRate = notionalConversionRate;
    this.notionalConversionSymbol = notionalConversionSymbol;
    this.rollingMonthlyStartDate = rollingMonthlyStartDate;
    this.lastTradeId = lastTradeId;
    this.notionalProductId = notionalProductId;
    this.dailyNotionalTradeVolume = dailyNotionalTradeVolume;
    this.monthlyNotionalTradeVolume = monthlyNotionalTradeVolume;
    this.yearlyNotionalTradeVolume = yearlyNotionalTradeVolume;
  }

  @Override
  public String toString() {
    return "AccountInstrumentStatistic [omsId=" + omsId + ", accountId=" + accountId + ", instrumentId=" + instrumentId
        + ", instrumentSymbol=" + instrumentSymbol + ", quantityBought=" + quantityBought + ", quantitySold="
        + quantitySold + ", notionalBought=" + notionalBought + ", notionalSold=" + notionalSold
        + ", monthlyQuantityBought=" + monthlyQuantityBought + ", monthlyQuantitySold=" + monthlyQuantitySold
        + ", monthlyNotionalBought=" + monthlyNotionalBought + ", monthlyNotionalSold=" + monthlyNotionalSold
        + ", tradeVolume=" + tradeVolume + ", monthlyTradeVolume=" + monthlyTradeVolume + ", totalDayBuys="
        + totalDayBuys + ", totalDaySells=" + totalDaySells + ", totalMonthBuys=" + totalMonthBuys
        + ", totalMonthSells=" + totalMonthSells + ", notionalConversionRate=" + notionalConversionRate
        + ", notionalConversionSymbol=" + notionalConversionSymbol + ", rollingMonthlyStartDate="
        + rollingMonthlyStartDate + ", lastTradeId=" + lastTradeId + ", notionalProductId=" + notionalProductId
        + ", dailyNotionalTradeVolume=" + dailyNotionalTradeVolume + ", monthlyNotionalTradeVolume="
        + monthlyNotionalTradeVolume + ", yearlyNotionalTradeVolume=" + yearlyNotionalTradeVolume + "]";
  }
}
