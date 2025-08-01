package exchange.notbank.wallet.responses;

import com.squareup.moshi.Json;

public class CbuOwner {
  @Json(name = "person_type")
  public final String personType;
  public final String cuit;
  public final String name;

  public CbuOwner(String personType, String cuit, String name) {
    this.personType = personType;
    this.cuit = cuit;
    this.name = name;
  }
}
