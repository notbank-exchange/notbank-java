package exchange.notbank.core.responses;

public class JsonResponse {
  public final Integer code;
  public final String json;

  public JsonResponse(Integer code, String json) {
    this.code = code;
    this.json = json;
  }

  @Override
  public String toString() {
    return "JsonResponse [code=" + code + ", json=" + json + "]";
  }
}
