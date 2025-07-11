package exchange.notbank.core;

import com.squareup.moshi.Json;

public class WebAuthenticateResponse {
  @Json(name = "Authenticated")
  public final Boolean authenticated;
  @Json(name = "SessionToken")
  public final String sessionToken;
  @Json(name = "UserId")
  public final Integer userId;
  @Json(name = "Requires2FA")
  public final Boolean requires2fa;
  @Json(name = "AuthType")
  public final String authType;
  @Json(name = "errormsg")
  public final String errormsg;
  @Json(name = "AddtlInfo")
  public final String addtlInfo;

  public WebAuthenticateResponse(Boolean authenticated, String sessionToken, Integer userId, Boolean requires2fa,
      String authType, String errormsg, String addtlInfo) {
    this.authenticated = authenticated;
    this.sessionToken = sessionToken;
    this.userId = userId;
    this.requires2fa = requires2fa;
    this.authType = authType;
    this.errormsg = errormsg;
    this.addtlInfo = addtlInfo;
  }
}
