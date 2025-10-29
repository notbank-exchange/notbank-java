package exchange.notbank.system;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import exchange.notbank.NotbankClient;
import exchange.notbank.TestHelper;

public class SystemServiceTest {
  private static NotbankClient client;

  @BeforeAll
  public static void beforeAll() {
    client = NotbankClient.Factory.createRestClient(TestHelper.HOST);
  }

  @Test
  public void ping() {
    var futureResponse = client.getSystemService().ping();
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void healthCheck() {
    var futureResponse = client.getSystemService().healthCheck();
    TestHelper.checkNoError(futureResponse);
  }
}
