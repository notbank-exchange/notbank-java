package exchange.notbank.trading;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;

import exchange.notbank.core.EndpointCategory;
import exchange.notbank.core.NotbankConnection;
import exchange.notbank.core.NotbankException;
import exchange.notbank.core.ParamBuilder;
import exchange.notbank.core.ParamListBuilder;
import exchange.notbank.trading.constants.Endpoints;
import exchange.notbank.trading.paramBuilders.CancelAllOrdersParamBuilder;
import exchange.notbank.trading.paramBuilders.CancelOrderParamBuilder;
import exchange.notbank.trading.paramBuilders.CancelReplaceOrderParamBuilder;
import exchange.notbank.trading.paramBuilders.GetLastTradesParamBuilder;
import exchange.notbank.trading.paramBuilders.GetLevel1ParamBuilder;
import exchange.notbank.trading.paramBuilders.GetLevel1SummaryParamBuilder;
import exchange.notbank.trading.paramBuilders.GetLevel2SnapshotParamBuilder;
import exchange.notbank.trading.paramBuilders.GetLevelSummaryParamBuilder;
import exchange.notbank.trading.paramBuilders.GetOpenOrdersParamBuilder;
import exchange.notbank.trading.paramBuilders.GetOpenTradeReportParamBuilder;
import exchange.notbank.trading.paramBuilders.GetOrderBookParamBuilder;
import exchange.notbank.trading.paramBuilders.GetOrderHistoryParamBuilder;
import exchange.notbank.trading.paramBuilders.GetOrderStatusParamBuilder;
import exchange.notbank.trading.paramBuilders.GetOrdersHistoryByOrderIdParamBuilder;
import exchange.notbank.trading.paramBuilders.GetOrdersHistoryParamBuilder;
import exchange.notbank.trading.paramBuilders.GetOrdersParamBuilder;
import exchange.notbank.trading.paramBuilders.GetUserAccountsParamBuilder;
import exchange.notbank.trading.paramBuilders.GetTickerHistoryParamBuilder;
import exchange.notbank.trading.paramBuilders.GetTradesHistoryParamBuilder;
import exchange.notbank.trading.paramBuilders.ModifyOrderParamBuilder;
import exchange.notbank.trading.paramBuilders.SendCancelListParamBuilder;
import exchange.notbank.trading.paramBuilders.SendCancelReplaceListParamBuilder;
import exchange.notbank.trading.paramBuilders.SendOrderListParamBuilder;
import exchange.notbank.trading.paramBuilders.SendOrderParamBuilder;
import exchange.notbank.trading.paramBuilders.SummaryParamBuilder;
import exchange.notbank.trading.paramBuilders.TradesParamBuilder;
import exchange.notbank.trading.responses.CancelAllOrdersResponse;
import exchange.notbank.trading.responses.CancelReplaceOrderResponse;
import exchange.notbank.trading.responses.DailyTicker;
import exchange.notbank.trading.responses.LastTrade;
import exchange.notbank.trading.responses.Level1Ticker;
import exchange.notbank.trading.responses.Level2Snapshot;
import exchange.notbank.trading.responses.ModifyOrderResponse;
import exchange.notbank.trading.responses.Order;
import exchange.notbank.trading.responses.OrderBook;
import exchange.notbank.trading.responses.PublicTrade;
import exchange.notbank.trading.responses.SendCancelListResponse;
import exchange.notbank.trading.responses.SendCancelReplaceListResponse;
import exchange.notbank.trading.responses.SendOrderListResponse;
import exchange.notbank.trading.responses.SendOrderResponse;
import exchange.notbank.trading.responses.SimpleUserAccounts;
import exchange.notbank.trading.responses.Summary;
import exchange.notbank.trading.responses.SummaryMin;
import exchange.notbank.trading.responses.Ticker;
import exchange.notbank.trading.responses.Trade;

import io.vavr.control.Either;

public class TradingService {
  private final Supplier<CompletableFuture<NotbankConnection>> getNotbankConnection;
  private final TradingServiceResponseAdapter responseAdapter;

  public TradingService(Supplier<CompletableFuture<NotbankConnection>> getNotbankConnection,
      TradingServiceResponseAdapter responseAdapter) {
    this.getNotbankConnection = getNotbankConnection;
    this.responseAdapter = responseAdapter;
  }

  private <T> CompletableFuture<T> requestPost(String endpoint, ParamBuilder paramBuilder,
      Function<String, Either<NotbankException, T>> deserializeFn) {
    return getNotbankConnection.get()
        .thenCompose(connection -> connection.requestPost(EndpointCategory.AP, endpoint, paramBuilder, deserializeFn));
  }

  private <T> CompletableFuture<T> requestPostWithParamList(String endpoint, ParamListBuilder paramBuilder,
      Function<String, Either<NotbankException, T>> deserializeFn) {
    return getNotbankConnection.get()
        .thenCompose(connection -> connection.requestPost(EndpointCategory.AP, endpoint, paramBuilder, deserializeFn));
  }

  /**
   * https://apidoc.notbank.exchange/#gettradeshistory
   */
  public CompletableFuture<List<Trade>> getTradesHistory(GetTradesHistoryParamBuilder paramBuilder) {
    return requestPost(Endpoints.GET_TRADES_HISTORY, paramBuilder, responseAdapter::toTradeList);
  }

  /**
   * https://apidoc.notbank.exchange/#getopenorders
   */
  public CompletableFuture<List<Order>> getOpenOrders(GetOpenOrdersParamBuilder paramBuilder) {
    return requestPost(Endpoints.GET_OPEN_ORDERS, paramBuilder, responseAdapter::toOrderList);
  }

  /**
   * https://apidoc.notbank.exchange/#getorders
   */
  public CompletableFuture<List<Order>> getOrders(GetOrdersParamBuilder paramBuilder) {
    return requestPost(Endpoints.GET_ORDERS, paramBuilder, responseAdapter::toOrderList);
  }

  /**
   * https://apidoc.notbank.exchange/#getopentradereports
   */
  public CompletableFuture<List<Order>> getOpenTradeReports(GetOpenTradeReportParamBuilder paramBuilder) {
    return requestPost(Endpoints.GET_OPEN_TRADE_REPORTS, paramBuilder, responseAdapter::toOrderList);
  }

  /**
   * https://apidoc.notbank.exchange/#getl2snapshot
   */
  public CompletableFuture<Level2Snapshot> getLevel2Snapshot(GetLevel2SnapshotParamBuilder paramBuilder) {
    return requestPost(Endpoints.GET_LEVEL_2_SNAPSHOT, paramBuilder, responseAdapter::toLevel2Snapshot);
  }

  /**
   * https://apidoc.notbank.exchange/#getlevel1summarymin
   */
  public CompletableFuture<List<SummaryMin>> getLevel1SummaryMin(GetLevelSummaryParamBuilder paramBuilder) {
    return requestPost(Endpoints.GET_LEVEL_1_SUMMARY_MIN, paramBuilder, responseAdapter::toSummaryMinList);
  }

  /**
   * https://apidoc.notbank.exchange/#getuseraccounts
   */
  public CompletableFuture<SimpleUserAccounts> getUserAccounts(GetUserAccountsParamBuilder paramBuilder) {
    return requestPost(Endpoints.GET_USER_ACCOUNTS, paramBuilder, responseAdapter::toSimpleAccountList);
  }

  /**
   * https://apidoc.notbank.exchange/#getlevel1
   */
  public CompletableFuture<Level1Ticker> getLevel1(GetLevel1ParamBuilder paramBuilder) {
    return requestPost(Endpoints.GET_LEVEL1, paramBuilder, this.responseAdapter::toLevel1);
  }

  /**
   * https://apidoc.notbank.exchange/#getlevel1summary
   */
  public CompletableFuture<List<Level1Ticker>> getLevel1Summary(GetLevel1SummaryParamBuilder paramBuilder) {
    return requestPost(Endpoints.GET_LEVEL1_SUMMARY, paramBuilder, this.responseAdapter::toLevel1Summary);
  }

  /**
   * https://apidoc.notbank.exchange/#trades
   */
  public CompletableFuture<List<PublicTrade>> trades(TradesParamBuilder paramBuilder) {
    return requestPost(Endpoints.TRADES, paramBuilder, this.responseAdapter::toPublicTradeList);
  }

  /**
   * https://apidoc.notbank.exchange/#summary
   */
  public CompletableFuture<List<Summary>> summary() {
    return requestPost(Endpoints.SUMMARY, new SummaryParamBuilder(), this.responseAdapter::toSummaryList);
  }

  /**
   * https://apidoc.notbank.exchange/#gettickerhistory
   */
  public CompletableFuture<List<Ticker>> getTickerHistory(GetTickerHistoryParamBuilder paramBuilder) {
    return requestPost(Endpoints.GET_TICKER_HISTORY, paramBuilder, responseAdapter::toTickerList);
  }

  /**
   * https://apidoc.notbank.exchange/#getlasttrades
   */
  public CompletableFuture<List<LastTrade>> getLastTrades(GetLastTradesParamBuilder paramBuilder) {
    return requestPost(Endpoints.GET_LAST_TRADES, paramBuilder, responseAdapter::toLastTradeList);
  }

  /**
   * https://apidoc.notbank.exchange/#ticker
   */
  public CompletableFuture<DailyTicker> getTicker() {
    return requestPost(Endpoints.TICKER, ParamBuilder.EMPTY, responseAdapter::toDailyTicker);
  }

  /**
   * https://apidoc.notbank.exchange/#orderbook
   */
  public CompletableFuture<OrderBook> getOrderbook(GetOrderBookParamBuilder paramBuilder) {
    return requestPost(Endpoints.ORDER_BOOK, paramBuilder, responseAdapter::toOrderBook);
  }

  /**
   * https://apidoc.notbank.exchange/#getordershistory
   */
  public CompletableFuture<List<Order>> getOrdersHistory(GetOrdersHistoryParamBuilder paramBuilder) {
    return requestPost(Endpoints.GET_ORDERS_HISTORY, paramBuilder, responseAdapter::toOrderList);
  }

  /**
   * https://apidoc.notbank.exchange/#getorderhistory
   */
  public CompletableFuture<List<Order>> getOrderHistory(GetOrderHistoryParamBuilder paramBuilder) {
    return requestPost(Endpoints.GET_ORDER_HISTORY, paramBuilder, responseAdapter::toOrderList);
  }

  /**
   * https://apidoc.notbank.exchange/#getorderhistorybyorderid
   */
  public CompletableFuture<List<Order>> getOrderHistoryByOrderId(GetOrdersHistoryByOrderIdParamBuilder paramBuilder) {
    return requestPost(Endpoints.GET_ORDER_HISTORY_BY_ORDER_ID, paramBuilder, responseAdapter::toOrderList);
  }

  /**
   * https://apidoc.notbank.exchange/#sendorder
   */
  public CompletableFuture<SendOrderResponse> sendOrder(SendOrderParamBuilder paramBuilder) {
    return requestPost(Endpoints.SEND_ORDER, paramBuilder, responseAdapter::toSendOrderResponse);
  }

  /**
   * https://apidoc.notbank.exchange/#sendorderlist
   */
  public CompletableFuture<SendOrderListResponse> sendOrderList(SendOrderListParamBuilder paramBuilder) {
    return requestPostWithParamList(Endpoints.SEND_ORDER_LIST, paramBuilder, responseAdapter::toSendOrderListResponse);
  }

  /**
   * https://apidoc.notbank.exchange/#cancelorder
   */
  public CompletableFuture<Void> cancelOrder(CancelOrderParamBuilder paramBuilder) {
    return requestPost(Endpoints.CANCEL_ORDER, paramBuilder, responseAdapter::toNone);
  }

  /**
   * https://apidoc.notbank.exchange/#cancelreplaceorder
   */
  public CompletableFuture<CancelReplaceOrderResponse> cancelReplaceOrder(CancelReplaceOrderParamBuilder paramBuilder) {
    return requestPost(Endpoints.CANCEL_REPLACE_ORDER, paramBuilder, responseAdapter::toCancelReplaceOrderResponse);
  }

  /**
   * https://apidoc.notbank.exchange/#sendcancellist
   */
  public CompletableFuture<SendCancelListResponse> sendCancelList(SendCancelListParamBuilder paramBuilder) {
    return requestPostWithParamList(Endpoints.SEND_CANCEL_LIST, paramBuilder,
        responseAdapter::toSendCancelListResponse);
  }

  /**
   * https://apidoc.notbank.exchange/#sendcancelreplacelist
   */
  public CompletableFuture<SendCancelReplaceListResponse> sendCancelReplaceList(
      SendCancelReplaceListParamBuilder paramBuilder) {
    return requestPostWithParamList(Endpoints.SEND_CANCEL_REPLACE_LIST, paramBuilder,
        responseAdapter::toSendCancelReplaceListResponse);
  }

  /**
   * https://apidoc.notbank.exchange/#modifyorder
   */
  public CompletableFuture<ModifyOrderResponse> modifyOrder(ModifyOrderParamBuilder paramBuilder) {
    return requestPost(Endpoints.MODIFY_ORDER, paramBuilder, responseAdapter::toModifyOrderResponse);
  }

  /**
   * https://apidoc.notbank.exchange/#cancelallorders
   */
  public CompletableFuture<CancelAllOrdersResponse> cancelAllOrders(CancelAllOrdersParamBuilder paramBuilder) {
    return requestPost(Endpoints.CANCEL_ALL_ORDERS, paramBuilder, responseAdapter::toCancelAllOrdersResponse);
  }

  /**
   * https://apidoc.notbank.exchange/#getorderstatus
   */
  public CompletableFuture<Order> getOrderStatus(GetOrderStatusParamBuilder paramBuilder) {
    return requestPost(Endpoints.GET_ORDER_STATUS, paramBuilder, responseAdapter::toOrder);
  }
}
