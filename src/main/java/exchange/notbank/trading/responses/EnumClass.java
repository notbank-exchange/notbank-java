package exchange.notbank.trading.responses;

import java.util.List;

import com.squareup.moshi.Json;

public class EnumClass {
  @Json(name = "Class")
  public final String parentClass;
  @Json(name = "Property")
  public final String property;
  @Json(name = "Enums")
  public final List<EnumValue> enums;

  public EnumClass(String enumClass, String property, List<EnumValue> enums) {
    this.parentClass = enumClass;
    this.property = property;
    this.enums = enums;
  }

  @Override
  public String toString() {
    return "EnumClass [parentClass=" + parentClass + ", property=" + property + ", enums=" + enums + "]";
  }
}
