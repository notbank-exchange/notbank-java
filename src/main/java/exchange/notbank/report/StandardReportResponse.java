package exchange.notbank.report;

import java.util.UUID;

import com.squareup.moshi.Json;

public class StandardReportResponse {
  @Json(name = "bAccepted")
  public final Boolean accepted;
  public final String rejectMessage;
  public final UUID requestId;

  public StandardReportResponse(Boolean accepted, String rejectMessage, UUID requestId) {
    this.accepted = accepted;
    this.rejectMessage = rejectMessage;
    this.requestId = requestId;
  }

  @Override
  public String toString() {
    return "StandardReportResponse [accepted=" + accepted + ", rejectMessage=" + rejectMessage + ", requestId="
        + requestId + "]";
  }
}
