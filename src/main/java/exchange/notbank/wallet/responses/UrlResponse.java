package exchange.notbank.wallet.responses;

public class UrlResponse {
  public final String url;

  public UrlResponse(String url) {
    this.url = url;
  }

  @Override
  public String toString() {
    return "UrlResponse [url=" + url + "]";
  }
}