package exchange.notbank.core.rest;

import java.net.http.HttpRequest.Builder;
import java.util.function.Function;

import exchange.notbank.core.AuthenticateUserParamBuilder;

public class SetAuthHeaderFn {
  public static Function<Builder, Builder> get(AuthenticateUserParamBuilder paramBuilder) {
    return builder -> {
      builder.header("APIKey", paramBuilder.apiKey);
      builder.header("Signature", paramBuilder.signature);
      builder.header("UserId", paramBuilder.userId);
      builder.header("Nonce", paramBuilder.nonce);
      return builder;
    };
  }
}
