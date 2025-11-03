package exchange.notbank.wallet;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import exchange.notbank.CredentialsLoader.UserCredentials;
import exchange.notbank.NotbankClient;
import exchange.notbank.TestHelper;
import exchange.notbank.wallet.constants.BankAccountKind;
import exchange.notbank.wallet.constants.DepositPaymentMethod;
import exchange.notbank.wallet.constants.UpdateOneStepWithdrawAction;
import exchange.notbank.wallet.paramBuilders.AddClientBankAccountParamBuilder;
import exchange.notbank.wallet.paramBuilders.AddWhitelistedAddressesParamBuilder;
import exchange.notbank.wallet.paramBuilders.ConfirmFiatWithdrawParamBuilder;
import exchange.notbank.wallet.paramBuilders.ConfirmWhitelistedAddressesParamBuilder;
import exchange.notbank.wallet.paramBuilders.CreateCryptoWithdrawParamBuilder;
import exchange.notbank.wallet.paramBuilders.CreateDepositAddressParamBuilder;
import exchange.notbank.wallet.paramBuilders.CreateFiatDepositParamBuilder;
import exchange.notbank.wallet.paramBuilders.CreateFiatWithdrawParamBuilder;
import exchange.notbank.wallet.paramBuilders.DeleteClientBankAccountParamBuilder;
import exchange.notbank.wallet.paramBuilders.DeleteWhitelistedAddressesParamBuilder;
import exchange.notbank.wallet.paramBuilders.GetBanksParamBuilder;
import exchange.notbank.wallet.paramBuilders.GetClientBankAccountParamBuilder;
import exchange.notbank.wallet.paramBuilders.GetClientBankAccountsParamBuilder;
import exchange.notbank.wallet.paramBuilders.GetDepositAddressesParamBuilder;
import exchange.notbank.wallet.paramBuilders.GetOwnersFiatWithdrawParamBuilder;
import exchange.notbank.wallet.paramBuilders.GetTransactionsParamBuilder;
import exchange.notbank.wallet.paramBuilders.GetWhitelistedAddressesParamBuilder;
import exchange.notbank.wallet.paramBuilders.GetnetworksTemplatesParamBuilder;
import exchange.notbank.wallet.paramBuilders.ResendVerificationCodeWhitelistedAddresParamBuilder;
import exchange.notbank.wallet.paramBuilders.TransferFundsParamBuilder;
import exchange.notbank.wallet.paramBuilders.UpdateOneStepWithdrawParamBuilder;

public class WalletServiceTest {
  private static UserCredentials credentials;
  private static NotbankClient client;

  @BeforeAll
  public static void beforeAll() throws InterruptedException, ExecutionException {
    client = TestHelper.newRestClient();
    credentials = TestHelper.getUserCredentials();
    client.authenticate(credentials.userId, credentials.apiPublicKey, credentials.apiSecretKey).get();
  }

  @Test
  public void getBanks() {
    var futureResponse = client.getWalletService().getBanks(new GetBanksParamBuilder("CL"));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void createBankAccount() {
    var futureResponse = client.getWalletService().addClientBankAccount(new AddClientBankAccountParamBuilder(
        "CL",
        "11",
        "222",
        BankAccountKind.VISTA));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getBankAccount() {
    var futureResponse = client.getWalletService()
        .getClientBankAccount(new GetClientBankAccountParamBuilder("5feba948-e7e3-472d-b78a-e3134293ab31"));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getBankAccounts() {
    var futureResponse = client.getWalletService()
        .getClientBankAccounts(new GetClientBankAccountsParamBuilder());
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void bankAccountFlow() {
    var futureResponse = client.getWalletService()
        .deleteClientBankAccount(new DeleteClientBankAccountParamBuilder("5feba948-e7e3-472d-b78a-e3134293ab31"));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getNetworkTemplates() {
    var networkTemplates = client.getWalletService().getNetworksTemplates(new GetnetworksTemplatesParamBuilder("USDT"));
    TestHelper.checkNoError(networkTemplates);
  }

  @Test
  public void createDepositAddress() {
    var futureResponse = client.getWalletService()
        .createDepositAddress(new CreateDepositAddressParamBuilder(credentials.accountId, "USDT", "USDT_BSC_TEST"));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getDepositAddress() {
    var futureResponse = client.getWalletService()
        .getDepositAddresses(new GetDepositAddressesParamBuilder(credentials.accountId, "BTC", "BTC_TEST"));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getWhitelistedAddresses() {
    var futureResponse = client.getWalletService()
        .getWhitelistedAddresses(new GetWhitelistedAddressesParamBuilder(credentials.accountId).search("BTC"));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void addWhitelistedAddress() {
    var futureResponse = client.getWalletService().addWhitelistedAddress(new AddWhitelistedAddressesParamBuilder(
        credentials.accountId,
        "BTC",
        "BTC_TEST",
        "tb1q8e7md5kg4m5j8lmgyp9caqfhx62mvv6pvd5dqj",
        "a label",
        "135866"));
    //3d9c928f-f40a-4793-898b-111feb4ff46e
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void confirmWhitelistedAddress() {
    var futureResponse = client.getWalletService().confirmWhitelistedAddress(
        new ConfirmWhitelistedAddressesParamBuilder(
            credentials.accountId,
            "33401503-0c7b-4765-90b2-4ca00253241b",
            "0794235"));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void resendVerificationCodeWhitelistedAddress() {
    var futureResponse = client.getWalletService().resendVerificationCodeWhitelistedAddress(
        new ResendVerificationCodeWhitelistedAddresParamBuilder(credentials.accountId,
            UUID.fromString("067023f5-a522-46a8-abf1-026198b71314")));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void deleteWhitelistedAddress() {
    var futureResponse = client.getWalletService()
        .deleteWhitelistedAddress(
            new DeleteWhitelistedAddressesParamBuilder(
                credentials.accountId,
                UUID.fromString("3d9c928f-f40a-4793-898b-111feb4ff46e"),
                "631133"));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void updateOneStepWithdraw() {
    var futureResponse = client.getWalletService().updateOneStepWithdraw(
        new UpdateOneStepWithdrawParamBuilder(credentials.accountId, UpdateOneStepWithdrawAction.ENABLE, "444429"));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void createCryptoWithdraw() {
    var futureResponse = client.getWalletService().createCryptoWithdraw(
        new CreateCryptoWithdrawParamBuilder(credentials.accountId, "USDT", "USDT_BSC_TEST",
            "6a36bdf4-cf21-42ce-9945-6008b0485969",
            new BigDecimal("0.1")).otp("526627"));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void createFiatDeposit() {
    var futureResponse = client.getWalletService().createFiatDeposit(
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
    var futureResponse = client.getWalletService()
        .getOwnersFiatWithdraw(new GetOwnersFiatWithdrawParamBuilder("1231231231231231231231"));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void createFiatWithdraw() {
    var futureResponse = client.getWalletService().createFiatWithdraw(new CreateFiatWithdrawParamBuilder(
        credentials.accountId,
        DepositPaymentMethod.BANK_TRANSFER,
        "CLP",
        new BigDecimal("20000"))
        .bankAccountId("72d3e264-2473-41fb-b3ca-a08231de05e2"));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void confirmFiatWithdraw() {
    var futureResponse = client.getWalletService().confirmFiatWithdraw(
        new ConfirmFiatWithdrawParamBuilder(UUID.fromString("32347216-4a4c-49ee-b0a5-1ad993fe522b"), "123456"));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getTransactions() throws InterruptedException, ExecutionException {
    var futureResponse = client.getWalletService().getTransactions(new GetTransactionsParamBuilder());
    System.out.println(futureResponse.get());
    // TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void transferFunds() {
    var futureResponse = client.getWalletService().transferFunds(new TransferFundsParamBuilder(
        credentials.accountId,
        13,
        "USDT",
        new BigDecimal("1"))
        .notes("transfer detail")
        .otp("123456"));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void deleteClientBankAccount() {
    var futureResponse = client.getWalletService()
        .deleteClientBankAccount(new DeleteClientBankAccountParamBuilder("72d3e264-2473-41fb-b3ca-a08231de05e2"));
    TestHelper.checkNoError(futureResponse);
  }
}
