package exchange.notbank.core.websocket;

import java.util.Optional;

import com.squareup.moshi.Json;

public class AccountEventPayload {
  private String payload;
  @Json(name = "AccountId")
  private final Integer accountId;
  @Json(name = "Account")
  private final Integer accountId2;

  public AccountEventPayload(String payload, Integer accountId, Integer accountId2) {
    this.payload = payload;
    this.accountId = accountId;
    this.accountId2 = accountId2;
  }

  public AccountEventPayload setPaylaod(String payload) {
    this.payload = Optional.ofNullable(payload).orElse("{}");
    return this;
  }

  public String getPayload() {
    return payload;
  }

  public Integer getAccountId() {
    return Optional.ofNullable(accountId).orElse(Optional.ofNullable(accountId2).orElse(0));
  }
}
