package exchange.notbank.core.websocket.restarter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

public class WebsocketRestarterTest {

  @Test
  public void websocketRestarter() throws InterruptedException, IOException {
    var testResultsFile = new java.io.File("test-results-4.log");
    if (!testResultsFile.exists()) {
      testResultsFile.createNewFile();
    }
    var fileWriter = new java.io.FileWriter(testResultsFile, true);
    var logWriter = new java.io.PrintWriter(fileWriter, true);
    TimeUnit.HOURS.sleep(20);
    logWriter.close();
  }

}
