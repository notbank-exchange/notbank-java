package exchange.notbank.wallet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
  public void getWhitelistedAddresses() throws InterruptedException, ExecutionException {
    var addresses = service.getWhitelistedAddresses(new GetWhitelistedAddressesParamBuilder(credentials.accountId))
        .get();
    System.out.println(addresses);
  }

  @Test
  public void addWhitelistedAddress() throws InterruptedException, ExecutionException {
    service.addWhitelistedAddress(new AddWhitelistedAddressesParamBuilder(
        credentials.accountId, "USDT", "USDT_BSC_TEST", "0x019d9fd2549235105c6C7fd406dF6E08Fd832d61", "a label",
        133513))
        .get();
  }

  @Test
  public void confirmWhitelistedAddress() throws InterruptedException, ExecutionException {
    var addressId = UUID.fromString("0x019d9fd2549235105c6C7fd406dF6E08Fd832d61");
    service
        .confirmWhitelistedAddress(new ConfirmWhitelistedAddressesParamBuilder(credentials.accountId, addressId, "123"))
        .get();
  }

  @Test
  public void deleteWhitelistedAddress() throws InterruptedException, ExecutionException {
    var addressId = UUID.fromString("b271fa07-f8bc-e46f-fedd-bdb789e0d90b");
    service
        .deleteWhitelistedAddress(new DeleteWhitelistedAddressesParamBuilder(credentials.accountId, addressId, 785989))
        .get();
  }

  @Test
  public void updateOneStepWithdraw() throws InterruptedException, ExecutionException {
    service.updateOneStepWithdraw(
        new UpdateOneStepWithdraw(credentials.accountId, WithdrawAction.DISABLE, 468211)).get();
  }

  @Test
  public void createCryptoWithdraw() throws InterruptedException, ExecutionException {
    var withdrawId = service.createCryptoWithdraw(
        new CreateCryptoWithdrawParamBuilder(credentials.accountId, "USDT", "USDT_BSC_TEST",
            "0xD9aF4Be918e2AE1302f37C11939bE3b41A88F23c",
            new BigDecimal("0.1")).otp(560811))
        .get();
    assertTrue(withdrawId.length() > 0);
  }

  @Test
  public void createFiatDeposit() throws InterruptedException, ExecutionException {
    var futureResponse = service.createFiatDeposit(
        new CreateFiatDepositParamBuilder(
            credentials.accountId,
            DepositPaymentMethod.BANK_TRANSFER,
            "CLP",
            new BigDecimal("123"))
            .bankAccountId("5feba948-e7e3-472d-b78a-e3134293ab31"));
    System.out.println(futureResponse.get());
  }

  @Test
  public void getOwnersFiatWithdraw() throws InterruptedException, ExecutionException {
    // *#reunion need help to test? 
    // *error = {"status":"error","code":"invalid_request","message":"CBU es requerido"}
    // *cbu usado es el del ejemplo
    var futureResponse = service.getOwnersFiatWithdraw(new GetOwnersFiatWithdrawParamBuilder("6845784411100069899422"));
    System.out.println(futureResponse.get());
  }

  @Test
  public void createFiatWithdraw() throws InterruptedException, ExecutionException {
    var futureResponse = service.createFiatWithdraw(
        new CreateFiatWithdrawParamBuilder(credentials.accountId, DepositPaymentMethod.BANK_TRANSFER, "CLP",
            new BigDecimal("20000")).bankAccountId("5feba948-e7e3-472d-b78a-e3134293ab31"));
    System.out.println(futureResponse.get());
  }

  @Test
  public void confirmFiatWithdraw() throws InterruptedException, ExecutionException {
    // *#reunion, error response is:
    // * <!DOCTYPE html><html lang="en"><head><title>500 | CryptoMarket</title><meta charset="utf-8"/><meta content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" name="viewport"/><meta content="ie=edge" http-equiv="X-UA-Compatible"/><meta content="cryptomkt" name="keywords"/><meta content="Dysopsis" name="author"/><link href="https://testing-r93bz-1.cryptomkt.com/static/errors/css/404.css" rel="stylesheet"/></head><body style="margin: 0; height: 100vh"><div class="content-error"><img alt="404 error" class="principl-error-img" src="https://testing-r93bz-1.cryptomkt.com/static/errors/img/404-error.png"/><h1>Oops! the page you`re looking for cannot be found</h1><p>Visit</p><a class="button-404" href="https://www.cryptomkt.com/">www.cryptomkt.com</a></div></body></html>

    // uses the id with createFiatWithdraw. only used for argentina fiat
    var futureResponse = service.confirmFiatWithdraw(
        new ConfirmFiatWithdrawParamBuilder(UUID.randomUUID(), "1"));
    System.out.println(futureResponse.get());
  }
}
