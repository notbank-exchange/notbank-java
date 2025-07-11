package exchange.notbank.trading.responses;

import java.math.BigDecimal;

import exchange.notbank.subscription.responses.AccountEvent;
import com.squareup.moshi.Json;

public class Balance implements AccountEvent {
  @Json(name = "OmsId")
  public final Integer omsId;
  @Json(name = "AccountId")
  public final Integer accountId;
  @Json(name = "ProductSymbol")
  public final String productSymbol;
  @Json(name = "ProductId")
  public final Integer productId;
  @Json(name = "Amount")
  public final BigDecimal amount;
  @Json(name = "Hold")
  public final BigDecimal hold;
  @Json(name = "PendingDeposits")
  public final BigDecimal pendingDeposits;
  @Json(name = "PendingWithdraws")
  public final BigDecimal pendingWithdraws;
  @Json(name = "TotalDayDeposits")
  public final BigDecimal totalDayDeposits;
  @Json(name = "TotalMonthDeposits")
  public final BigDecimal totalMonthDeposits;
  @Json(name = "TotalYearDeposits")
  public final BigDecimal totalYearDeposits;
  @Json(name = "TotalDayDepositNotional")
  public final BigDecimal totalDayDepositNotional;
  @Json(name = "TotalMonthDepositNotional")
  public final BigDecimal totalMonthDepositNotional;
  @Json(name = "TotalYearDepositNotional")
  public final BigDecimal totalYearDepositNotional;
  @Json(name = "TotalDayWithdraws")
  public final BigDecimal totalDayWithdraws;
  @Json(name = "TotalMonthWithdraws")
  public final BigDecimal totalMonthWithdraws;
  @Json(name = "TotalYearWithdraws")
  public final BigDecimal totalYearWithdraws;
  @Json(name = "TotalDayWithdrawNotional")
  public final BigDecimal totalDayWithdrawNotional;
  @Json(name = "TotalMonthWithdrawNotional")
  public final BigDecimal totalMonthWithdrawNotional;
  @Json(name = "TotalYearWithdrawNotional")
  public final BigDecimal totalYearWithdrawNotional;
  @Json(name = "NotionalProductId")
  public final Integer notionalProductId;
  @Json(name = "NotionalProductSymbol")
  public final String notionalProductSymbol;
  @Json(name = "NotionalValue")
  public final BigDecimal notionalValue;
  @Json(name = "NotionalHoldAmount")
  public final BigDecimal notionalHoldAmount;
  @Json(name = "NotionalRate")
  public final BigDecimal notionalRate;
  @Json(name = "TotalDayTransferNotional")
  public final BigDecimal totalDayTransferNotional;

  public Balance(Integer omsId, Integer accountId, String productSymbol, Integer productId, BigDecimal amount,
      BigDecimal hold, BigDecimal pendingDeposits, BigDecimal pendingWithdraws, BigDecimal totalDayDeposits,
      BigDecimal totalMonthDeposits, BigDecimal totalYearDeposits, BigDecimal totalDayDepositNotional,
      BigDecimal totalMonthDepositNotional, BigDecimal totalYearDepositNotional, BigDecimal totalDayWithdraws,
      BigDecimal totalMonthWithdraws, BigDecimal totalYearWithdraws, BigDecimal totalDayWithdrawNotional,
      BigDecimal totalMonthWithdrawNotional, BigDecimal totalYearWithdrawNotional, Integer notionalProductId,
      String notionalProductSymbol, BigDecimal notionalValue, BigDecimal notionalHoldAmount, BigDecimal notionalRate,
      BigDecimal totalDayTransferNotional) {
    this.omsId = omsId;
    this.accountId = accountId;
    this.productSymbol = productSymbol;
    this.productId = productId;
    this.amount = amount;
    this.hold = hold;
    this.pendingDeposits = pendingDeposits;
    this.pendingWithdraws = pendingWithdraws;
    this.totalDayDeposits = totalDayDeposits;
    this.totalMonthDeposits = totalMonthDeposits;
    this.totalYearDeposits = totalYearDeposits;
    this.totalDayDepositNotional = totalDayDepositNotional;
    this.totalMonthDepositNotional = totalMonthDepositNotional;
    this.totalYearDepositNotional = totalYearDepositNotional;
    this.totalDayWithdraws = totalDayWithdraws;
    this.totalMonthWithdraws = totalMonthWithdraws;
    this.totalYearWithdraws = totalYearWithdraws;
    this.totalDayWithdrawNotional = totalDayWithdrawNotional;
    this.totalMonthWithdrawNotional = totalMonthWithdrawNotional;
    this.totalYearWithdrawNotional = totalYearWithdrawNotional;
    this.notionalProductId = notionalProductId;
    this.notionalProductSymbol = notionalProductSymbol;
    this.notionalValue = notionalValue;
    this.notionalHoldAmount = notionalHoldAmount;
    this.notionalRate = notionalRate;
    this.totalDayTransferNotional = totalDayTransferNotional;
  }

  @Override
  public String toString() {
    return "Balance [omsId=" + omsId + ", accountId=" + accountId + ", productSymbol=" + productSymbol + ", productId="
        + productId + ", amount=" + amount.toPlainString() + ", hold=" + hold.toPlainString() + ", pendingDeposits="
        + pendingDeposits.toPlainString()
        + ", pendingWithdraws=" + pendingWithdraws.toPlainString() + ", totalDayDeposits="
        + totalDayDeposits.toPlainString() + ", totalMonthDeposits="
        + totalMonthDeposits.toPlainString() + ", totalYearDeposits=" + totalYearDeposits.toPlainString()
        + ", totalDayDepositNotional="
        + totalDayDepositNotional.toPlainString() + ", totalMonthDepositNotional="
        + totalMonthDepositNotional.toPlainString()
        + ", totalYearDepositNotional=" + totalYearDepositNotional.toPlainString() + ", totalDayWithdraws="
        + totalDayWithdraws.toPlainString()
        + ", totalMonthWithdraws=" + totalMonthWithdraws.toPlainString() + ", totalYearWithdraws="
        + totalYearWithdraws.toPlainString()
        + ", totalDayWithdrawNotional=" + totalDayWithdrawNotional.toPlainString() + ", totalMonthWithdrawNotional="
        + totalMonthWithdrawNotional.toPlainString() + ", totalYearWithdrawNotional="
        + totalYearWithdrawNotional.toPlainString()
        + ", notionalProductId=" + notionalProductId + ", notionalProductSymbol=" + notionalProductSymbol
        + ", notionalValue=" + notionalValue.toPlainString() + ", notionalHoldAmount="
        + notionalHoldAmount.toPlainString() + ", notionalRate="
        + notionalRate.toPlainString() + ", totalDayTransferNotional=" + totalDayTransferNotional.toPlainString() + "]";
  }

}
