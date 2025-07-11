package exchange.notbank.core;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class NotbankConnectionAuthenticator {
  public static CompletableFuture<Void> authenticate(
      NotbankConnection notbankConnection,
      String userId,
      String userApiKey,
      String userSecretKey) {
    return authenticate(notbankConnection::authenticateUser, userId, userApiKey, userSecretKey);
  }

  public static CompletableFuture<Void> authenticate(
      Function<AuthenticateUserParamBuilder, CompletableFuture<Void>> notbankConnectionAuthenticateUserFn,
      String userId,
      String userApiKey,
      String userSecretKey) {
    var hmac = new HMAC(userApiKey, userSecretKey, userId);
    var rand = new Random();
    var nonce = String.valueOf(Math.abs(rand.nextInt()));
    var signature = hmac.sign(nonce);
    if (signature.isLeft()) {
      return CompletableFuture.failedFuture(signature.getLeft());
    }
    return notbankConnectionAuthenticateUserFn
        .apply(new AuthenticateUserParamBuilder(userApiKey, signature.get(), userId, nonce));
  }
}
