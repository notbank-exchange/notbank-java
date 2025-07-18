package exchange.notbank.wallet;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;

import exchange.notbank.core.EndpointCategory;
import exchange.notbank.core.NotbankConnection;
import exchange.notbank.core.NotbankException;
import exchange.notbank.core.ParamBuilder;
import exchange.notbank.wallet.constants.Endpoints;
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
import exchange.notbank.wallet.paramBuilders.GetTransactionsParamBuilder;
import exchange.notbank.wallet.paramBuilders.GetWhitelistedAddressesParamBuilder;
import exchange.notbank.wallet.paramBuilders.GetnetworksTemplatesParamBuilder;
import exchange.notbank.wallet.paramBuilders.TransferFundsParamBuilder;
import exchange.notbank.wallet.paramBuilders.UpdateOneStepWithdraw;
import exchange.notbank.wallet.responses.BankAccount;
import exchange.notbank.wallet.responses.BankAccounts;
import exchange.notbank.wallet.responses.Banks;
import exchange.notbank.wallet.responses.CurrencyNetworkTemplates;
import exchange.notbank.wallet.responses.Transaction;
import exchange.notbank.wallet.responses.WhitelistedAddress;
import io.vavr.control.Either;

public class WalletService {
  private final Supplier<CompletableFuture<NotbankConnection>> getNotbankConnection;
  private final WalletServiceResponseAdapter responseAdapter;

  public WalletService(Supplier<CompletableFuture<NotbankConnection>> getNotbankConnection,
      WalletServiceResponseAdapter responseAdapter) {
    this.getNotbankConnection = getNotbankConnection;
    this.responseAdapter = responseAdapter;
  }

  private <T> CompletableFuture<T> requestPost(String endpoint, ParamBuilder paramBuilder,
      Function<String, Either<NotbankException, T>> deserializeFn) {
    return getNotbankConnection.get()
        .thenCompose(
            connection -> connection.requestPost(EndpointCategory.NB, endpoint, paramBuilder, deserializeFn));
  }

  private <T> CompletableFuture<T> requestGet(String endpoint, ParamBuilder paramBuilder,
      Function<String, Either<NotbankException, T>> deserializeFn) {
    return getNotbankConnection.get()
        .thenCompose(
            connection -> connection.requestGet(EndpointCategory.NB, endpoint, paramBuilder, deserializeFn));
  }

  private <T> CompletableFuture<T> requestDelete(String endpoint, ParamBuilder paramBuilder,
      Function<String, Either<NotbankException, T>> deserializeFn) {
    return getNotbankConnection.get()
        .thenCompose(
            connection -> connection.requestDelete(EndpointCategory.NB, endpoint, paramBuilder,
                deserializeFn));
  }

  /**
   * https://apidoc.notbank.exchange/#getbanks
   */
  public CompletableFuture<Banks> getBanks(GetBanksParamBuilder paramBuilder) {
    return requestGet(Endpoints.BANKS, paramBuilder, responseAdapter::toBanks);
  }

  /**
   * https://apidoc.notbank.exchange/#createbankaccount
   */
  public CompletableFuture<BankAccount> createBankAccount(CreateBankAccountParamBuilder paramBuilder) {
    return requestPost(Endpoints.BANK_ACCOUNTS, paramBuilder, responseAdapter::toBankAccount);
  }

  /**
   * https://apidoc.notbank.exchange/#getbankaccount
   */
  public CompletableFuture<BankAccount> getBankAccount(GetBankAccountParamBuilder paramBuilder) {
    return requestGet(Endpoints.BANK_ACCOUNTS + "/" + paramBuilder.getBankAccountId(), paramBuilder,
        responseAdapter::toBankAccount);
  }

  /**
   * https://apidoc.notbank.exchange/#getbankaccounts
   */
  public CompletableFuture<BankAccounts> getBankAccounts(GetBankAccountsParamBuilder paramBuilder) {
    return requestGet(Endpoints.BANK_ACCOUNTS, paramBuilder, responseAdapter::toBankAccounts);
  }

  /**
   * https://apidoc.notbank.exchange/#deletebankaccount
   * 
   */
  public CompletableFuture<Void> deleteBankAccount(DeleteBankAccountParamBuilder paramBuilder) {
    return requestDelete(Endpoints.BANK_ACCOUNTS + "/" + paramBuilder.getBankAccountId(), paramBuilder,
        responseAdapter::toNone);
  }

  /** 
   *https://apidoc.notbank.exchange/#getnetworkstemplates
   */
  public CompletableFuture<List<CurrencyNetworkTemplates>> getNetworksTemplates(
      GetnetworksTemplatesParamBuilder paramBuilder) {
    return requestGet(Endpoints.GET_NETWORK_TEMPLATES, paramBuilder, responseAdapter::toCurrencyNetworkTemplates);
  }

  /**
   * https://apidoc.notbank.exchange/#getdepositaddresses
   */
  public CompletableFuture<List<String>> getDepositAddresses(
      GetDepositAddressesParamBuilder paramBuilder) {
    return requestGet(Endpoints.DEPOSIT_ADDRESS, paramBuilder, responseAdapter::toStringList);
  }

  /**
   * https://apidoc.notbank.exchange/#createdepositaddress
   */
  public CompletableFuture<String> createDepositAddress(
      CreateDepositAddressParamBuilder paramBuilder) {
    return requestPost(Endpoints.DEPOSIT_ADDRESS, paramBuilder, responseAdapter::toStringResponse);
  }

  /**
   * https://apidoc.notbank.exchange/#getwhitelistedaddresses
   */
  public CompletableFuture<List<WhitelistedAddress>> getWhitelistedAddresses(
      GetWhitelistedAddressesParamBuilder paramBuilder) {
    return requestGet(Endpoints.WHITELISTED_ADDRESSES, paramBuilder, responseAdapter::toWhiteListedAddressList);
  }

  /**
   * https://apidoc.notbank.exchange/#addwhitelistedaddress
   */
  public CompletableFuture<String> addWhitelistedAddress(
      AddWhitelistedAddressesParamBuilder paramBuilder) {
    return requestPost(Endpoints.WHITELISTED_ADDRESSES, paramBuilder, responseAdapter::toIdString);
  }

  /**
   * https://apidoc.notbank.exchange/#confirmwhitelistedaddress
   */
  public CompletableFuture<Void> confirmWhitelistedAddress(ConfirmWhitelistedAddressesParamBuilder paramBuilder) {
    return requestPost(Endpoints.WHITELISTED_ADDRESSES + "/" + paramBuilder.getWhitelistAddressId(), paramBuilder,
        responseAdapter::toNone);
  }

  /**
   * https://apidoc.notbank.exchange/#deletewhitelistedaddress
   */
  public CompletableFuture<Void> deleteWhitelistedAddress(DeleteWhitelistedAddressesParamBuilder paramBuilder) {
    return requestPost(Endpoints.WHITELISTED_ADDRESSES + "/" + paramBuilder.getWhitelistAddressId(), paramBuilder,
        responseAdapter::toNone);
  }

  /**
   * https://apidoc.notbank.exchange/#updateonestepwithdraw
   */

  public CompletableFuture<Void> updateOneStepWithdraw(UpdateOneStepWithdraw paramBuilder) {
    return requestPost(Endpoints.UPDATE_ONE_STEP_WITHDRAW, paramBuilder, responseAdapter::toNone);
  }

  /**
   * https://apidoc.notbank.exchange/#createcryptowithdraw
   */
  public CompletableFuture<String> createCryptoWithdraw(CreateCryptoWithdrawParamBuilder paramBuilder) {
    return requestPost(Endpoints.CREATE_CRIPTO_WITHDRAW, paramBuilder, responseAdapter::toStringResponse);
  }

  /**
   * https://apidoc.notbank.exchange/?http#transferfunds
   */
  public CompletableFuture<String> transferFunds(TransferFundsParamBuilder paramBuilder) {
    return requestPost(Endpoints.TRANSFER_FUNDS, paramBuilder, responseAdapter::toStringResponse);
  }

  /**
   * https://apidoc.notbank.exchange/?http#gettransactions
   */
  public CompletableFuture<List<Transaction>> transferFunds(GetTransactionsParamBuilder paramBuilder) {
    return requestPost(Endpoints.TRANSACTIONS, paramBuilder, responseAdapter::toTransactionList);
  }
}
