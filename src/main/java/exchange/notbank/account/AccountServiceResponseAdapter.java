package exchange.notbank.account;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import exchange.notbank.account.responses.AccountInfo;
import exchange.notbank.account.responses.AccountInstrumentStatistic;
import exchange.notbank.account.responses.Position;
import exchange.notbank.account.responses.Transaction;
import exchange.notbank.core.ErrorHandler;
import exchange.notbank.core.NotbankException;
import exchange.notbank.core.StandardApResponseHandler;
import exchange.notbank.core.responses.StandardResponse;
import io.vavr.control.Either;

public class AccountServiceResponseAdapter {
  private final ErrorHandler errorHandler;
  private final JsonAdapter<StandardResponse> standardResponseJsonAdapter;
  private final JsonAdapter<List<Transaction>> transactionListJsonAdapter;
  private final JsonAdapter<AccountInfo> accountInfoJsonAdapter;
  private final JsonAdapter<List<AccountInfo>> accountInfoListJsonAdapter;
  private final JsonAdapter<List<AccountInstrumentStatistic>> accountInstrumentStatisticListJsonAdapter;
  private final JsonAdapter<List<Position>> balanceListJsonAdapter;

  public AccountServiceResponseAdapter(StandardApResponseHandler responseHandler, Moshi moshi) {
    this.errorHandler = ErrorHandler.Factory.createApErrorHandler(moshi);
    this.standardResponseJsonAdapter = moshi.adapter(StandardResponse.class);
    ParameterizedType TransactionListType = Types.newParameterizedType(List.class, Transaction.class);
    this.transactionListJsonAdapter = moshi.adapter(TransactionListType);
    this.accountInfoJsonAdapter = moshi.adapter(AccountInfo.class);
    ParameterizedType UserAccountInfoListType = Types.newParameterizedType(List.class, AccountInfo.class);
    this.accountInfoListJsonAdapter = moshi.adapter(UserAccountInfoListType);
    ParameterizedType AccountInstrumentStatisticListType = Types.newParameterizedType(List.class,
        AccountInstrumentStatistic.class);
    this.accountInstrumentStatisticListJsonAdapter = moshi.adapter(AccountInstrumentStatisticListType);
    ParameterizedType BalanceListType = Types.newParameterizedType(List.class,
        Position.class);
    this.balanceListJsonAdapter = moshi.adapter(BalanceListType);
  }

  public Either<NotbankException, Void> toNone(String jsonStr) {
    return errorHandler.toNone(jsonStr);
  }

  private <T> Either<NotbankException, T> handle(String jsonString, JsonAdapter<T> jsonAdapter) {
    return errorHandler.handleAndThen(jsonAdapter).apply(jsonString);
  }

  public Either<NotbankException, StandardResponse> toStandardResponse(String jsonStr) {
    return handle(jsonStr, standardResponseJsonAdapter);
  }

  public Either<NotbankException, List<Transaction>> toTransactionList(String jsonStr) {
    return handle(jsonStr, transactionListJsonAdapter);
  }

  public Either<NotbankException, List<AccountInfo>> toAccountInfoList(String jsonStr) {
    return handle(jsonStr, accountInfoListJsonAdapter);
  }

  public Either<NotbankException, AccountInfo> toAccountInfo(String jsonStr) {
    return handle(jsonStr, accountInfoJsonAdapter);
  }

  public Either<NotbankException, List<AccountInstrumentStatistic>> toAccountInstrumentStatisticList(String jsonStr) {
    return handle(jsonStr, accountInstrumentStatisticListJsonAdapter);
  }

  public Either<NotbankException, List<Position>> toBalanceList(String jsonStr) {
    return handle(jsonStr, balanceListJsonAdapter);
  }
}