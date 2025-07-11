package exchange.notbank.system.responses;

import com.squareup.moshi.Json;

public class Pong {
  @Json(name = "msg")
  public final String message;

  public Pong(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return "Pong [message=" + message + "]";
  }
}
