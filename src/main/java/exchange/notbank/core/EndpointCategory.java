package exchange.notbank.core;

public enum EndpointCategory {
  AP("/ap/"),
  NB("/api/nb/");

  private final String value;

  EndpointCategory(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
