package exchange.notbank.system.responses;

import com.squareup.moshi.Json;

public class HealthCheck {
  @Json(name = "detail")
  public final String detail;

  public HealthCheck(String detail) {
    this.detail = detail;
  }

  @Override
  public String toString() {
    return "HealthCheck [detail="
        + detail + "]";
  }
}
