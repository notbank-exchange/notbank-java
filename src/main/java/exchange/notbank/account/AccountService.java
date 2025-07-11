package exchange.notbank.account;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;

import exchange.notbank.account.constants.Endpoints;
import exchange.notbank.account.paramBuilders.GetAccountInfoParamBuilder;
import exchange.notbank.account.paramBuilders.GetAccountInstrumentStatisticsParamBuilder;
import exchange.notbank.account.paramBuilders.GetAccountPositionsParamBuilder;
import exchange.notbank.account.paramBuilders.GetAccountTransactionsParamBuilder;
import exchange.notbank.account.responses.AccountInfo;
import exchange.notbank.account.responses.AccountInstrumentStatistic;
import exchange.notbank.account.responses.Position;
import exchange.notbank.account.responses.Transaction;
import exchange.notbank.core.EndpointCategory;
import exchange.notbank.core.NotbankConnection;
import exchange.notbank.core.NotbankException;
import exchange.notbank.core.ParamBuilder;

import io.vavr.control.Either;

public class AccountService {
  private final Supplier<CompletableFuture<NotbankConnection>> getNotbankConnection;
  private final AccountServiceResponseAdapter responseAdapter;

  public AccountService(Supplier<CompletableFuture<NotbankConnection>> getNotbankConnection,
      AccountServiceResponseAdapter userResponseAdapter) {
    this.getNotbankConnection = getNotbankConnection;
    this.responseAdapter = userResponseAdapter;
  }

  private <T> CompletableFuture<T> requestPost(String endpoint, ParamBuilder paramBuilder,
      Function<String, Either<NotbankException, T>> deserializeFn) {
    return getNotbankConnection.get()
        .thenCompose(
            connection -> connection.requestPost(EndpointCategory.AP, endpoint, paramBuilder, deserializeFn));
  }

  /**
   * https://apidoc.notbank.exchange/#getaccounttransactions
   */
  public CompletableFuture<List<Transaction>> getAccountTransactions(GetAccountTransactionsParamBuilder paramBuilder) {
    return requestPost(Endpoints.GET_ACCOUNT_TRANSACTIONS, paramBuilder,
        responseAdapter::toTransactionList);
  }

  /**
   * https://apidoc.notbank.exchange/#getaccountpositions
   */
  public CompletableFuture<List<Position>> getAccountPositions(GetAccountPositionsParamBuilder paramBuilder) {
    return requestPost(Endpoints.GET_ACCOUNT_POSITIONS, paramBuilder, responseAdapter::toBalanceList);
  }

  /**
   * https://apidoc.notbank.exchange/#getaccountinfo
   */
  public CompletableFuture<AccountInfo> getAccountInfo(GetAccountInfoParamBuilder paramBuilder) {
    return requestPost(Endpoints.GET_ACCOUNT_INFO, paramBuilder, responseAdapter::toAccountInfo);
  }

  /**
   * https://apidoc.notbank.exchange/#getaccountinstrumentstatistics
   */
  public CompletableFuture<List<AccountInstrumentStatistic>> getAccountInstrumentStatistics(
      GetAccountInstrumentStatisticsParamBuilder paramBuilder) {
    return requestPost(Endpoints.GET_ACCOUNT_INSTRUMENT_STATISTICS, paramBuilder,
        responseAdapter::toAccountInstrumentStatisticList);
  }
}
