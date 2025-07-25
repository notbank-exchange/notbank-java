package exchange.notbank.wallet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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
import exchange.notbank.wallet.paramBuilders.CreateBankAccountParamBuilder;
import exchange.notbank.wallet.paramBuilders.CreateCryptoWithdrawParamBuilder;
import exchange.notbank.wallet.paramBuilders.CreateDepositAddressParamBuilder;
import exchange.notbank.wallet.paramBuilders.CreateFiatDepositParamBuilder;
import exchange.notbank.wallet.paramBuilders.CreateFiatWithdrawParamBuilder;
import exchange.notbank.wallet.paramBuilders.DeleteBankAccountParamBuilder;
import exchange.notbank.wallet.paramBuilders.DeleteWhitelistedAddressesParamBuilder;
import exchange.notbank.wallet.paramBuilders.GetBankAccountParamBuilder;
import exchange.notbank.wallet.paramBuilders.GetBankAccountsParamBuilder;
import exchange.notbank.wallet.paramBuilders.GetBanksParamBuilder;
import exchange.notbank.wallet.paramBuilders.GetDepositAddressesParamBuilder;
import exchange.notbank.wallet.paramBuilders.GetOwnersFiatWithdrawParamBuilder;
import exchange.notbank.wallet.paramBuilders.GetWhitelistedAddressesParamBuilder;
import exchange.notbank.wallet.paramBuilders.GetnetworksTemplatesParamBuilder;
import exchange.notbank.wallet.paramBuilders.UpdateOneStepWithdraw;
import exchange.notbank.wallet.responses.BankAccount;

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
  public void getBanks() throws InterruptedException, ExecutionException {
    var banks = service.getBanks(new GetBanksParamBuilder("CL"));
    System.out.println(banks.get());
    // TestHelper.checkNoError(banks);
  }

  @Test
  public void createBankAccount() throws InterruptedException, ExecutionException {
    var bankAccount = service.createBankAccount(new CreateBankAccountParamBuilder("CL", "11", "222", "kind")).get();
    System.out.println(bankAccount);
  }

  @Test
  public void getBankAccount() throws InterruptedException, ExecutionException {
    var fetchedBankAccount = service
        .getBankAccount(new GetBankAccountParamBuilder("5feba948-e7e3-472d-b78a-e3134293ab31")).get();
    System.out.println(fetchedBankAccount);
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
  public void createDepositAddress() {
    var depositAddress = service
        .createDepositAddress(new CreateDepositAddressParamBuilder(credentials.accountId, "USDT", "USDT_BSC_TEST"));
    TestHelper.checkNoError(depositAddress);
  }

  @Test
  public void getDepositAddress() {
    var depositAddresses = service
        .getDepositAddresses(new GetDepositAddressesParamBuilder(credentials.accountId, "USDT", "USDT_BSC_TEST"));
    TestHelper.checkNoError(depositAddresses);
  }

  @Test
  public void getWhitelistedAddresses() {
    var addresses = service.getWhitelistedAddresses(new GetWhitelistedAddressesParamBuilder(credentials.accountId));
    TestHelper.checkNoError(addresses);
  }

  @Test
  public void addWhitelistedAddress() {
    var addressId = service.addWhitelistedAddress(new AddWhitelistedAddressesParamBuilder(
        credentials.accountId, "USDT", "USDT_BSC_TEST", "0x019d9fd2549235105c6C7fd406dF6E08Fd832d61", "a label",
        133513));
    TestHelper.checkNoError(addressId);
  }

  @Test
  public void confirmWhitelistedAddress() {
    var addressId = "11baa35a-aee0-dea9-8528-73075254f9d7";
    var futureResponse = service
        .confirmWhitelistedAddress(
            new ConfirmWhitelistedAddressesParamBuilder(credentials.accountId, addressId, "123"));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void deleteWhitelistedAddress() {
    var addressId = UUID.fromString("b271fa07-f8bc-e46f-fedd-bdb789e0d90b");
    var futureResponse = service
        .deleteWhitelistedAddress(new DeleteWhitelistedAddressesParamBuilder(credentials.accountId, addressId, 785989));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void updateOneStepWithdraw() {
    var futureResponse = service.updateOneStepWithdraw(
        new UpdateOneStepWithdraw(credentials.accountId, WithdrawAction.DISABLE, 468211));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void createCryptoWithdraw() {
    var withdrawId = service.createCryptoWithdraw(
        new CreateCryptoWithdrawParamBuilder(credentials.accountId, "USDT", "USDT_BSC_TEST",
            "0xD9aF4Be918e2AE1302f37C11939bE3b41A88F23c",
            new BigDecimal("0.1")).otp(560811));
    TestHelper.checkNoError(withdrawId);
  }

  @Test
  public void createFiatDeposit() {
    var futureResponse = service.createFiatDeposit(
        new CreateFiatDepositParamBuilder(
            credentials.accountId,
            DepositPaymentMethod.BANK_TRANSFER,
            "CLP",
            new BigDecimal("123"))
            .bankAccountId(UUID.randomUUID()));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getOwnersFiatWithdraw() {
    var futureResponse = service.getOwnersFiatWithdraw(new GetOwnersFiatWithdrawParamBuilder("1231231231231313123132"));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void createFiatWithdraw() {
    var futureResponse = service.createFiatWithdraw(
        new CreateFiatWithdrawParamBuilder(credentials.accountId, DepositPaymentMethod.BANK_TRANSFER, "CLP",
            new BigDecimal("20000")).bankAccountId("5feba948-e7e3-472d-b78a-e3134293ab31"));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void confirmFiatWithdraw() {
    var futureResponse = service.confirmFiatWithdraw(
        new ConfirmFiatWithdrawParamBuilder(UUID.randomUUID(), "1"));
    TestHelper.checkNoError(futureResponse);
  }
}
