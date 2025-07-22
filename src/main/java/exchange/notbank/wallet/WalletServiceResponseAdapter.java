package exchange.notbank.wallet;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.UUID;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import exchange.notbank.core.ErrorHandler;
import exchange.notbank.core.NotbankException;
import exchange.notbank.core.responses.DataResponse;
import exchange.notbank.wallet.responses.BankAccount;
import exchange.notbank.wallet.responses.BankAccounts;
import exchange.notbank.wallet.responses.Banks;
import exchange.notbank.wallet.responses.CurrencyNetworkTemplates;
import exchange.notbank.wallet.responses.IdResponse;
import exchange.notbank.wallet.responses.Transaction;
import exchange.notbank.wallet.responses.WhitelistedAddress;
import io.vavr.control.Either;

public class WalletServiceResponseAdapter {
  private final ErrorHandler errorHandler;
  private final JsonAdapter<Banks> banksJsonAdapter;
  private final JsonAdapter<BankAccounts> bankAccountsJsonAdapter;
  private final JsonAdapter<DataResponse<BankAccount>> bankAccountJsonAdapter;
  private final JsonAdapter<DataResponse<String>> stringJsonAdapter;
  private final JsonAdapter<DataResponse<List<String>>> stringListJsonAdapter;
  private final JsonAdapter<DataResponse<List<WhitelistedAddress>>> whitelistedAddressListJsonAdapter;
  private final JsonAdapter<DataResponse<IdResponse>> idResponseJsonAdapter;
  private final JsonAdapter<DataResponse<List<CurrencyNetworkTemplates>>> currencyNetworkTemplatesJsonAdapter;
  private final JsonAdapter<DataResponse<List<Transaction>>> transactionListJsonAdapter;

  public WalletServiceResponseAdapter(Moshi moshi) {
    this.errorHandler = ErrorHandler.Factory.createNbErrorHandler(moshi);
    this.banksJsonAdapter = moshi.adapter(Banks.class);
    this.bankAccountsJsonAdapter = moshi.adapter(BankAccounts.class);
    ParameterizedType BankAccountResponseType = Types.newParameterizedType(
        DataResponse.class,
        BankAccount.class);
    this.bankAccountJsonAdapter = moshi.adapter(BankAccountResponseType);
    ParameterizedType StringResponseType = Types.newParameterizedType(
        DataResponse.class,
        String.class);
    this.stringJsonAdapter = moshi.adapter(StringResponseType);
    ParameterizedType StringListType = Types.newParameterizedType(
        List.class,
        String.class);
    ParameterizedType StringListResponseType = Types.newParameterizedType(
        DataResponse.class,
        StringListType);
    this.stringListJsonAdapter = moshi.adapter(StringListResponseType);
    ParameterizedType WhitelistAddressListType = Types.newParameterizedType(
        List.class,
        WhitelistedAddress.class);
    ParameterizedType WhitelistAddressListResponseType = Types.newParameterizedType(
        DataResponse.class,
        WhitelistAddressListType);
    this.whitelistedAddressListJsonAdapter = moshi.adapter(WhitelistAddressListResponseType);
    ParameterizedType IdResponseType = Types.newParameterizedType(
        DataResponse.class,
        IdResponse.class);
    this.idResponseJsonAdapter = moshi.adapter(IdResponseType);
    ParameterizedType CurrencyNetworkTemplatesListType = Types.newParameterizedType(
        List.class,
        CurrencyNetworkTemplates.class);
    ParameterizedType CurrencyNetworkTemplatesListResponseType = Types.newParameterizedType(
        DataResponse.class,
        CurrencyNetworkTemplatesListType);
    this.currencyNetworkTemplatesJsonAdapter = moshi.adapter(CurrencyNetworkTemplatesListResponseType);
    ParameterizedType TransactionListType = Types.newParameterizedType(
        List.class,
        Transaction.class);
    ParameterizedType TransactionListResponseType = Types.newParameterizedType(
        DataResponse.class,
        TransactionListType);
    this.transactionListJsonAdapter = moshi.adapter(TransactionListResponseType);
  }

  public Either<NotbankException, Void> toNone(String jsonStr) {
    return errorHandler.toNone(jsonStr);
  }

  private <T> Either<NotbankException, T> handle(String jsonString, JsonAdapter<T> jsonAdapter) {
    return errorHandler.handleAndThen(jsonAdapter).apply(jsonString);
  }

  public Either<NotbankException, Banks> toBanks(String jsonStr) {
    return handle(jsonStr, banksJsonAdapter);
  }

  Either<NotbankException, BankAccount> toBankAccount(String jsonStr) {
    return handle(jsonStr, bankAccountJsonAdapter).map(response -> response.data);
  }

  Either<NotbankException, BankAccounts> toBankAccounts(String jsonStr) {
    return handle(jsonStr, bankAccountsJsonAdapter);
  }

  Either<NotbankException, List<CurrencyNetworkTemplates>> toCurrencyNetworkTemplates(String jsonStr) {
    return handle(jsonStr, currencyNetworkTemplatesJsonAdapter).map(response -> response.data);
  }

  Either<NotbankException, String> toStringResponse(String jsonStr) {
    return handle(jsonStr, stringJsonAdapter).map(response -> response.data);
  }

  Either<NotbankException, List<String>> toStringList(String jsonStr) {
    return handle(jsonStr, stringListJsonAdapter).map(response -> response.data);
  }

  Either<NotbankException, List<WhitelistedAddress>> toWhiteListedAddressList(String jsonStr) {
    System.out.println(jsonStr);
    return handle(jsonStr, whitelistedAddressListJsonAdapter).map(response -> response.data);
  }

  Either<NotbankException, UUID> toUuid(String jsonStr) {
    return handle(jsonStr, idResponseJsonAdapter).map(response -> response.data.id);
  }

  Either<NotbankException, List<Transaction>> toTransactionList(String jsonStr) {
    return handle(jsonStr, transactionListJsonAdapter).map(response -> response.data);
  }

}
