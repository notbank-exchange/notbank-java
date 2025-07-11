package exchange.notbank.users;

import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import exchange.notbank.CredentialsLoader.UserCredentials;
import exchange.notbank.NotbankClient;
import exchange.notbank.TestHelper;
import exchange.notbank.WebSocketChecker;
import exchange.notbank.users.paramBuilders.GetUserInfoParamBuilder;

public class UserServiceTest {
  private static UserService userService;
  private static UserCredentials credentials;
  private static WebSocketChecker webSocketChecker;
  private static NotbankClient websocketServiceFactory;

  @BeforeAll
  public static void beforeAll() throws InterruptedException, ExecutionException {
    var serviceFactory = TestHelper.newRestClient();
    userService = serviceFactory.getUserService();
    credentials = TestHelper.getUserCredentials();
    webSocketChecker = new WebSocketChecker();
    websocketServiceFactory = TestHelper.newWebsocketClient(webSocketChecker::fromThrowable);
  }

  @AfterAll
  public static void afterAll() throws Exception {
    websocketServiceFactory.getNotbankConnection().close();
  }

  @Test
  public void getUserById() {
    var params = new GetUserInfoParamBuilder().userId(credentials.userId);
    var futureResponse = userService.getUserInfo(params);
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getUserByEmail() {
    var params = new GetUserInfoParamBuilder().email("nino+5@dysopsis.com");
    var futureResponse = userService.getUserInfo(params);
    TestHelper.checkRightOrErrorCode(futureResponse, 104);
  }
}
