package exchange.notbank.core.responses;

public class DataResponse<T> {
  public final T data;

  public DataResponse(T data) {
    this.data = data;
  }
}
