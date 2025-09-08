package exchange.notbank.core.websocket.restarter;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import exchange.notbank.NotbankClientFactory;
import exchange.notbank.TestHelper;
import exchange.notbank.subscription.paramBuilders.SubscribeLevel1ParamBuilder;

public class WebsocketRestarterTest {

  @Test
  public void websocketRestarter() throws InterruptedException, IOException, ExecutionException {
    var testResultsFile = new java.io.File("test-results-4.log");
    if (!testResultsFile.exists()) {
      testResultsFile.createNewFile();
    }
    var fileWriter = new java.io.FileWriter(testResultsFile, true);
    var logWriter = new java.io.PrintWriter(fileWriter, true);

    var client = NotbankClientFactory.createRestartingWebsocketClient(
        TestHelper.HOST,
        connection -> CompletableFuture.completedFuture(connection),
        e -> logWriter.println("error: " + e.getMessage()),
        messageIn -> logWriter.println("messageIn: "),
        messageOut -> logWriter.println("messageOut: ")).get();
    var credentials = TestHelper.getUserCredentials();
    client.authenticate(
        credentials.userId,
        credentials.apiPublicKey,
        credentials.apiSecretKey).get();
    client.getSubscriptionService().subscribeLevel1(
        new SubscribeLevel1ParamBuilder(66),
        snapshot -> logWriter.println("snapshot: " + snapshot),
        update -> logWriter.println("update: " + update));
    client.getSubscriptionService().subscribeLevel1(
        new SubscribeLevel1ParamBuilder(156),
        snapshot -> logWriter.println("snapshot: " + snapshot),
        update -> logWriter.println("update: " + update));
    TimeUnit.HOURS.sleep(20);
    logWriter.close();
  }
}
