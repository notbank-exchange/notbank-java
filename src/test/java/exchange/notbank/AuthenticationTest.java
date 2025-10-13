package exchange.notbank;

import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import exchange.notbank.CredentialsLoader.UserCredentials;
import exchange.notbank.core.WebAuthenticateUserParamBuilder;

public class AuthenticationTest {
  private static UserCredentials credentials;

  @BeforeAll
  public static void beforeAll() throws InterruptedException, ExecutionException {
    credentials = TestHelper.getUserCredentials();
  }

  @Test
  public void authenticate() throws InterruptedException, ExecutionException {
    var client = TestHelper.newRestClient();
    var futureResponse = client.authenticate(
        credentials.userId,
        credentials.apiPublicKey,
        credentials.apiSecretKey);
    var response = futureResponse.get();
  }



  @Test
  public void webAuthenticate() throws InterruptedException, ExecutionException {
    var client = TestHelper.newWebsocketClient(System.out::println);
    var futureResponse = client.webAuthenticateUser(new WebAuthenticateUserParamBuilder().sessionToken("session-token"));
    var response = futureResponse.get();
  }
}
