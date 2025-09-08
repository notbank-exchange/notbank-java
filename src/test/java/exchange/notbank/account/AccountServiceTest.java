package exchange.notbank.account;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;

import exchange.notbank.NotbankClient;
import exchange.notbank.TestHelper;
import exchange.notbank.account.paramBuilders.GetAccountPositionsParamBuilder;

public class AccountServiceTest {
  @Test
  public void getAccountPositions() throws InterruptedException, ExecutionException {
    var credentials = TestHelper.getUserCredentials();
    var accountId = credentials.accountId;

    var client = NotbankClient.Factory.createRestClient(TestHelper.HOST);

    client.authenticate(
        credentials.userId,
        credentials.apiPublicKey,
        credentials.apiSecretKey).get();

    var positions = client.getAccountService().getAccountPositions(new GetAccountPositionsParamBuilder(accountId))
        .get();
    positions.stream().filter(p->p.productSymbol.equals("CLP")).forEach(System.out::println);
    assertTrue(positions.size() > 0);
  }
}
