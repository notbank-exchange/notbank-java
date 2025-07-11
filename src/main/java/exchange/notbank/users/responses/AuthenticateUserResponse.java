package exchange.notbank.users.responses;

import com.squareup.moshi.Json;

public class AuthenticateUserResponse {
  @Json(name = "Authenticated")
  public final Boolean authenticated;
  @Json(name = "SessionToken")
  public final String sessionToken;
  @Json(name = "User")
  public final User user;
  @Json(name = "Locked")
  public final Boolean locked;
  @Json(name = "Requires2FA")
  public final Boolean requires2FA;
  @Json(name = "EnforceEnable2FA")
  public final Boolean enforceEnable2FA;
  @Json(name = "TwoFAType")
  public final Integer twoFAType;
  @Json(name = "TwoFAToken")
  public final Integer twoFAToken;
  @Json(name = "errormsg")
  public final String errorMessage;

  public AuthenticateUserResponse(Boolean authenticated, String sessionToken, User user, Boolean locked,
      Boolean requires2fa, Boolean enforceEnable2FA, Integer twoFAType, Integer twoFAToken, String errorMessage) {
    this.authenticated = authenticated;
    this.sessionToken = sessionToken;
    this.user = user;
    this.locked = locked;
    requires2FA = requires2fa;
    this.enforceEnable2FA = enforceEnable2FA;
    this.twoFAType = twoFAType;
    this.twoFAToken = twoFAToken;
    this.errorMessage = errorMessage;
  }

  @Override
  public String toString() {
    return "AuthenticateUserResponse [authenticated=" + authenticated + ", sessionToken=" + sessionToken + ", user="
        + user + ", locked=" + locked + ", requires2FA=" + requires2FA + ", enforceEnable2FA=" + enforceEnable2FA
        + ", twoFAType=" + twoFAType + ", twoFAToken=" + twoFAToken + ", errorMessage=" + errorMessage + "]";
  }

}
