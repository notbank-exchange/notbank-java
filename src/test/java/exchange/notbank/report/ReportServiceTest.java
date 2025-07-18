package exchange.notbank.report;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import exchange.notbank.CredentialsLoader.UserCredentials;
import exchange.notbank.TestHelper;
import exchange.notbank.instrument.responses.Instrument;
import exchange.notbank.report.paramBuilders.GenerateActivityReportParamBuilder;

public class ReportServiceTest {
  private static Integer accountId;
  private static UserCredentials credentials;
  private static Instrument anInstrument;
  private static ReportService service;

  @BeforeAll
  public static void beforeAll() throws InterruptedException, ExecutionException {
    var client = TestHelper.newRestClient();
    service = client.getReportService();
    credentials = TestHelper.getUserCredentials();
    accountId = credentials.accountId;
    anInstrument = client.getInstrumentService().getInstrument("BTCUSDT").get();
  }

  @Test
  public void some() {
    var futureResponse = service.generateTradeActivityReport(new GenerateActivityReportParamBuilder(
        List.of(accountId),
        "2025-06-10T16:00:00.000Z",
        "2025-07-15T16:00:00.000Z"));
    TestHelper.checkNoError(futureResponse);
  }
}
