package exchange.notbank.account.responses;

import exchange.notbank.subscription.responses.AccountEvent;
import exchange.notbank.users.constants.AccountType;
import exchange.notbank.users.constants.FeeProductType;
import exchange.notbank.users.constants.RiskType;
import com.squareup.moshi.Json;

public class AccountInfo implements AccountEvent {
  @Json(name = "OMSID")
  public final Integer omsid;
  @Json(name = "AccountId")
  public final Integer accountId;
  @Json(name = "AccountName")
  public final String accountName;
  @Json(name = "AccountHandle")
  public final String accountHandle;
  @Json(name = "FirmId")
  public final String firmId;
  @Json(name = "FirmName")
  public final String firmName;
  @Json(name = "AccountType")
  public final AccountType accountType;
  @Json(name = "FeeGroupId")
  public final Integer feeGroupId;
  @Json(name = "ParentID")
  public final Integer parentId;
  @Json(name = "RiskType")
  public final RiskType riskType;
  @Json(name = "VerificationLevel")
  public final Integer verificationLevel;
  @Json(name = "VerificationLevelName")
  public final String verificationLevelName;
  @Json(name = "CreditTier")
  public final Integer creditTier;
  @Json(name = "FeeProductType")
  public final FeeProductType feeProductType;
  @Json(name = "FeeProduct")
  public final Integer feeProduct;
  @Json(name = "RefererId")
  public final Integer refererId;
  @Json(name = "LoyaltyProductId")
  public final Integer loyaltyProductId;
  @Json(name = "LoyaltyEnabled")
  public final Boolean loyaltyEnabled;
  @Json(name = "PriceTier")
  public final Integer priceTier;
  @Json(name = "Frozen")
  public final Boolean frozen;

  public AccountInfo(Integer omsid, Integer accountId, String accountName, String accountHandle, String firmId,
      String firmName, AccountType accountType, Integer feeGroupId, Integer parentId, RiskType riskType,
      Integer verificationLevel,
      String verificationLevelName, Integer creditTier, FeeProductType feeProductType, Integer feeProduct, Integer refererId,
      Integer loyaltyProductId, Boolean loyaltyEnabled, Integer priceTier, Boolean frozen) {
    this.omsid = omsid;
    this.accountId = accountId;
    this.accountName = accountName;
    this.accountHandle = accountHandle;
    this.firmId = firmId;
    this.firmName = firmName;
    this.accountType = accountType;
    this.feeGroupId = feeGroupId;
    this.parentId = parentId;
    this.riskType = riskType;
    this.verificationLevel = verificationLevel;
    this.verificationLevelName = verificationLevelName;
    this.creditTier = creditTier;
    this.feeProductType = feeProductType;
    this.feeProduct = feeProduct;
    this.refererId = refererId;
    this.loyaltyProductId = loyaltyProductId;
    this.loyaltyEnabled = loyaltyEnabled;
    this.priceTier = priceTier;
    this.frozen = frozen;
  }

  @Override
  public String toString() {
    return "AccountInfo [omsid=" + omsid + ", accountId=" + accountId + ", accountName=" + accountName
        + ", accountHandle=" + accountHandle + ", firmId=" + firmId + ", firmName=" + firmName + ", accountType="
        + accountType + ", feeGroupId=" + feeGroupId + ", parentId=" + parentId + ", riskType=" + riskType
        + ", verificationLevel=" + verificationLevel + ", verificationLevelName=" + verificationLevelName
        + ", creditTier=" + creditTier + ", feeProductType=" + feeProductType + ", feeProduct=" + feeProduct
        + ", refererId=" + refererId + ", loyaltyProductId=" + loyaltyProductId + ", loyaltyEnabled=" + loyaltyEnabled
        + ", priceTier=" + priceTier + ", frozen=" + frozen + "]";
  }
}
