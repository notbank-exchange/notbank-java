package exchange.notbank.system;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import exchange.notbank.NotbankClient;
import exchange.notbank.TestHelper;

public class SystemServiceTest {
  private static SystemService systemService;

  @BeforeAll
  public static void beforeAll() {
    systemService = NotbankClient.Factory.createRestClient(TestHelper.HOST).getSystemService();
  }

  @Test
  public void ping() {
    var futureResponse = systemService.ping();
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void healthCheck() {
    var futureResponse = systemService.healthCheck();
    TestHelper.checkNoError(futureResponse);
  }
}
