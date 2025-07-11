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
import exchange.notbank.core.websocket.AccountEventPayload;
import exchange.notbank.core.websocket.SubscriptionHandler;
import exchange.notbank.subscription.SubscriptionResponseAdapter;
import exchange.notbank.subscription.constants.AccountEventNames;
import exchange.notbank.subscription.responses.CancelOrderRejectEvent;
import exchange.notbank.subscription.responses.DepositEvent;
import exchange.notbank.subscription.responses.DepositTicket;
import exchange.notbank.subscription.responses.WithdrawTicket;
import exchange.notbank.trading.responses.Balance;
import exchange.notbank.trading.responses.Order;
import exchange.notbank.trading.responses.Trade;

import io.vavr.control.Either;

public class SubscribeAccountEventsParamBuilder implements ParamBuilder {
  private final Map<String, Object> params;
  private HttpConfiguration httpConfiguration;
  private Optional<SubscriptionHandler<WithdrawTicket>> withdrawTicketHandler;
  private Optional<SubscriptionHandler<Transaction>> transactionHandler;
  private Optional<SubscriptionHandler<Trade>> tradeHandler;
  private Optional<SubscriptionHandler<Order>> orderHandler;
  private Optional<SubscriptionHandler<DepositTicket>> depositTicketHandler;
  private Optional<SubscriptionHandler<AccountInfo>> accountInfoHandler;
  private Optional<SubscriptionHandler<Balance>> balanceHandler;
  private Optional<SubscriptionHandler<DepositEvent>> depositEventHandler;
  private Optional<SubscriptionHandler<CancelOrderRejectEvent>> cancelOrderRejectEventHandler;

  public SubscribeAccountEventsParamBuilder(Integer accountId) {
    this.httpConfiguration = HttpConfiguration.empty();
    this.params = new HashMap<>();
    this.params.put("OMSId", 1);
    this.params.put("AccountId", accountId);
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
    this.withdrawTicketHandler = Optional
        .of(new SubscriptionHandler<>(AccountEventNames.WITHDRAW_TICKET_UPDATE_EVENT, handler));
    return this;
  }

  public SubscribeAccountEventsParamBuilder transactionHandler(Consumer<Transaction> handler) {
    this.transactionHandler = Optional.of(new SubscriptionHandler<>(AccountEventNames.TRANSACTION_EVENT, handler));
    return this;
  }

  public SubscribeAccountEventsParamBuilder tradeHandler(Consumer<Trade> handler) {
    this.tradeHandler = Optional.of(new SubscriptionHandler<>(AccountEventNames.ORDER_TRADE_EVENT, handler));
    return this;
  }

  public SubscribeAccountEventsParamBuilder orderHandler(Consumer<Order> handler) {
    this.orderHandler = Optional.of(new SubscriptionHandler<>(AccountEventNames.ORDER_STATE_EVENT, handler));
    return this;
  }

  public SubscribeAccountEventsParamBuilder depositTicketHandler(Consumer<DepositTicket> handler) {
    this.depositTicketHandler = Optional
        .of(new SubscriptionHandler<>(AccountEventNames.DEPOSIT_TICKET_UPDATE_EVENT, handler));
    return this;
  }

  public SubscribeAccountEventsParamBuilder accountInfoHandler(Consumer<AccountInfo> handler) {
    this.accountInfoHandler = Optional
        .of(new SubscriptionHandler<>(AccountEventNames.ACCOUNT_INFO_UPDATE_EVENT, handler));
    return this;
  }

  public SubscribeAccountEventsParamBuilder depositHandler(Consumer<DepositEvent> handler) {
    this.depositEventHandler = Optional
        .of(new SubscriptionHandler<>(AccountEventNames.DEPOSIT_EVENT, handler));
    return this;
  }

  public SubscribeAccountEventsParamBuilder cancelOrderReject(
      Consumer<CancelOrderRejectEvent> handler) {
    this.cancelOrderRejectEventHandler = Optional
        .of(new SubscriptionHandler<>(AccountEventNames.CANCEL_ORDER_REJECT_EVENT, handler));
    return this;
  }

  public SubscribeAccountEventsParamBuilder balanceHandler(Consumer<Balance> handler) {
    this.balanceHandler = Optional.of(new SubscriptionHandler<>(AccountEventNames.ACCOUNT_POSITION_EVENT, handler));
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

  public List<SubscriptionHandler<AccountEventPayload>> getSubscriptions(
      SubscriptionResponseAdapter responseAdapter,
      Consumer<Throwable> onError) {
    return List.of(
        toHandler(withdrawTicketHandler, responseAdapter::toWithdrawTicket, onError),
        toHandler(transactionHandler, responseAdapter::toTransaction, onError),
        toHandler(tradeHandler, responseAdapter::toTrade, onError),
        toHandler(orderHandler, responseAdapter::toOrder, onError),
        toHandler(depositTicketHandler, responseAdapter::toDepositTicket, onError),
        toHandler(accountInfoHandler, responseAdapter::toAccountInfo, onError),
        toHandler(balanceHandler, responseAdapter::toBalance, onError),
        toHandler(depositEventHandler, responseAdapter::toDepositEvent, onError),
        toHandler(cancelOrderRejectEventHandler, responseAdapter::toCancelOrderRejectEvent, onError))
        .stream().flatMap(Optional::stream).toList();
  }

  <T> Optional<SubscriptionHandler<AccountEventPayload>> toHandler(
      Optional<SubscriptionHandler<T>> optNamedHandler,
      Function<String, Either<NotbankException, T>> parser,
      Consumer<Throwable> onError) {
    return optNamedHandler
        .map(namedHandler -> new SubscriptionHandler<>(namedHandler.eventName,
            accountEventPayload -> {
              var jsonData = parser.apply(accountEventPayload.getPayload());
              if (jsonData.isLeft()) {
                onError.accept(jsonData.getLeft());
                return;
              }
              namedHandler.eventHandler.accept(jsonData.get());
            }));
  }
}
