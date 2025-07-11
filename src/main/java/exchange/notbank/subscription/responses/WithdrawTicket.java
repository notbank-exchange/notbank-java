package exchange.notbank.subscription.responses;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import exchange.notbank.subscription.constants.WithdrawTicketStatus;
import com.squareup.moshi.Json;

public class WithdrawTicket implements AccountEvent {
  @Json(name = "AssetManagerId")
  public final Integer assetManagerId;
  @Json(name = "AccountProviderId")
  public final Integer accountProviderId;
  @Json(name = "AccountId")
  public final Integer accountId;
  @Json(name = "AccountName")
  public final String accountName;
  @Json(name = "AssetId")
  public final Integer assetId;
  @Json(name = "AssetName")
  public final String assetName;
  @Json(name = "Amount")
  public final BigDecimal amount;
  @Json(name = "NotionalValue")
  public final BigDecimal notionalValue;
  @Json(name = "NotionalProductId")
  public final Integer notionalProductId;
  @Json(name = "TemplateForm")
  public final String templateForm;
  @Json(name = "TemplateFormType")
  public final String templateFormType;
  @Json(name = "OMSId")
  public final Integer omsId;
  @Json(name = "RequestCode")
  public final UUID requestCode;
  @Json(name = "RequestIP")
  public final String requestIP;
  @Json(name = "RequestUserId")
  public final Integer requestUserId;
  @Json(name = "RequestUserName")
  public final String requestUserName;
  @Json(name = "OperatorId")
  public final Integer operatorId;
  @Json(name = "Status")
  public final WithdrawTicketStatus status;
  @Json(name = "FeeAmt")
  public final BigDecimal feeAmt;
  @Json(name = "UpdatedByUser")
  public final Integer updatedByUser;
  @Json(name = "UpdatedByUserName")
  public final String updatedByUserName;
  @Json(name = "TicketNumber")
  public final Long ticketNumber;
  @Json(name = "WithdrawTransactionDetails")
  public final String withdrawTransactionDetails;
  @Json(name = "RejectReason")
  public final String rejectReason;
  @Json(name = "CreatedTimestamp")
  public final String createdTimestamp;
  @Json(name = "LastUpdateTimestamp")
  public final String lastUpdateTimestamp;
  @Json(name = "CreatedTimestampTick")
  public final Long createdTimestampTick;
  @Json(name = "LastUpdateTimestampTick")
  public final Long lastUpdateTimestampTick;
  @Json(name = "Attachments")
  public final List<String> attachments;
  @Json(name = "AuditLog")
  public final List<String> auditLog;

  public WithdrawTicket(Integer assetManagerId, Integer accountProviderId, Integer accountId, String accountName,
      Integer assetId, String assetName, BigDecimal amount, BigDecimal notionalValue, Integer notionalProductId,
      String templateForm, String templateFormType, Integer omsId, UUID requestCode, String requestIP,
      Integer requestUserId, String requestUserName, Integer operatorId, WithdrawTicketStatus status, BigDecimal feeAmt,
      Integer updatedByUser, String updatedByUserName, Long ticketNumber, String withdrawTransactionDetails,
      String rejectReason, String createdTimestamp, String lastUpdateTimestamp, Long createdTimestampTick,
      Long lastUpdateTimestampTick, List<String> attachments, List<String> auditLog) {
    this.assetManagerId = assetManagerId;
    this.accountProviderId = accountProviderId;
    this.accountId = accountId;
    this.accountName = accountName;
    this.assetId = assetId;
    this.assetName = assetName;
    this.amount = amount;
    this.notionalValue = notionalValue;
    this.notionalProductId = notionalProductId;
    this.templateForm = templateForm;
    this.templateFormType = templateFormType;
    this.omsId = omsId;
    this.requestCode = requestCode;
    this.requestIP = requestIP;
    this.requestUserId = requestUserId;
    this.requestUserName = requestUserName;
    this.operatorId = operatorId;
    this.status = status;
    this.feeAmt = feeAmt;
    this.updatedByUser = updatedByUser;
    this.updatedByUserName = updatedByUserName;
    this.ticketNumber = ticketNumber;
    this.withdrawTransactionDetails = withdrawTransactionDetails;
    this.rejectReason = rejectReason;
    this.createdTimestamp = createdTimestamp;
    this.lastUpdateTimestamp = lastUpdateTimestamp;
    this.createdTimestampTick = createdTimestampTick;
    this.lastUpdateTimestampTick = lastUpdateTimestampTick;
    this.attachments = attachments;
    this.auditLog = auditLog;
  }

}
