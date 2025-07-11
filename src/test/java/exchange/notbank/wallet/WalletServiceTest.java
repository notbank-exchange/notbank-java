package exchange.notbank.wallet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import exchange.notbank.CredentialsLoader.UserCredentials;
import exchange.notbank.NotbankClient;
import exchange.notbank.TestHelper;
import exchange.notbank.wallet.paramBuilders.AddWhitelistedAddressesParamBuilder;
import exchange.notbank.wallet.paramBuilders.ConfirmWhitelistedAddressesParamBuilder;
import exchange.notbank.wallet.paramBuilders.CreateBankAccountParamBuilder;
import exchange.notbank.wallet.paramBuilders.CreateCryptoWithdrawParamBuilder;
import exchange.notbank.wallet.paramBuilders.CreateDepositAddressParamBuilder;
import exchange.notbank.wallet.paramBuilders.DeleteBankAccountParamBuilder;
import exchange.notbank.wallet.paramBuilders.DeleteWhitelistedAddressesParamBuilder;
import exchange.notbank.wallet.paramBuilders.GetBankAccountParamBuilder;
import exchange.notbank.wallet.paramBuilders.GetBankAccountsParamBuilder;
import exchange.notbank.wallet.paramBuilders.GetBanksParamBuilder;
import exchange.notbank.wallet.paramBuilders.GetDepositAddressesParamBuilder;
import exchange.notbank.wallet.paramBuilders.GetWhitelistedAddressesParamBuilder;
import exchange.notbank.wallet.paramBuilders.GetnetworksTemplatesParamBuilder;
import exchange.notbank.wallet.paramBuilders.UpdateOneStepWithdraw;
import exchange.notbank.wallet.responses.BankAccount;

public class WalletServiceTest {
  private static WalletService service;
  private static UserCredentials credentials;

  @BeforeAll
  public static void beforeAll() throws InterruptedException, ExecutionException {
    var client = NotbankClient.Factory.createRestClient(TestHelper.HOST);
    credentials = TestHelper.getUserCredentials();
    client.authenticate(credentials.userId, credentials.apiPublicKey, credentials.apiSecretKey).get();
    service = client.getWalletService();

  }

  @Test
  public void getBanks() throws InterruptedException, ExecutionException {
    var banks = service.getBanks(new GetBanksParamBuilder("CL"));
    System.out.println(banks.get());
    // TestHelper.checkNoError(banks);
  }

  @Test
  public void bankAccountFlow() throws InterruptedException, ExecutionException {
    var bankAccount = service.createBankAccount(new CreateBankAccountParamBuilder("CL", "123", "111", "kind")).get();
    var fetchedBankAccount = service.getBankAccount(new GetBankAccountParamBuilder(bankAccount.id)).get();
    assertEqualBankAccounts(bankAccount, fetchedBankAccount);

    var fetchedBankAccounts = service.getBankAccounts(new GetBankAccountsParamBuilder()).get();
    var filteredBankAccount = fetchedBankAccounts.accounts.stream()
        .filter(bnkAccount -> bankAccount.id.equals(bankAccount.id)).findFirst();
    if (filteredBankAccount.isEmpty()) {
      fail();
    }
    assertEqualBankAccounts(bankAccount, filteredBankAccount.get());
    service.deleteBankAccount(new DeleteBankAccountParamBuilder(bankAccount.id)).get();
  }

  private void assertEqualBankAccounts(BankAccount bankAccount, BankAccount fetchedBankAccount) {
    assertEquals(bankAccount.id, fetchedBankAccount.id);
    assertEquals(bankAccount.country, fetchedBankAccount.country);
    assertEquals(bankAccount.currency, fetchedBankAccount.currency);
  }

  @Test
  public void getNetworkTemplates() {
    var networkTemplates = service.getNetworksTemplates(new GetnetworksTemplatesParamBuilder("USDT"));
    TestHelper.checkNoError(networkTemplates);
  }

  @Test
  public void createDepositAddress() throws InterruptedException, ExecutionException {
    var depositAddress = service
        .createDepositAddress(new CreateDepositAddressParamBuilder(credentials.accountId, "USDT", "USDT_BSC_TEST"))
        .get();
    System.out.println(depositAddress);
  }

  @Test
  public void getDepositAddress() throws InterruptedException, ExecutionException {
    var depositAddress = service
        .getDepositAddresses(new GetDepositAddressesParamBuilder(credentials.accountId, "USDT", "USDT_BSC_TEST"))
        .get();
    System.out.println(depositAddress);
  }

  @Test
  public void whitelistedAddressFlow() throws InterruptedException, ExecutionException {
    service.getWhitelistedAddresses(new GetWhitelistedAddressesParamBuilder(credentials.accountId)).get();
    var addressId = service
        .addWhitelistedAddress(new AddWhitelistedAddressesParamBuilder(
            credentials.accountId, "USDT", "USDT_BSC_TEST", "123123", "a label", "otp"))
        .get();
    service.confirmWhitelistedAddress(new ConfirmWhitelistedAddressesParamBuilder(addressId, "123")).get();
    service.getWhitelistedAddresses(new GetWhitelistedAddressesParamBuilder(credentials.accountId)).get();
    service
        .deleteWhitelistedAddress(new DeleteWhitelistedAddressesParamBuilder(addressId, credentials.accountId, "123"))
        .get();
  }

  @Test
  public void updateOneStepWithdraw() throws InterruptedException, ExecutionException {
    service.updateOneStepWithdraw(new UpdateOneStepWithdraw("action", "otp")).get();
  }

  @Test
  public void createCryptoWithdraw() throws InterruptedException, ExecutionException {
    var withdrawId = service.createCryptoWithdraw(
        new CreateCryptoWithdrawParamBuilder(credentials.accountId, "BTC", "USDT_BSC", "1231213",
            new BigDecimal("0.1")))
        .get();
  }
}
