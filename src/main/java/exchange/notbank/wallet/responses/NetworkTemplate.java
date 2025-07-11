package exchange.notbank.wallet.responses;

public class NetworkTemplate {
  public final String name;
  public final String type;
  public final Boolean required;
  public final Integer maxLength;
  public final Integer minLength;

  public NetworkTemplate(String name, String type, Boolean required, Integer maxLength, Integer minLength) {
    this.name = name;
    this.type = type;
    this.required = required;
    this.maxLength = maxLength;
    this.minLength = minLength;
  }

  @Override
  public String toString() {
    return "NetworkTemplate [name=" + name + ", type=" + type + ", required=" + required + ", maxLength=" + maxLength
        + ", minLength=" + minLength + "]";
  }

}
