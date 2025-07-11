package exchange.notbank.fee.responses;

import java.math.BigDecimal;

import com.squareup.moshi.Json;

public class TransactionFee {
  @Json(name = "OMSId")
  public final Integer omsId;
  @Json(name = "AccountId")
  public final Integer accountId;
  @Json(name = "AccountProviderId")
  public final Integer accountProviderId;
  @Json(name = "FeeId")
  public final Integer feeId;
  @Json(name = "FeeAmt")
  public final BigDecimal feeAmount;
  @Json(name = "FeeCalcType")
  public final String feeCalcType;
  @Json(name = "IsActive")
  public final Boolean isActive;
  @Json(name = "ProductId")
  public final Integer productId;
  @Json(name = "MinimalFeeAmt")
  public final BigDecimal minimalFeeAmount;
  @Json(name = "MinimalFeeCalcType")
  public final String minimalFeeCalcType;

  public TransactionFee(Integer omsId, Integer accountId, Integer accountProviderId, Integer feeId,
      BigDecimal feeAmount,
      String feeCalcType, Boolean isActive, Integer productId, BigDecimal minimalFeeAmount, String minimalFeeCalcType) {
    this.omsId = omsId;
    this.accountId = accountId;
    this.accountProviderId = accountProviderId;
    this.feeId = feeId;
    this.feeAmount = feeAmount;
    this.feeCalcType = feeCalcType;
    this.isActive = isActive;
    this.productId = productId;
    this.minimalFeeAmount = minimalFeeAmount;
    this.minimalFeeCalcType = minimalFeeCalcType;
  }

  @Override
  public String toString() {
    return "TransactionFee [omsId=" + omsId + ", accountId=" + accountId + ", accountProviderId=" + accountProviderId
        + ", feeId=" + feeId + ", feeAmount=" + feeAmount.toPlainString() + ", feeCalcType=" + feeCalcType
        + ", isActive=" + isActive
        + ", productId=" + productId + ", minimalFeeAmount=" + minimalFeeAmount.toPlainString()
        + ", minimalFeeCalcType="
        + minimalFeeCalcType + "]";
  }
}
