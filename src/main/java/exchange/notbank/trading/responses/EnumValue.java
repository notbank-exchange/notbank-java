package exchange.notbank.trading.responses;

import com.squareup.moshi.Json;

public class EnumValue {
  @Override
  public String toString() {
    return "EnumValue [name=" + name + ", description=" + description + ", number=" + number + "]";
  }

  @Json(name = "Name")
  public final String name;
  @Json(name = "Description")
  public final String description;
  @Json(name = "Number")
  public final Integer number;

  public EnumValue(String name, String description, Integer number) {
    this.name = name;
    this.description = description;
    this.number = number;
  }
}
