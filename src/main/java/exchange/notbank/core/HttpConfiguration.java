package exchange.notbank.core;

import java.net.http.HttpRequest.Builder;
import java.util.function.Function;

public class HttpConfiguration {
  public final Function<Builder, Builder> customizeRequestBuilderFn;

  public HttpConfiguration() {
    this.customizeRequestBuilderFn = Function.identity();
  }

  public HttpConfiguration(Function<Builder, Builder> customizeRequestBuilderFn) {
    this.customizeRequestBuilderFn = customizeRequestBuilderFn;
  }

  public static HttpConfiguration empty() {
    return new HttpConfiguration(Function.identity());
  }
}
