package exchange.notbank.core.responses;

public class NbResponse {
  public final String status;
  public final String message;
  public final String detail;

  public NbResponse(String status, String message, String detail) {
    this.status = status;
    this.message = message;
    this.detail = detail;
  }
}
