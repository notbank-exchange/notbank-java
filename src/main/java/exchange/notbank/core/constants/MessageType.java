package exchange.notbank.core.constants;

public enum MessageType {
  REQUEST(0),
  REPLY(1),
  SUBSCRIBE_TO_EVENT(2),
  EVENT(3),
  UNSUBSCRIBE_FROM_EVENT(4),
  ERROR(5);

  public final Integer value;

  MessageType(Integer value) {
    this.value = value;
  }
}
