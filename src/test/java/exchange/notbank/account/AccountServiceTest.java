package exchange.notbank.account;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import exchange.notbank.CredentialsLoader.UserCredentials;
import exchange.notbank.NotbankClient;
import exchange.notbank.TestHelper;
import exchange.notbank.account.paramBuilders.GetAccountPositionsParamBuilder;
import exchange.notbank.account.paramBuilders.GetAccountTransactionsParamBuilder;
import exchange.notbank.users.constants.ReferenceType;

public class AccountServiceTest {

  private static NotbankClient client;
  private static UserCredentials credentials;

  @BeforeAll
  public static void beforeAll() throws InterruptedException, ExecutionException {
    client = TestHelper.newRestClient();
    credentials = TestHelper.getUserCredentials();
    client.authenticate(
        credentials.userId,
        credentials.apiPublicKey,
        credentials.apiSecretKey).get();
  }

  @Test
  public void getAccountPositions() throws InterruptedException, ExecutionException {
    var futureResponse = client.getAccountService()
        .getAccountPositions(new GetAccountPositionsParamBuilder(credentials.accountId));
    var response = futureResponse.get();
    assertTrue(response.size() > 0);
  }

  @Test
  public void getAccountTransactions() throws InterruptedException, ExecutionException {
    var futureResponse = client.getAccountService().getAccountTransactions(new GetAccountTransactionsParamBuilder()
        .accountId(credentials.accountId)
        .transactionReferenceTypes(List.of(ReferenceType.DEPOSIT, ReferenceType.WITHDRAW))
        .productId(3));
    TestHelper.checkNoError(futureResponse);
  }
}
