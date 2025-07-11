package exchange.notbank.wallet.responses;

public class IdResponse {
  public final String id;

  public IdResponse(String id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "IdResponse [id=" + id + "]";
  }
}