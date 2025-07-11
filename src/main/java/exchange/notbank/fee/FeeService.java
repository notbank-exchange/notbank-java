package exchange.notbank.fee;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;

import exchange.notbank.core.EndpointCategory;
import exchange.notbank.core.NotbankConnection;
import exchange.notbank.core.NotbankException;
import exchange.notbank.core.ParamBuilder;
import exchange.notbank.fee.constants.Endpoints;
import exchange.notbank.fee.paramBuilders.GetAccountFeesParamBuilder;
import exchange.notbank.fee.paramBuilders.GetDepositFeeParamBuilder;
import exchange.notbank.fee.paramBuilders.GetOmsDepositFeesParamsBuilder;
import exchange.notbank.fee.paramBuilders.GetOmsWithdrawFeesParamsBuilder;
import exchange.notbank.fee.paramBuilders.GetOrderFeeParamBuilder;
import exchange.notbank.fee.paramBuilders.GetWithdrawFeeParamBuilder;
import exchange.notbank.fee.responses.AccountFee;
import exchange.notbank.fee.responses.DepositFee;
import exchange.notbank.fee.responses.OrderFee;
import exchange.notbank.fee.responses.TransactionFee;
import exchange.notbank.fee.responses.WithdrawFee;

import io.vavr.control.Either;

public class FeeService {
  private final Supplier<CompletableFuture<NotbankConnection>> getNotbankConnection;
  private final FeeServiceResponseAdapter responseAdapter;

  public FeeService(Supplier<CompletableFuture<NotbankConnection>> getNotbankConnection,
      FeeServiceResponseAdapter responseAdapter) {
    this.getNotbankConnection = getNotbankConnection;
    this.responseAdapter = responseAdapter;
  }

  private <T> CompletableFuture<T> requestPost(String endpoint, ParamBuilder paramBuilder,
      Function<String, Either<NotbankException, T>> deserializeFn) {
    return getNotbankConnection.get()
        .thenCompose(
            connection -> connection.requestPost(EndpointCategory.AP, endpoint, paramBuilder, deserializeFn));
  }

  /**
   * https://apidoc.notbank.exchange/#getomsdepositfees
   */
  public CompletableFuture<List<TransactionFee>> getOmsDepositFees(GetOmsDepositFeesParamsBuilder paramBuilder) {
    return requestPost(Endpoints.GET_OMS_DEPOSIT_FEES, paramBuilder, responseAdapter::toTransactionFeeList);
  }

  /**
   * https://apidoc.notbank.exchange/#getomswithdrawfees
   */
  public CompletableFuture<List<TransactionFee>> getOmsWithdrawtFees(GetOmsWithdrawFeesParamsBuilder paramBuilder) {
    return requestPost(Endpoints.GET_OMS_WITHDRAW_FEES, paramBuilder, responseAdapter::toTransactionFeeList);
  }

  /**
   * https://apidoc.notbank.exchange/#getaccountfees
   */
  public CompletableFuture<List<AccountFee>> getAccountFees(GetAccountFeesParamBuilder paramBuilder) {
    return requestPost(Endpoints.GET_ACCOUNT_FEES, paramBuilder, responseAdapter::toAccountFeeList);
  }

  /**
   * https://apidoc.notbank.exchange/#getdepositfee
   */
  public CompletableFuture<DepositFee> getDepositFee(GetDepositFeeParamBuilder paramBuilder) {
    return requestPost(Endpoints.GET_DEPOSIT_FEE, paramBuilder, responseAdapter::toDepositFee);
  }

  /**
   * https://apidoc.notbank.exchange/#getwithdrawfee
   */
  public CompletableFuture<WithdrawFee> getWithdrawFee(GetWithdrawFeeParamBuilder paramBuilder) {
    return requestPost(Endpoints.GET_WITHDRAW_FEE, paramBuilder, responseAdapter::toWithdrawFee);
  }

  /**
   * https://apidoc.notbank.exchange/#getorderfee
   */
  public CompletableFuture<OrderFee> getOrderFee(GetOrderFeeParamBuilder paramBuilder) {
    return requestPost(Endpoints.GET_ORDER_FEE, paramBuilder, responseAdapter::toOrderFee);
  }
}
