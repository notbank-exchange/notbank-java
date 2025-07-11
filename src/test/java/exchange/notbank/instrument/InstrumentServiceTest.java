package exchange.notbank.instrument;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import exchange.notbank.NotbankClient;
import exchange.notbank.TestHelper;
import exchange.notbank.core.CompletableFutureAdapter;
import exchange.notbank.instrument.paramBuilders.GetInstrumentParamBuilder;
import exchange.notbank.instrument.paramBuilders.GetInstrumentsParamBuilder;

public class InstrumentServiceTest {
  private static InstrumentService service;

  @BeforeAll
  public static void beforeAll() {
    service = NotbankClient.Factory.createRestClient(TestHelper.HOST).getInstrumentService();
  }

  @Test
  public void getInstruments() {
    var futureResponse = service.getInstruments(new GetInstrumentsParamBuilder());
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getInstrument() {
    var futureResponse = service.getInstrument(new GetInstrumentParamBuilder(1));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getInstrumentId() {
    var btcUsdtInstrumentId = 154;
    var btcUsdtSymbol = "BTCUSDT";
    var firstCallResponse = service.getInstrumentId(btcUsdtSymbol);
    checkInstrumentIdResponse(btcUsdtInstrumentId, firstCallResponse);
    var secondCallResponse = service.getInstrumentId(btcUsdtSymbol);
    checkInstrumentIdResponse(btcUsdtInstrumentId, secondCallResponse);
  }

  @Test
  public void getInstrumentIdFailsOnInvalidSymbol() {
    var invalidSymbol = "ABCABC";
    var futureResponse = service.getInstrumentId(invalidSymbol);
    var response = CompletableFutureAdapter.getToEither(futureResponse);
    assertTrue(response.isLeft());
  }

  private void checkInstrumentIdResponse(int btcUsdtInstrumentId, CompletableFuture<Integer> futureResponse) {
    var response = CompletableFutureAdapter.getToEither(futureResponse);
    TestHelper.assertIsRight(response);
    assertEquals(btcUsdtInstrumentId, response.get());
  }
}
