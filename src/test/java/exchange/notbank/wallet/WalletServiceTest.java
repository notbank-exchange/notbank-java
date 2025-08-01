package exchange.notbank.wallet;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import exchange.notbank.CredentialsLoader.UserCredentials;
import exchange.notbank.TestHelper;
import exchange.notbank.wallet.constants.DepositPaymentMethod;
import exchange.notbank.wallet.constants.WithdrawAction;
import exchange.notbank.wallet.paramBuilders.AddWhitelistedAddressesParamBuilder;
import exchange.notbank.wallet.paramBuilders.ConfirmFiatWithdrawParamBuilder;
import exchange.notbank.wallet.paramBuilders.ConfirmWhitelistedAddressesParamBuilder;
import exchange.notbank.wallet.paramBuilders.AddClientBankAccountParamBuilder;
import exchange.notbank.wallet.paramBuilders.CreateCryptoWithdrawParamBuilder;
import exchange.notbank.wallet.paramBuilders.CreateDepositAddressParamBuilder;
import exchange.notbank.wallet.paramBuilders.CreateFiatDepositParamBuilder;
import exchange.notbank.wallet.paramBuilders.CreateFiatWithdrawParamBuilder;
import exchange.notbank.wallet.paramBuilders.DeleteClientBankAccountParamBuilder;
import exchange.notbank.wallet.paramBuilders.DeleteWhitelistedAddressesParamBuilder;
import exchange.notbank.wallet.paramBuilders.GetClientBankAccountParamBuilder;
import exchange.notbank.wallet.paramBuilders.GetClientBankAccountsParamBuilder;
import exchange.notbank.wallet.paramBuilders.GetBanksParamBuilder;
import exchange.notbank.wallet.paramBuilders.GetDepositAddressesParamBuilder;
import exchange.notbank.wallet.paramBuilders.GetOwnersFiatWithdrawParamBuilder;
import exchange.notbank.wallet.paramBuilders.GetTransactionsParamBuilder;
import exchange.notbank.wallet.paramBuilders.GetWhitelistedAddressesParamBuilder;
import exchange.notbank.wallet.paramBuilders.GetnetworksTemplatesParamBuilder;
import exchange.notbank.wallet.paramBuilders.ResendVerificationCodeWhitelistedAddresParamBuilder;
import exchange.notbank.wallet.paramBuilders.TransferFundsParamBuilder;
import exchange.notbank.wallet.paramBuilders.UpdateOneStepWithdraw;

public class WalletServiceTest {
  private static WalletService service;
  private static UserCredentials credentials;

  @BeforeAll
  public static void beforeAll() throws InterruptedException, ExecutionException {
    var client = TestHelper.newRestClient();
    credentials = TestHelper.getUserCredentials();
    client.authenticate(credentials.userId, credentials.apiPublicKey, credentials.apiSecretKey).get();
    service = client.getWalletService();

  }

  @Test
  public void getBanks() {
    var futureResponse = service.getBanks(new GetBanksParamBuilder("CL"));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void createBankAccount() {
    var futureResponse = service.addClientBankAccount(new AddClientBankAccountParamBuilder("CL", "11", "222", "kind"));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getBankAccount() {
    var futureResponse = service
        .getClientBankAccount(new GetClientBankAccountParamBuilder("5feba948-e7e3-472d-b78a-e3134293ab31"));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getBankAccounts() {
    var futureResponse = service
        .getClientBankAccounts(new GetClientBankAccountsParamBuilder());
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void bankAccountFlow() {
    var futureResponse = service
        .deleteClientBankAccount(new DeleteClientBankAccountParamBuilder("5feba948-e7e3-472d-b78a-e3134293ab31"));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getNetworkTemplates() {
    var networkTemplates = service.getNetworksTemplates(new GetnetworksTemplatesParamBuilder("USDT"));
    TestHelper.checkNoError(networkTemplates);
  }

  @Test
  public void createDepositAddress() {
    var futureResponse = service
        .createDepositAddress(new CreateDepositAddressParamBuilder(credentials.accountId, "USDT", "USDT_BSC_TEST"));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getDepositAddress() {
    var futureResponse = service
        .getDepositAddresses(new GetDepositAddressesParamBuilder(credentials.accountId, "BTC", "BTC_TEST"));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getWhitelistedAddresses() {
    var futureResponse = service
        .getWhitelistedAddresses(new GetWhitelistedAddressesParamBuilder(credentials.accountId).search("BTC"));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void addWhitelistedAddress() {
    var futureResponse = service.addWhitelistedAddress(new AddWhitelistedAddressesParamBuilder(
        credentials.accountId, "BTC", "BTC_TEST",
        "tb1q8e7md5kg4m5j8lmgyp9caqfhx62mvv6pvd5dqj", "a label",
        "135866"));
    //3d9c928f-f40a-4793-898b-111feb4ff46e
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void confirmWhitelistedAddress() {
    var futureResponse = service.confirmWhitelistedAddress(
        new ConfirmWhitelistedAddressesParamBuilder(
            credentials.accountId,
            "33401503-0c7b-4765-90b2-4ca00253241b",
            "0794235"));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void resendVerificationCodeWhitelistedAddress() {
    var futureResponse = service.resendVerificationCodeWhitelistedAddress(
        new ResendVerificationCodeWhitelistedAddresParamBuilder(credentials.accountId,
            UUID.fromString("067023f5-a522-46a8-abf1-026198b71314")));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void deleteWhitelistedAddress() {
    var futureResponse = service
        .deleteWhitelistedAddress(
            new DeleteWhitelistedAddressesParamBuilder(
                credentials.accountId,
                UUID.fromString("3d9c928f-f40a-4793-898b-111feb4ff46e"),
                "631133"));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void updateOneStepWithdraw() {
    var futureResponse = service.updateOneStepWithdraw(
        new UpdateOneStepWithdraw(credentials.accountId, WithdrawAction.ENABLE, "444429"));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void createCryptoWithdraw() {
    var futureResponse = service.createCryptoWithdraw(
        new CreateCryptoWithdrawParamBuilder(credentials.accountId, "USDT", "USDT_BSC_TEST",
            "6a36bdf4-cf21-42ce-9945-6008b0485969",
            new BigDecimal("0.1")).otp("526627"));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void createFiatDeposit() {
    var futureResponse = service.createFiatDeposit(
        new CreateFiatDepositParamBuilder(
            credentials.accountId,
            DepositPaymentMethod.BANK_TRANSFER,
            "ARS",
            new BigDecimal("1000"))
            .bankAccountId("72d3e264-2473-41fb-b3ca-a08231de05e2"));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getOwnersFiatWithdraw() {
    var futureResponse = service.getOwnersFiatWithdraw(new GetOwnersFiatWithdrawParamBuilder("1231231231231231231231"));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void createFiatWithdraw() {
    var futureResponse = service.createFiatWithdraw(
        new CreateFiatWithdrawParamBuilder(
            credentials.accountId,
            DepositPaymentMethod.BANK_TRANSFER,
            "CLP",
            new BigDecimal("20000"))
            .bankAccountId("72d3e264-2473-41fb-b3ca-a08231de05e2"));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void confirmFiatWithdraw() {
    var futureResponse = service.confirmFiatWithdraw(new ConfirmFiatWithdrawParamBuilder(UUID.randomUUID(), "1"));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getTransactions() {
    var futureResponse = service.getTransactinos(
        new GetTransactionsParamBuilder());
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void transferFunds() {
    var futureResponse = service.transferFunds(new TransferFundsParamBuilder(
        credentials.accountId,
        13,
        "USDT", new BigDecimal("1")));
    TestHelper.checkNoError(futureResponse);
  }
}
