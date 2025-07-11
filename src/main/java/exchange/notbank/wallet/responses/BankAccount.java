package exchange.notbank.wallet.responses;

import com.squareup.moshi.Json;

public class BankAccount {
  public final String id;
  public final String country;
  public final Bank bank;
  public final String number;
  public final String kind;
  public final String currency;
  /** may be null*/
  public final String agency;
  /** may be null*/
  public final String dv;
  /** may be null*/
  public final String province;
  /** may be null*/
  @Json(name = "pix_type")
  public final String pixType;

  public BankAccount(String id, String country, Bank bank, String number, String kind, String currency, String agency,
      String dv, String province, String pixType) {
    this.id = id;
    this.country = country;
    this.bank = bank;
    this.number = number;
    this.kind = kind;
    this.currency = currency;
    this.agency = agency;
    this.dv = dv;
    this.province = province;
    this.pixType = pixType;
  }

  @Override
  public String toString() {
    return "BankAccount [id=" + id + ", country=" + country + ", bank=" + bank + ", number=" + number + ", kind=" + kind
        + ", currency=" + currency + ", agency=" + agency + ", dv=" + dv + ", province=" + province + ", pixType="
        + pixType + "]";
  }
}
