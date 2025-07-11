package exchange.notbank.core.adapters;

import exchange.notbank.core.constants.MessageType;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

public class MessageTypeAdapter {
  @ToJson
  int toJson(MessageType value) {
    return value.value;
  }

  @FromJson
  MessageType fromJson(int value) {
    return MessageType.values()[value];
  }
}
