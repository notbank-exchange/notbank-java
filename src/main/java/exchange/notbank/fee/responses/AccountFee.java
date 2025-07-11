package exchange.notbank.fee.responses;

import java.math.BigDecimal;

import exchange.notbank.fee.constants.FeeType;
import exchange.notbank.trading.constants.OrderType;
import com.squareup.moshi.Json;

public class AccountFee {
  @Json(name = "FeeId")
  public final Long feeId;
  @Json(name = "OMSId")
  public final Integer omsId;
  @Json(name = "FeeTier")
  public final Integer feeTier;
  @Json(name = "AccountId")
  public final Integer accountId;
  @Json(name = "FeeAmt")
  public final BigDecimal feeAmount;
  @Json(name = "FeeCalcType")
  public final String feeCalcType;
  @Json(name = "FeeType")
  public final FeeType feeType;
  @Json(name = "LadderThreshold")
  public final BigDecimal ladderThreshold;
  @Json(name = "LadderSeconds")
  public final Long ladderSeconds;
  @Json(name = "IsActive")
  public final Boolean isActive;
  @Json(name = "InstrumentId")
  public final Integer instrumentId;
  @Json(name = "OrderType")
  public final OrderType orderType;
  @Json(name = "PeggedProductId")
  public final Integer peggedProductId;

  public AccountFee(Long feeId, Integer omsId, Integer feeTier, Integer accountId, BigDecimal feeAmount, String feeCalcType,
      FeeType feeType, BigDecimal ladderThreshold, Long ladderSeconds, Boolean isActive, Integer instrumentId,
      OrderType orderType, Integer peggedProductId) {
    this.feeId = feeId;
    this.omsId = omsId;
    this.feeTier = feeTier;
    this.accountId = accountId;
    this.feeAmount = feeAmount;
    this.feeCalcType = feeCalcType;
    this.feeType = feeType;
    this.ladderThreshold = ladderThreshold;
    this.ladderSeconds = ladderSeconds;
    this.isActive = isActive;
    this.instrumentId = instrumentId;
    this.orderType = orderType;
    this.peggedProductId = peggedProductId;
  }

  @Override
  public String toString() {
    return "OmsFee [feeId=" + feeId + ", omsId=" + omsId + ", feeTier=" + feeTier + ", accountId=" + accountId
        + ", feeAmount=" + feeAmount.toPlainString() + ", feeCalcType=" + feeCalcType + ", feeType=" + feeType
        + ", ladderThreshold="
        + ladderThreshold.toPlainString() + ", ladderSeconds=" + ladderSeconds + ", isActive=" + isActive
        + ", instrumentId="
        + instrumentId + ", orderType=" + orderType + ", peggedProductId=" + peggedProductId + "]";
  }

}
