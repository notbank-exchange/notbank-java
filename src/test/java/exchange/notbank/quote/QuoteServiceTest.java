package exchange.notbank.quote;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import exchange.notbank.CredentialsLoader.UserCredentials;
import exchange.notbank.NotbankClient;
import exchange.notbank.TestHelper;
import exchange.notbank.quote.constants.QuoteMode;
import exchange.notbank.quote.constants.QuoteOperation;
import exchange.notbank.quote.paramBuilders.CreateDirectQuoteParamBuilder;
import exchange.notbank.quote.paramBuilders.CreateInverseQuoteParamBuilder;
import exchange.notbank.quote.paramBuilders.ExecuteQuoteParamBuilder;
import exchange.notbank.quote.paramBuilders.GetQuoteParamBuilder;
import exchange.notbank.quote.paramBuilders.GetQuotesParamBuilders;

public class QuoteServiceTest {
  private static NotbankClient client;
  private static UserCredentials credentials;

  @BeforeAll
  public static void beforeAll() throws InterruptedException, ExecutionException {
    client = NotbankClient.Factory.createRestClient(TestHelper.HOST);
    credentials = TestHelper.getUserCredentials();
    client.authenticate(credentials.userId, credentials.apiPublicKey, credentials.apiSecretKey).get();
  }

  @Test
  public void getQuotes() {
    var futureResponse = client.getQuoteService().getQuotes(new GetQuotesParamBuilders(QuoteMode.DIRECT));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void createDirectQuote() {
    var futureResponse = client.getQuoteService().createDirectQuote(new CreateDirectQuoteParamBuilder(
        credentials.accountId,
        "BTC",
        new BigDecimal("1000"),
        "USDT",
        QuoteOperation.BUY));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void createInverseQuote() {
    var futureResponse = client.getQuoteService().createInverseQuote(new CreateInverseQuoteParamBuilder(
        credentials.accountId,
        "BTC",
        new BigDecimal("1000"),
        "USDT"));
    TestHelper.checkNoError(futureResponse);
  }


    @Test
  public void getQuote() {
    var futureResponse = client.getQuoteService().getQuote(new GetQuoteParamBuilder(UUID.fromString("c1c56ca3-75c3-4bb6-97e3-702448382cd3")));
    TestHelper.checkNoError(futureResponse);
  }


    @Test
  public void executeQuote() {
    var futureResponse = client.getQuoteService().executeQuote(new ExecuteQuoteParamBuilder(UUID.fromString("c1c56ca3-75c3-4bb6-97e3-702448382cd3")));
    TestHelper.checkNoError(futureResponse);
  }

}
