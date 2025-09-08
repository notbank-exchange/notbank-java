package exchange.notbank.report.responses;

import com.squareup.moshi.Json;

public class DocumentDescriptor {
  @Json(name = "DescriptorId")
  public final String descriptorId;
  @Json(name = "DocName")
  public final String docName;
  @Json(name = "NumSlices")
  public final Integer numSlices;
  public final String statusCode;
  public final String statusMessage;

  public DocumentDescriptor(String descriptorId, String docName, Integer numSlices, String statusCode, String statusMessage) {
    this.descriptorId = descriptorId;
    this.docName = docName;
    this.numSlices = numSlices;
    this.statusCode = statusCode;
    this.statusMessage = statusMessage;
  }

  @Override
  public String toString() {
    return "Document [descriptorId=" + descriptorId + ", docName=" + docName + ", numSlices=" + numSlices
        + ", statusCode=" + statusCode + ", statusMessage=" + statusMessage + "]";
  }
}
