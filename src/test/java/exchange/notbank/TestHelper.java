package exchange.notbank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import exchange.notbank.CredentialsLoader.UserCredentials;
import exchange.notbank.core.CompletableFutureAdapter;

import io.vavr.control.Either;

public class TestHelper {
  public static final String HOST = "stgapi.notbank.exchange";
  private static Optional<UserCredentials> userCredentials = Optional.empty();

  public static UserCredentials getUserCredentials() {
    if (userCredentials.isEmpty()) {
      var credentials = CredentialsLoader.load("key.json");
      userCredentials = Optional.ofNullable(credentials);
    }
    return userCredentials.get();
  }

  public static <T> void checkNoError(CompletableFuture<T> futureResponse) {
    var response = CompletableFutureAdapter.getToEither(futureResponse);
    // System.out.println(response);
    assertIsRight(response);
  }

  public static <T> void checkRightOrErrorCode(CompletableFuture<T> futureResponse, Integer code) {
    var response = CompletableFutureAdapter.getToEither(futureResponse);
    if (response.isLeft()) {
      assertEquals(response.getLeft().code.get(), code, () -> response.getLeft().toString());
    }
  }

  public static <T> void assertIsRight(Either<? extends Object, T> either) {
    assertTrue(either.isRight(), () -> either.getLeft().toString());
  }

  public static NotbankClient newWebsocketClient(Consumer<Throwable> onFailure)
      throws InterruptedException, ExecutionException {
    var serviceFactory = NotbankClient.Factory.createWebsocketClient(
        HOST,
        CompletableFuture::completedFuture,
        onFailure,
        messageIn -> {
          System.out.println("***message in***");
          System.out.println(messageIn);
        },
        messageOut -> {
          System.out.println("***message out***");
          System.out.println(messageOut);
        }).get();
    return serviceFactory;
  }

  public static NotbankClient newRestClient() {
    var serviceFactory = NotbankClient.Factory.createRestClient(HOST, CompletableFuture::completedFuture,
        (request, jsonBody) -> {
          System.out.println("*connection request*");
          System.out.println("uri: " + request.uri());
          System.out.println("body: " + jsonBody);
        },
        response -> {
          System.out.println("*connection response*");
          System.out.println("body: " + response.body());
        });
    authenticateFactory(serviceFactory);
    return serviceFactory;
  }

  private static void authenticateFactory(NotbankClient serviceFactory) {
    var userCredentials = getUserCredentials();
    var future = serviceFactory.authenticate(
        userCredentials.userId.toString(),
        userCredentials.apiPublicKey,
        userCredentials.apiSecretKey);
    var authResult = CompletableFutureAdapter.getToEither(future);
    if (authResult.isLeft()) {
      throw new RuntimeException("unable to authenticate. " + authResult.getLeft());
    }
    return;
  }
}
