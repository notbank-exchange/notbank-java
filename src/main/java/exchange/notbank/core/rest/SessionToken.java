package exchange.notbank.core.rest;

import java.net.http.HttpRequest.Builder;
import java.util.Optional;
import java.util.function.Function;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.users.responses.AuthenticateUserResponse;

public class SessionToken {

  private Optional<String> sessionToken = Optional.empty();

  public void setSessionToken(AuthenticateUserResponse response) {
    this.sessionToken = Optional.of(response.sessionToken);
  }

  public void removeSessionToken() {
    this.sessionToken = Optional.empty();
  }

  private Builder addSessionTokenHeaderIfPresent(Builder builder) {
    if (sessionToken.isPresent()) {
      return builder.header("aptoken", sessionToken.get());
    }
    return builder;
  }

  public Function<Builder, Builder> withSessionTokenIfPresent(HttpConfiguration httpConfiguration) {
    return httpConfiguration.customizeRequestBuilderFn.andThen(this::addSessionTokenHeaderIfPresent);
  }

}
