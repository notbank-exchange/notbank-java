package exchange.notbank.wallet.responses;

import java.util.UUID;

import com.squareup.moshi.Json;

public class WithdrawalIdResponse {
  @Json(name = "withdrawal_id")
  public final UUID withdrawalId;

  public WithdrawalIdResponse(UUID withdrawalId) {
    this.withdrawalId = withdrawalId;
  }
}