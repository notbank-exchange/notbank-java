package exchange.notbank.subscription.paramBuilders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import exchange.notbank.account.responses.AccountInfo;
import exchange.notbank.account.responses.Transaction;
import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.NotbankException;
import exchange.notbank.core.ParamBuilder;
import exchange.notbank.core.websocket.callback.subscription.SubscriptionHandler;
import exchange.notbank.core.websocket.callback.subscription.SubscriptionIdMaker;
import exchange.notbank.subscription.SubscriptionResponseAdapter;
import exchange.notbank.subscription.constants.AccountEventNames;
import exchange.notbank.subscription.responses.CancelOrderRejectEvent;
import exchange.notbank.subscription.responses.DepositEvent;
import exchange.notbank.subscription.responses.DepositTicket;
import exchange.notbank.subscription.responses.WithdrawTicket;
import exchange.notbank.trading.responses.Balance;
import exchange.notbank.trading.responses.Order;
import exchange.notbank.trading.responses.Trade;
import io.vavr.Tuple2;
import io.vavr.control.Either;

public class SubscribeAccountEventsParamBuilder implements ParamBuilder {
  private final Map<String, Object> params;
  private final Integer accountId;
  private HttpConfiguration httpConfiguration;
  private Optional<Tuple2<String, Consumer<WithdrawTicket>>> withdrawTicketHandler;
  private Optional<Tuple2<String, Consumer<Transaction>>> transactionHandler;
  private Optional<Tuple2<String, Consumer<Trade>>> tradeHandler;
  private Optional<Tuple2<String, Consumer<Order>>> orderHandler;
  private Optional<Tuple2<String, Consumer<DepositTicket>>> depositTicketHandler;
  private Optional<Tuple2<String, Consumer<AccountInfo>>> accountInfoHandler;
  private Optional<Tuple2<String, Consumer<Balance>>> balanceHandler;
  private Optional<Tuple2<String, Consumer<DepositEvent>>> depositEventHandler;
  private Optional<Tuple2<String, Consumer<CancelOrderRejectEvent>>> cancelOrderRejectEventHandler;

  public SubscribeAccountEventsParamBuilder(Integer accountId) {
    this.httpConfiguration = HttpConfiguration.empty();
    this.params = new HashMap<>();
    this.params.put("OMSId", 1);
    this.params.put("AccountId", accountId);
    this.accountId = accountId;
    this.withdrawTicketHandler = Optional.empty();
    this.transactionHandler = Optional.empty();
    this.tradeHandler = Optional.empty();
    this.orderHandler = Optional.empty();
    this.depositTicketHandler = Optional.empty();
    this.accountInfoHandler = Optional.empty();
    this.balanceHandler = Optional.empty();
    this.depositEventHandler = Optional.empty();
    this.cancelOrderRejectEventHandler = Optional.empty();
  }

  public SubscribeAccountEventsParamBuilder withdrawTicketHandler(
      Consumer<WithdrawTicket> handler) {
    this.withdrawTicketHandler = Optional.of(new Tuple2<>(AccountEventNames.WITHDRAW_TICKET_UPDATE_EVENT, handler));
    return this;
  }

  public SubscribeAccountEventsParamBuilder transactionHandler(Consumer<Transaction> handler) {
    this.transactionHandler = Optional.of(new Tuple2<>(AccountEventNames.TRANSACTION_EVENT, handler));
    return this;
  }

  public SubscribeAccountEventsParamBuilder tradeHandler(Consumer<Trade> handler) {
    this.tradeHandler = Optional.of(new Tuple2<>(AccountEventNames.ORDER_TRADE_EVENT, handler));
    return this;
  }

  public SubscribeAccountEventsParamBuilder orderHandler(Consumer<Order> handler) {
    this.orderHandler = Optional.of(new Tuple2<>(AccountEventNames.ORDER_STATE_EVENT, handler));
    return this;
  }

  public SubscribeAccountEventsParamBuilder depositTicketHandler(Consumer<DepositTicket> handler) {
    this.depositTicketHandler = Optional
        .of(new Tuple2<>(AccountEventNames.DEPOSIT_TICKET_UPDATE_EVENT, handler));
    return this;
  }

  public SubscribeAccountEventsParamBuilder accountInfoHandler(Consumer<AccountInfo> handler) {
    this.accountInfoHandler = Optional
        .of(new Tuple2<>(AccountEventNames.ACCOUNT_INFO_UPDATE_EVENT, handler));
    return this;
  }

  public SubscribeAccountEventsParamBuilder depositHandler(Consumer<DepositEvent> handler) {
    this.depositEventHandler = Optional
        .of(new Tuple2<>(AccountEventNames.DEPOSIT_EVENT, handler));
    return this;
  }

  public SubscribeAccountEventsParamBuilder cancelOrderReject(
      Consumer<CancelOrderRejectEvent> handler) {
    this.cancelOrderRejectEventHandler = Optional
        .of(new Tuple2<>(AccountEventNames.CANCEL_ORDER_REJECT_EVENT, handler));
    return this;
  }

  public SubscribeAccountEventsParamBuilder balanceHandler(Consumer<Balance> handler) {
    this.balanceHandler = Optional.of(new Tuple2<>(AccountEventNames.ACCOUNT_POSITION_EVENT, handler));
    return this;
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public SubscribeAccountEventsParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }

  public List<SubscriptionHandler> getHandlers(SubscriptionResponseAdapter responseAdapter) {
    return List.of(
        toHandler(withdrawTicketHandler, responseAdapter::toWithdrawTicket),
        toHandler(transactionHandler, responseAdapter::toTransaction),
        toHandler(tradeHandler, responseAdapter::toTrade),
        toHandler(orderHandler, responseAdapter::toOrder),
        toHandler(depositTicketHandler, responseAdapter::toDepositTicket),
        toHandler(accountInfoHandler, responseAdapter::toAccountInfo),
        toHandler(balanceHandler, responseAdapter::toBalance),
        toHandler(depositEventHandler, responseAdapter::toDepositEvent),
        toHandler(cancelOrderRejectEventHandler, responseAdapter::toCancelOrderRejectEvent))
        .stream().flatMap(Optional::stream).toList();
  }

  <T> Optional<SubscriptionHandler> toHandler(
      Optional<Tuple2<String, Consumer<T>>> optNamedHandler,
      Function<String, Either<NotbankException, T>> parser) {
    if (optNamedHandler.isEmpty()) {
      return Optional.empty();
    }
    var handler = SubscriptionHandler.Factory.create(
        SubscriptionIdMaker.get(optNamedHandler.get()._1(), accountId),
        parser,
        optNamedHandler.get()._2);
    return Optional.of(handler);
  }
}
