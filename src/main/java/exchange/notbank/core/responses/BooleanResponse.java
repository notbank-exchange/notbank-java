package exchange.notbank.core.responses;

import com.squareup.moshi.Json;

public class BooleanResponse {
  @Json(name = "Subscribed")
  public final Boolean subscribed;

  public BooleanResponse(Boolean subscribed) {
    this.subscribed = subscribed;
  }
}
