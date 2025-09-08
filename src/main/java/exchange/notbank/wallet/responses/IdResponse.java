package exchange.notbank.wallet.responses;

import java.util.UUID;

public class IdResponse {
  public final UUID id;

  public IdResponse(UUID id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "IdResponse [id=" + id + "]";
  }
}