package exchange.notbank.core;

import java.util.concurrent.CompletableFuture;

import exchange.notbank.NotbankClient;

import io.vavr.control.Either;

public class NotbankUserAuthenticator {
  public static Either<String, Void> authenticate(
      NotbankRequester requester,
      NotbankClient notbankServiceFactory,
      UserCredentials userCredentials) {
    return requester.run(() -> authenticate(notbankServiceFactory, userCredentials));
  }

  public static CompletableFuture<Void> authenticate(
      NotbankClient notbankServiceFactory,
      UserCredentials userCredentials) {
    return notbankServiceFactory.authenticate(
        userCredentials.userId,
        userCredentials.apiPublicKey,
        userCredentials.apiSecretKey);
  }

  public static Either<String, Void> authenticate(
      NotbankRequester requester,
      NotbankConnection notbankConnection,
      UserCredentials userCredentials) {
    return requester.run(() -> authenticate(notbankConnection, userCredentials));
  }

  public static CompletableFuture<Void> authenticate(
      NotbankConnection notbankConnection,
      UserCredentials userCredentials) {
    return NotbankConnectionAuthenticator.authenticate(
        notbankConnection,
        userCredentials.userId.toString(),
        userCredentials.apiPublicKey,
        userCredentials.apiSecretKey);
  }
}
