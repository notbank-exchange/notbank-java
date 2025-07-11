package exchange.notbank.wallet.responses;

import java.util.List;

import com.squareup.moshi.Json;

public class CurrencyNetworkTemplates {
  public final String currency;
  public final String network;
  @Json(name = "network_name")
  public final String networkName;
  @Json(name = "network_protocol")
  public final String netwrokProtocol;
  @Json(name = "template")
  public final List<NetworkTemplate> templates;

  public CurrencyNetworkTemplates(String currency, String network, String networkName, String netwrokProtocol,
      List<NetworkTemplate> templates) {
    this.currency = currency;
    this.network = network;
    this.networkName = networkName;
    this.netwrokProtocol = netwrokProtocol;
    this.templates = templates;
  }

  @Override
  public String toString() {
    return "CurrencyNetworkTemplates [currency=" + currency + ", network=" + network + ", networkName=" + networkName
        + ", netwrokProtocol=" + netwrokProtocol + ", templates=" + templates + "]";
  }
}
