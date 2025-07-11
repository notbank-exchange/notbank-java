package exchange.notbank.account;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import exchange.notbank.CredentialsLoader.UserCredentials;
import exchange.notbank.TestHelper;
import exchange.notbank.account.paramBuilders.GetAccountInfoParamBuilder;
import exchange.notbank.account.paramBuilders.GetAccountInstrumentStatisticsParamBuilder;
import exchange.notbank.account.paramBuilders.GetAccountPositionsParamBuilder;
import exchange.notbank.account.paramBuilders.GetAccountTransactionsParamBuilder;

public class AccountServiceAdapterTest {
  private static AccountService accountService;
  private static Integer accountId;
  private static UserCredentials credentials;

  @BeforeAll
  public static void beforeAll() {
    var serviceFactory = TestHelper.newRestClient();
    accountService = serviceFactory.getAccountService();
    accountId = TestHelper.getUserCredentials().accountId;
    credentials = TestHelper.getUserCredentials();
  }

  @Test
  public void getBalances() {
    var futureResponse = accountService
        .getAccountPositions(new GetAccountPositionsParamBuilder(accountId).includePending(true));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getAccountInstrumentStatistics() {
    var params = new GetAccountInstrumentStatisticsParamBuilder().omsId(1).accountId(credentials.accountId);
    var futureResponse = accountService.getAccountInstrumentStatistics(params);
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getAccountInfo() {
    var params = new GetAccountInfoParamBuilder().accountId(credentials.accountId);
    var futureResponse = accountService.getAccountInfo(params);
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getAccountTrades() {
    var params = new GetAccountTransactionsParamBuilder();
    var futureResponse = accountService.getAccountTransactions(params);
    TestHelper.checkNoError(futureResponse);
  }

}
