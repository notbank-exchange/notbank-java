package exchange.notbank.subscription;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import exchange.notbank.account.responses.AccountInfo;
import exchange.notbank.account.responses.Transaction;
import exchange.notbank.core.ErrorHandler;
import exchange.notbank.core.NotbankException;
import exchange.notbank.core.StandardApResponseHandler;
import exchange.notbank.subscription.responses.CancelOrderRejectEvent;
import exchange.notbank.subscription.responses.DepositEvent;
import exchange.notbank.subscription.responses.DepositTicket;
import exchange.notbank.subscription.responses.Level2;
import exchange.notbank.subscription.responses.SocketTrade;
import exchange.notbank.subscription.responses.WithdrawTicket;
import exchange.notbank.trading.responses.Balance;
import exchange.notbank.trading.responses.Level1;
import exchange.notbank.trading.responses.Order;
import exchange.notbank.trading.responses.Ticker;
import exchange.notbank.trading.responses.Trade;
import io.vavr.control.Either;

public class SubscriptionResponseAdapter {
  private final ErrorHandler errorHandler;
  private final JsonAdapter<Transaction> transactionJsonAdapter;
  private final JsonAdapter<AccountInfo> accountInfoJsonAdapter;
  private final JsonAdapter<WithdrawTicket> withdrawTicketJsonAdapter;
  private final JsonAdapter<DepositTicket> depositTicketJsonAdapter;
  private final JsonAdapter<Balance> balanceJsonAdapter;
  private final JsonAdapter<Order> orderJsonAdapter;
  private final JsonAdapter<Trade> tradeJsonAdapter;
  private final JsonAdapter<DepositEvent> depositEventJsonAdapter;
  private final JsonAdapter<CancelOrderRejectEvent> cancelOrderRejectEventJsonAdapter;
  private final JsonAdapter<List<Ticker>> tickerListAdapter;
  private final JsonAdapter<Level1> level1TickerAdapter;
  private final JsonAdapter<List<Level2>> level2TickerListAdapter;
  private final JsonAdapter<List<SocketTrade>> socketTradeListAdapter;

  public SubscriptionResponseAdapter(StandardApResponseHandler responseHandler, Moshi moshi) {
    this.errorHandler = ErrorHandler.Factory.createApErrorHandler(moshi);
    this.transactionJsonAdapter = moshi.adapter(Transaction.class);
    this.accountInfoJsonAdapter = moshi.adapter(AccountInfo.class);
    this.withdrawTicketJsonAdapter = moshi.adapter(WithdrawTicket.class);
    this.depositTicketJsonAdapter = moshi.adapter(DepositTicket.class);
    this.balanceJsonAdapter = moshi.adapter(Balance.class);
    this.orderJsonAdapter = moshi.adapter(Order.class);
    this.tradeJsonAdapter = moshi.adapter(Trade.class);
    this.depositEventJsonAdapter = moshi.adapter(DepositEvent.class);
    this.cancelOrderRejectEventJsonAdapter = moshi.adapter(CancelOrderRejectEvent.class);
    ParameterizedType TickerListType = Types.newParameterizedType(List.class, Ticker.class);
    this.tickerListAdapter = moshi.adapter(TickerListType);
    this.level1TickerAdapter = moshi.adapter(Level1.class);
    ParameterizedType Level2TickerListType = Types.newParameterizedType(List.class, Level2.class);
    this.level2TickerListAdapter = moshi.adapter(Level2TickerListType);
    ParameterizedType SocketTradeListType = Types.newParameterizedType(List.class, SocketTrade.class);
    this.socketTradeListAdapter = moshi.adapter(SocketTradeListType);
  }

  public Either<NotbankException, Void> toNone(String jsonStr) {
    return errorHandler.toNone(jsonStr);
  }

  private <T> Either<NotbankException, T> handle(String jsonString, JsonAdapter<T> jsonAdapter) {
    return errorHandler.handleAndThen(jsonAdapter).apply(jsonString);
  }

  public Either<NotbankException, Transaction> toTransaction(String jsonStr) {
    return handle(jsonStr, transactionJsonAdapter);
  }

  public Either<NotbankException, AccountInfo> toAccountInfo(String jsonStr) {
    return handle(jsonStr, accountInfoJsonAdapter);
  }

  public Either<NotbankException, WithdrawTicket> toWithdrawTicket(String jsonStr) {
    return handle(jsonStr, withdrawTicketJsonAdapter);
  }

  public Either<NotbankException, DepositTicket> toDepositTicket(String jsonStr) {
    return handle(jsonStr, depositTicketJsonAdapter);
  }

  public Either<NotbankException, Balance> toBalance(String jsonStr) {
    return handle(jsonStr, balanceJsonAdapter);
  }

  public Either<NotbankException, Order> toOrder(String jsonStr) {
    return handle(jsonStr, orderJsonAdapter);
  }

  public Either<NotbankException, Trade> toTrade(String jsonStr) {
    return handle(jsonStr, tradeJsonAdapter);
  }

  public Either<NotbankException, DepositEvent> toDepositEvent(String jsonStr) {
    return handle(jsonStr, depositEventJsonAdapter);
  }

  public Either<NotbankException, CancelOrderRejectEvent> toCancelOrderRejectEvent(String jsonStr) {
    return handle(jsonStr, cancelOrderRejectEventJsonAdapter);
  }

  public Either<NotbankException, List<Ticker>> toTickerList(String jsonStr) {
    return handle(jsonStr, tickerListAdapter);
  }

  public Either<NotbankException, Level1> toLevel1(String jsonStr) {
    return handle(jsonStr, level1TickerAdapter);
  }

  public Either<NotbankException, List<Level2>> toLevel2List(String jsonStr) {
    return handle(jsonStr, level2TickerListAdapter);
  }

  public Either<NotbankException, List<SocketTrade>> toSocketTradeList(String jsonStr) {
    return handle(jsonStr, socketTradeListAdapter);
  }
}