package exchange.notbank.fee;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import exchange.notbank.core.ErrorHandler;
import exchange.notbank.core.NotbankException;
import exchange.notbank.core.StandardApResponseHandler;
import exchange.notbank.fee.responses.AccountFee;
import exchange.notbank.fee.responses.DepositFee;
import exchange.notbank.fee.responses.OrderFee;
import exchange.notbank.fee.responses.TransactionFee;
import exchange.notbank.fee.responses.WithdrawFee;
import io.vavr.control.Either;

public class FeeServiceResponseAdapter {
  private final ErrorHandler errorHandler;
  private final JsonAdapter<List<TransactionFee>> transactionFeeListJsonAdapter;
  private final JsonAdapter<List<AccountFee>> accountFeeListJsonAdapter;
  private final JsonAdapter<DepositFee> depositFeeJsonAdapter;
  private final JsonAdapter<WithdrawFee> withdrawFeeJsonAdapter;
  private final JsonAdapter<OrderFee> orderFeeJsonAdapter;

  public FeeServiceResponseAdapter(StandardApResponseHandler responseHandler, Moshi moshi) {
    this.errorHandler = ErrorHandler.Factory.createApErrorHandler(moshi);
    ParameterizedType TransactionFeeListType = Types.newParameterizedType(List.class, TransactionFee.class);
    this.transactionFeeListJsonAdapter = moshi.adapter(TransactionFeeListType);
    ParameterizedType AccountFeeListType = Types.newParameterizedType(List.class, AccountFee.class);
    this.accountFeeListJsonAdapter = moshi.adapter(AccountFeeListType);
    this.depositFeeJsonAdapter = moshi.adapter(DepositFee.class);
    this.withdrawFeeJsonAdapter = moshi.adapter(WithdrawFee.class);
    this.orderFeeJsonAdapter = moshi.adapter(OrderFee.class);
  }

  public Either<NotbankException, Void> toNone(String jsonStr) {
    return errorHandler.toNone(jsonStr);
  }

  private <T> Either<NotbankException, T> handle(String jsonString, JsonAdapter<T> jsonAdapter) {
    return errorHandler.handleAndThen(jsonAdapter).apply(jsonString);
  }

  public Either<NotbankException, List<TransactionFee>> toTransactionFeeList(String jsonStr) {
    return handle(jsonStr, transactionFeeListJsonAdapter);
  }

  public Either<NotbankException, List<AccountFee>> toAccountFeeList(String jsonStr) {
    return handle(jsonStr, accountFeeListJsonAdapter);
  }

  public Either<NotbankException, DepositFee> toDepositFee(String jsonStr) {
    return handle(jsonStr, depositFeeJsonAdapter);
  }

  public Either<NotbankException, WithdrawFee> toWithdrawFee(String jsonStr) {
    return handle(jsonStr, withdrawFeeJsonAdapter);
  }

  public Either<NotbankException, OrderFee> toOrderFee(String jsonStr) {
    return handle(jsonStr, orderFeeJsonAdapter);
  }
}
