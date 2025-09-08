package exchange.notbank.wallet.responses;

import java.util.UUID;

import com.squareup.moshi.Json;

public class WhitelistedAddress {
  public final UUID id;
  public final String currency;
  public final String label;
  public final String network;
  public final String address;
  public final String memo;
  public final Boolean verified;
  @Json(name = "provider_id")
  public final Integer providerId;

  public WhitelistedAddress(UUID id, String currency, String label, String network, String address, String memo,
      Boolean verified, Integer providerId) {
    this.id = id;
    this.currency = currency;
    this.label = label;
    this.network = network;
    this.address = address;
    this.memo = memo;
    this.verified = verified;
    this.providerId = providerId;
  }

  @Override
  public String toString() {
    return "WhitelistedAddress [id=" + id + ", currency=" + currency + ", label=" + label + ", network=" + network
        + ", address=" + address + ", memo=" + memo + ", verified=" + verified + ", providerId=" + providerId + "]";
  }
}
