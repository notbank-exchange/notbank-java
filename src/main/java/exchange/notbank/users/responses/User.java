package exchange.notbank.users.responses;

import com.squareup.moshi.Json;

public class User {
  @Json(name = "UserId")
  public final Integer userId;
  @Json(name = "UserName")
  public final String userName;
  @Json(name = "Email")
  public final String email;
  @Json(name = "EmailVerified")
  public final Boolean emailVerified;
  @Json(name = "AccountId")
  public final Integer accountId;
  @Json(name = "OMSId")
  public final Integer oMSId;
  @Json(name = "Use2FA")
  public final Boolean use2FA;

  public User(Integer userId, String userName, String email, Boolean emailVerified, Integer accountId, Integer oMSId,
      Boolean use2fa) {
    this.userId = userId;
    this.userName = userName;
    this.email = email;
    this.emailVerified = emailVerified;
    this.accountId = accountId;
    this.oMSId = oMSId;
    this.use2FA = use2fa;
  }

  @Override
  public String toString() {
    return "User [userId=" + userId + ", userName=" + userName + ", email=" + email + ", emailVerified=" + emailVerified
        + ", accountId=" + accountId + ", oMSId=" + oMSId + ", use2FA=" + use2FA + "]";
  }
}
