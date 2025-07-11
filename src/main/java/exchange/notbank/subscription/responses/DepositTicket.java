package exchange.notbank.subscription.responses;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import exchange.notbank.subscription.constants.DepositTicketStatus;
import com.squareup.moshi.Json;

public class DepositTicket implements AccountEvent {
  @Json(name = "AssetManagerId")
  public final Integer assetManagerId;
  @Json(name = "AccountProviderId")
  public final Integer accountProviderId;
  @Json(name = "AccountId")
  public final Integer accountId;
  @Json(name = "AssetId")
  public final Integer assetId;
  @Json(name = "AccountName")
  public final String accountName;
  @Json(name = "AssetName")
  public final String assetName;
  @Json(name = "Amount")
  public final BigDecimal amount;
  @Json(name = "NotionalValue")
  public final BigDecimal notionalValue;
  @Json(name = "NotionalProductId")
  public final Integer notionalProductId;
  @Json(name = "OMSId")
  public final Integer omsId;
  @Json(name = "RequestCode")
  public final UUID requestCode;
  @Json(name = "ReferenceId")
  public final String referenceId;
  @Json(name = "RequestIP")
  public final String requestIp;
  @Json(name = "RequestUser")
  public final Integer requestUser;
  @Json(name = "RequestUserName")
  public final String requestUserName;
  @Json(name = "OperatorId")
  public final Integer operatorId;
  @Json(name = "Status")
  public final DepositTicketStatus status;
  @Json(name = "FeeAmt")
  public final BigDecimal feeAmt;
  @Json(name = "UpdatedByUser")
  public final Integer updatedByUser;
  @Json(name = "UpdatedByUserName")
  public final String updatedByUserName;
  @Json(name = "TicketNumber")
  public final Long ticketNumber;
  @Json(name = "DepositInfo")
  public final String depositInfo;
  @Json(name = "RejectReason")
  public final String rejectReason;
  @Json(name = "CreatedTimestamp")
  public final String createdTimestamp;
  @Json(name = "LastUpdateTimeStamp")
  public final String lastUpdateTimeStamp;
  @Json(name = "CreatedTimestampTick")
  public final Long createdTimestampTick;
  @Json(name = "LastUpdateTimeStampTick")
  public final Long lastUpdateTimeStampTick;
  @Json(name = "Comments")
  public final List<String> comments;
  @Json(name = "Attachments")
  public final List<String> attachments;

  public DepositTicket(Integer assetManagerId, Integer accountProviderId, Integer accountId, Integer assetId,
      String accountName, String assetName, BigDecimal amount, BigDecimal notionalValue, Integer notionalProductId,
      Integer omsId, UUID requestCode, String referenceId, String requestIp, Integer requestUser,
      String requestUserName, Integer operatorId, DepositTicketStatus status, BigDecimal feeAmt, Integer updatedByUser,
      String updatedByUserName, Long ticketNumber, String depositInfo, String rejectReason, String createdTimestamp,
      String lastUpdateTimeStamp, Long createdTimestampTick, Long lastUpdateTimeStampTick, List<String> comments,
      List<String> attachments) {
    this.assetManagerId = assetManagerId;
    this.accountProviderId = accountProviderId;
    this.accountId = accountId;
    this.assetId = assetId;
    this.accountName = accountName;
    this.assetName = assetName;
    this.amount = amount;
    this.notionalValue = notionalValue;
    this.notionalProductId = notionalProductId;
    this.omsId = omsId;
    this.requestCode = requestCode;
    this.referenceId = referenceId;
    this.requestIp = requestIp;
    this.requestUser = requestUser;
    this.requestUserName = requestUserName;
    this.operatorId = operatorId;
    this.status = status;
    this.feeAmt = feeAmt;
    this.updatedByUser = updatedByUser;
    this.updatedByUserName = updatedByUserName;
    this.ticketNumber = ticketNumber;
    this.depositInfo = depositInfo;
    this.rejectReason = rejectReason;
    this.createdTimestamp = createdTimestamp;
    this.lastUpdateTimeStamp = lastUpdateTimeStamp;
    this.createdTimestampTick = createdTimestampTick;
    this.lastUpdateTimeStampTick = lastUpdateTimeStampTick;
    this.comments = comments;
    this.attachments = attachments;
  }

  @Override
  public String toString() {
    return "DepositTicket [assetManagerId=" + assetManagerId + ", accountProviderId=" + accountProviderId
        + ", accountId=" + accountId + ", assetId=" + assetId + ", accountName=" + accountName + ", assetName="
        + assetName + ", amount=" + amount + ", notionalValue=" + notionalValue + ", notionalProductId="
        + notionalProductId + ", omsId=" + omsId + ", requestCode=" + requestCode + ", referenceId=" + referenceId
        + ", requestIp=" + requestIp + ", requestUser=" + requestUser + ", requestUserName=" + requestUserName
        + ", operatorId=" + operatorId + ", status=" + status + ", feeAmt=" + feeAmt + ", updatedByUser="
        + updatedByUser + ", updatedByUserName=" + updatedByUserName + ", ticketNumber=" + ticketNumber
        + ", depositInfo=" + depositInfo + ", rejectReason=" + rejectReason + ", createdTimestamp=" + createdTimestamp
        + ", lastUpdateTimeStamp=" + lastUpdateTimeStamp + ", createdTimestampTick=" + createdTimestampTick
        + ", lastUpdateTimeStampTick=" + lastUpdateTimeStampTick + ", comments=" + comments + ", attachments="
        + attachments + "]";
  }
}
