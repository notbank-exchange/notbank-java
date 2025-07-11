package exchange.notbank.users.responses;

import com.squareup.moshi.Json;
import java.time.LocalDateTime;

public class UserInfo {
  @Json(name = "UserId")
  public final Integer userId;
  @Json(name = "UserName")
  public final String userName;
  @Json(name = "Email")
  public final String email;
  @Json(name = "PasswordHash")
  public final String passwordHash;
  @Json(name = "PendingEmailCode")
  public final String pendingEmailCode;
  @Json(name = "EmailVerified")
  public final Boolean emailVerified;
  @Json(name = "AccountId")
  public final Integer accountId;
  @Json(name = "DateTimeCreated")
  public final LocalDateTime dateTimeCreated;
  @Json(name = "AffiliateId")
  public final Integer affiliateId;
  @Json(name = "RefererId")
  public final Integer refererId;
  @Json(name = "OMSId")
  public final Integer omsId;
  @Json(name = "Use2FA")
  public final Boolean use2FA;
  @Json(name = "Salt")
  public final String salt;
  @Json(name = "PendingCodeTime")
  public final LocalDateTime pendingCodeTime;
  @Json(name = "Locked")
  public final Boolean locked;
  @Json(name = "LockedTime")
  public final LocalDateTime lockedTime;
  @Json(name = "NumberOfFailedAttempt")
  public final Integer numberOfFailedAttempt;
  @Json(name = "MarginBorrowerEnabled")
  public final Boolean marginBorrowerEnabled;
  @Json(name = "MarginAcquisitionHalt")
  public final Boolean marginAcquisitionHalt;
  @Json(name = "OperatorId")
  public final Integer operatorId;

  public UserInfo(Integer userId, String userName, String email, String passwordHash, String pendingEmailCode,
      Boolean emailVerified, Integer accountId, LocalDateTime dateTimeCreated, Integer affiliateId, Integer refererId,
      Integer omsId,
      Boolean use2fa, String salt, LocalDateTime pendingCodeTime, Boolean locked, LocalDateTime lockedTime,
      Integer numberOfFailedAttempt, Boolean marginBorrowerEnabled, Boolean marginAcquisitionHalt, Integer operatorId) {
    this.userId = userId;
    this.userName = userName;
    this.email = email;
    this.passwordHash = passwordHash;
    this.pendingEmailCode = pendingEmailCode;
    this.emailVerified = emailVerified;
    this.accountId = accountId;
    this.dateTimeCreated = dateTimeCreated;
    this.affiliateId = affiliateId;
    this.refererId = refererId;
    this.omsId = omsId;
    this.use2FA = use2fa;
    this.salt = salt;
    this.pendingCodeTime = pendingCodeTime;
    this.locked = locked;
    this.lockedTime = lockedTime;
    this.numberOfFailedAttempt = numberOfFailedAttempt;
    this.marginBorrowerEnabled = marginBorrowerEnabled;
    this.marginAcquisitionHalt = marginAcquisitionHalt;
    this.operatorId = operatorId;
  }
}
