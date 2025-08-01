package exchange.notbank.report.responses;

import com.squareup.moshi.Json;

public class DocumentSlice {
  @Json(name = "DescriptorId")
  public final String descriptorId;
  public final String base64Bytes;
  public final String statusCode;
  public final String statusMessage;

  public DocumentSlice(String descriptorId, String base64Bytes, String statusCode, String statusMessage) {
    this.descriptorId = descriptorId;
    this.base64Bytes = base64Bytes;
    this.statusCode = statusCode;
    this.statusMessage = statusMessage;
  }

  @Override
  public String toString() {
    return "DocumentSlice [descriptorId=" + descriptorId + ", base64Bytes=" + base64Bytes + ", statusCode=" + statusCode
        + ", statusMessage=" + statusMessage + "]";
  }
}
