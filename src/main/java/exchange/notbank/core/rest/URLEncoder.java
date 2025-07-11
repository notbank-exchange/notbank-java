package exchange.notbank.core.rest;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class URLEncoder {

  public static String encode(Map<String, String> params) {
    return params.entrySet().stream()
        .map(URLEncoder::urlEncodedUTF8)
        .reduce((a, b) -> a + "&" + b)
        .orElse("");
  }

  private static String urlEncodedUTF8(Map.Entry<String, String> entry) {
    return urlEncodedUTF8(entry.getKey()) + "=" + urlEncodedUTF8(entry.getValue());
  }

  private static String urlEncodedUTF8(String str) {
    return java.net.URLEncoder.encode(str, StandardCharsets.UTF_8);
  }

  public static String asUrl(String endpoint, Map<String, String> params) {
    var urlEncodedParams = encode(params);
    return (urlEncodedParams.equals(""))
        ? endpoint
        : endpoint + "?" + urlEncodedParams;
  }
}
