package exchange.notbank.wallet.responses;

public class WhitelistedAddress {
  public final String id;
  public final String currency;
  public final String label;
  public final String network;
  public final String address;
  public final String memo;
  public final Boolean verified;

  public WhitelistedAddress(String id, String currency, String label, String network, String address, String memo,
      Boolean verified) {
    this.id = id;
    this.currency = currency;
    this.label = label;
    this.network = network;
    this.address = address;
    this.memo = memo;
    this.verified = verified;
  }

  @Override
  public String toString() {
    return "WhitelistedAddress [id=" + id + ", currency=" + currency + ", label=" + label + ", network=" + network
        + ", address=" + address + ", memo=" + memo + ", verified=" + verified + "]";
  }
}
