package exchange.notbank.product.responses;

import java.math.BigDecimal;

import com.squareup.moshi.Json;

public class ProductConfiguration {
  @Json(name = "VerificationLevel")
  public final Integer verificationLevel;
  @Json(name = "VerificationLevelName")
  public final String verificationLevelName;
  @Json(name = "OMSId")
  public final Integer omsId;
  @Json(name = "ProductId")
  public final Integer productId;
  @Json(name = "ProductName")
  public final String productName;
  @Json(name = "AutoWithdrawThreshold")
  public final BigDecimal autoWithdrawThreshold;
  @Json(name = "DailyDepositLimit")
  public final BigDecimal dailyDepositLimit;
  @Json(name = "DailyDepositNotionalLimit")
  public final BigDecimal dailyDepositNotionalLimit;
  @Json(name = "MonthlyDepositLimit")
  public final BigDecimal monthlyDepositLimit;
  @Json(name = "MonthlyDepositNotionalLimit")
  public final BigDecimal monthlyDepositNotionalLimit;
  @Json(name = "YearlyDepositLimit")
  public final BigDecimal yearlyDepositLimit;
  @Json(name = "YearlyDepositNotionalLimit")
  public final BigDecimal yearlyDepositNotionalLimit;
  @Json(name = "DailyWithdrawLimit")
  public final BigDecimal dailyWithdrawLimit;
  @Json(name = "DailyWithdrawNotionalLimit")
  public final BigDecimal dailyWithdrawNotionalLimit;
  @Json(name = "MonthlyWithdrawLimit")
  public final BigDecimal monthlyWithdrawLimit;
  @Json(name = "MonthlyWithdrawNotionalLimit")
  public final BigDecimal monthlyWithdrawNotionalLimit;
  @Json(name = "YearlyWithdrawLimit")
  public final BigDecimal yearlyWithdrawLimit;
  @Json(name = "YearlyWithdrawNotionalLimit")
  public final BigDecimal yearlyWithdrawNotionalLimit;
  @Json(name = "DailyTransferNotionalLimit")
  public final BigDecimal dailyTransferNotionalLimit;
  @Json(name = "NotionalProductId")
  public final Integer notionalProductId;
  @Json(name = "OverLimitRejected")
  public final Boolean overLimitRejected;
  @Json(name = "WithdrawProcessingDelaySec")
  public final Integer withdrawProcessingDelaySec;
  @Json(name = "DepositTicketWorkflow")
  public final String depositTicketWorkflow;
  @Json(name = "WithdrawTicketWorkflow")
  public final String withdrawTicketWorkflow;
  @Json(name = "RequireWhitelistedAddress")
  public final Boolean requireWhitelistedAddress;
  @Json(name = "AutoAcceptWhitelistedAddress")
  public final Boolean autoAcceptWhitelistedAddress;

  public ProductConfiguration(Integer verificationLevel, String verificationLevelName, Integer omsId,
      Integer productId, String productName, BigDecimal autoWithdrawThreshold, BigDecimal dailyDepositLimit,
      BigDecimal dailyDepositNotionalLimit, BigDecimal monthlyDepositLimit, BigDecimal monthlyDepositNotionalLimit,
      BigDecimal yearlyDepositLimit, BigDecimal yearlyDepositNotionalLimit, BigDecimal dailyWithdrawLimit,
      BigDecimal dailyWithdrawNotionalLimit, BigDecimal monthlyWithdrawLimit,
      BigDecimal monthlyWithdrawNotionalLimit, BigDecimal yearlyWithdrawLimit,
      BigDecimal yearlyWithdrawNotionalLimit, BigDecimal dailyTransferNotionalLimit, Integer notionalProductId,
      Boolean overLimitRejected, Integer withdrawProcessingDelaySec, String depositTicketWorkflow,
      String withdrawTicketWorkflow, Boolean requireWhitelistedAddress, Boolean autoAcceptWhitelistedAddress) {
    this.verificationLevel = verificationLevel;
    this.verificationLevelName = verificationLevelName;
    this.omsId = omsId;
    this.productId = productId;
    this.productName = productName;
    this.autoWithdrawThreshold = autoWithdrawThreshold;
    this.dailyDepositLimit = dailyDepositLimit;
    this.dailyDepositNotionalLimit = dailyDepositNotionalLimit;
    this.monthlyDepositLimit = monthlyDepositLimit;
    this.monthlyDepositNotionalLimit = monthlyDepositNotionalLimit;
    this.yearlyDepositLimit = yearlyDepositLimit;
    this.yearlyDepositNotionalLimit = yearlyDepositNotionalLimit;
    this.dailyWithdrawLimit = dailyWithdrawLimit;
    this.dailyWithdrawNotionalLimit = dailyWithdrawNotionalLimit;
    this.monthlyWithdrawLimit = monthlyWithdrawLimit;
    this.monthlyWithdrawNotionalLimit = monthlyWithdrawNotionalLimit;
    this.yearlyWithdrawLimit = yearlyWithdrawLimit;
    this.yearlyWithdrawNotionalLimit = yearlyWithdrawNotionalLimit;
    this.dailyTransferNotionalLimit = dailyTransferNotionalLimit;
    this.notionalProductId = notionalProductId;
    this.overLimitRejected = overLimitRejected;
    this.withdrawProcessingDelaySec = withdrawProcessingDelaySec;
    this.depositTicketWorkflow = depositTicketWorkflow;
    this.withdrawTicketWorkflow = withdrawTicketWorkflow;
    this.requireWhitelistedAddress = requireWhitelistedAddress;
    this.autoAcceptWhitelistedAddress = autoAcceptWhitelistedAddress;
  }

  @Override
  public String toString() {
    return "ProductConfiguration [verificationLevel=" + verificationLevel + ", verificationLevelName="
        + verificationLevelName + ", omsId=" + omsId + ", productId=" + productId + ", productName=" + productName
        + ", autoWithdrawThreshold=" + autoWithdrawThreshold + ", dailyDepositLimit=" + dailyDepositLimit
        + ", dailyDepositNotionalLimit=" + dailyDepositNotionalLimit + ", monthlyDepositLimit=" + monthlyDepositLimit
        + ", monthlyDepositNotionalLimit=" + monthlyDepositNotionalLimit + ", yearlyDepositLimit=" + yearlyDepositLimit
        + ", yearlyDepositNotionalLimit=" + yearlyDepositNotionalLimit + ", dailyWithdrawLimit=" + dailyWithdrawLimit
        + ", dailyWithdrawNotionalLimit=" + dailyWithdrawNotionalLimit + ", monthlyWithdrawLimit="
        + monthlyWithdrawLimit + ", monthlyWithdrawNotionalLimit=" + monthlyWithdrawNotionalLimit
        + ", yearlyWithdrawLimit=" + yearlyWithdrawLimit + ", yearlyWithdrawNotionalLimit="
        + yearlyWithdrawNotionalLimit + ", dailyTransferNotionalLimit=" + dailyTransferNotionalLimit
        + ", notionalProductId=" + notionalProductId + ", overLimitRejected=" + overLimitRejected
        + ", withdrawProcessingDelaySec=" + withdrawProcessingDelaySec + ", depositTicketWorkflow="
        + depositTicketWorkflow + ", withdrawTicketWorkflow=" + withdrawTicketWorkflow + ", requireWhitelistedAddress="
        + requireWhitelistedAddress + ", autoAcceptWhitelistedAddress=" + autoAcceptWhitelistedAddress + "]";
  }
}
