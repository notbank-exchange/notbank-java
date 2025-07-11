package exchange.notbank.core.responses;

import exchange.notbank.core.constants.MessageType;
import com.squareup.moshi.Json;

public class MessageFrame {
  @Json(name = "m")
  public final MessageType messageType;
  @Json(name = "i")
  public final Long sequenceNumber;
  @Json(name = "n")
  public final String functionName;
  @Json(name = "o")
  public final String payload;

  public MessageFrame(MessageType messageType, Long sequenceNumber, String functionName, String payload) {
    this.messageType = messageType;
    this.sequenceNumber = sequenceNumber;
    this.functionName = functionName;
    this.payload = payload;
  }

  @Override
  public String toString() {
    return "MessageFrame [messageType=" + messageType + ", sequenceNumber=" + sequenceNumber + ", functionName="
        + functionName + ", payload=" + payload + "]";
  }
}
