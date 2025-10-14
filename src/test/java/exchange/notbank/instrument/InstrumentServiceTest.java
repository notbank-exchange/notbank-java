package exchange.notbank.instrument;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import exchange.notbank.NotbankClient;
import exchange.notbank.TestHelper;
import exchange.notbank.core.CompletableFutureAdapter;
import exchange.notbank.instrument.constants.InstrumentState;
import exchange.notbank.instrument.paramBuilders.GetInstrumentParamBuilder;
import exchange.notbank.instrument.paramBuilders.GetInstrumentVerificationLevelConfigParamBuilder;
import exchange.notbank.instrument.paramBuilders.GetInstrumentsParamBuilder;

public class InstrumentServiceTest {
  private static NotbankClient client;

  @BeforeAll
  public static void beforeAll() {
    client = TestHelper.newRestClient();
  }

  @Test
  public void getInstruments() {
    var futureResponse = client.getInstrumentService().getInstruments(new GetInstrumentsParamBuilder().instrumentState(InstrumentState.BOTH));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getInstrument() {
    var futureResponse = client.getInstrumentService().getInstrument(new GetInstrumentParamBuilder(1));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getInstrumentVerificationLevelConfig() throws InterruptedException, ExecutionException {
    var credentials = TestHelper.getUserCredentials();
    client.authenticate(credentials.userId, credentials.apiPublicKey, credentials.apiSecretKey).get();
    var futureResponse = client.getInstrumentService().getInstrumentVerificationLevelConfig(new GetInstrumentVerificationLevelConfigParamBuilder(credentials.accountId));
    TestHelper.checkNoError(futureResponse);
  }

  

  @Test
  public void getInstrumentId() {
    var btcUsdtInstrumentId = 154;
    var btcUsdtSymbol = "BTCUSDT";
    var firstCallResponse = client.getInstrumentService().getInstrumentId(btcUsdtSymbol);
    checkInstrumentIdResponse(btcUsdtInstrumentId, firstCallResponse);
    var secondCallResponse = client.getInstrumentService().getInstrumentId(btcUsdtSymbol);
    checkInstrumentIdResponse(btcUsdtInstrumentId, secondCallResponse);
  }

  @Test
  public void getInstrumentIdFailsOnInvalidSymbol() {
    var invalidSymbol = "ABCABC";
    var futureResponse = client.getInstrumentService().getInstrumentId(invalidSymbol);
    var response = CompletableFutureAdapter.getToEither(futureResponse);
    assertTrue(response.isLeft());
  }

  private void checkInstrumentIdResponse(int btcUsdtInstrumentId, CompletableFuture<Integer> futureResponse) {
    var response = CompletableFutureAdapter.getToEither(futureResponse);
    TestHelper.assertIsRight(response);
    assertEquals(btcUsdtInstrumentId, response.get());
  }
}
