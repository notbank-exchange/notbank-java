package exchange.notbank.wallet.responses;

public class Bank {
  public final String id;
  public final String name;
  public final String country;

  public Bank(String id, String name, String country) {
    this.id = id;
    this.name = name;
    this.country = country;
  }

  @Override
  public String toString() {
    return "Bank [id=" + id + ", name=" + name + ", country=" + country + "]";
  }
}
