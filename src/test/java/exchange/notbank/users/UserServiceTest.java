package exchange.notbank.users;

import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import exchange.notbank.CredentialsLoader.UserCredentials;
import exchange.notbank.NotbankClient;
import exchange.notbank.TestHelper;
import exchange.notbank.WebSocketChecker;
import exchange.notbank.users.paramBuilders.GetUserAccountsParamBuilder;
import exchange.notbank.users.paramBuilders.GetUserDevicesParamBuilder;

public class UserServiceTest {
  private static UserCredentials credentials;
  private static WebSocketChecker webSocketChecker;
  private static NotbankClient websocketServiceFactory;
  private static NotbankClient client;

  @BeforeAll
  public static void beforeAll() throws InterruptedException, ExecutionException {
    client = TestHelper.newRestClient();
    credentials = TestHelper.getUserCredentials();
    client.authenticate(credentials.userId, credentials.apiPublicKey, credentials.apiSecretKey).get();
    webSocketChecker = new WebSocketChecker();
    websocketServiceFactory = TestHelper.newWebsocketClient(webSocketChecker::fromThrowable);
  }

  @AfterAll
  public static void afterAll() throws Exception {
    websocketServiceFactory.getNotbankConnection().close();
  }

  @Test
  public void getUserAccounts() {
    var futureResponse = client.getUserService().getUserAccounts(new GetUserAccountsParamBuilder(credentials.userId));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getUserDevices() {
    var futureResponse = client.getUserService().getUserDevices(new GetUserDevicesParamBuilder(credentials.userId));
    TestHelper.checkNoError(futureResponse);
  }
}
