package exchange.notbank.trading;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import exchange.notbank.core.ErrorHandler;
import exchange.notbank.core.NotbankException;
import exchange.notbank.trading.responses.Balance;
import exchange.notbank.trading.responses.CancelAllOrdersResponse;
import exchange.notbank.trading.responses.CancelReplaceOrderResponse;
import exchange.notbank.trading.responses.DailyTicker;
import exchange.notbank.trading.responses.EarliestTickTime;
import exchange.notbank.trading.responses.LastTrade;
import exchange.notbank.trading.responses.Level;
import exchange.notbank.trading.responses.Level1;
import exchange.notbank.trading.responses.Level2Snapshot;
import exchange.notbank.trading.responses.Level2Ticker;
import exchange.notbank.trading.responses.ModifyOrderResponse;
import exchange.notbank.trading.responses.Order;
import exchange.notbank.trading.responses.OrderBook;
import exchange.notbank.trading.responses.OrderBookRaw;
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

public class TradingServiceResponseAdapter {
  private final ErrorHandler errorHandler;
  private final JsonAdapter<List<Balance>> balanceListJsonAdapter;
  private final JsonAdapter<List<Order>> orderListJsonAdapter;
  private final JsonAdapter<Level2Snapshot> level2SnapshotJsonAdapter;
  private final JsonAdapter<SimpleUserAccounts> simpleUserAccountListJsonAdapter;
  private final JsonAdapter<List<String>> stringListJsonAdapter;
  private final JsonAdapter<Level1> level1JsonAdapter;
  private final JsonAdapter<List<Level1>> level1ListJsonAdapter;
  private final JsonAdapter<List<Level2Ticker>> level2ListJsonAdapter;
  private final JsonAdapter<List<Trade>> tradeListJsonAdapter;
  private final JsonAdapter<PublicTrade> publicTradeJsonAdapter;
  private final JsonAdapter<List<PublicTrade>> publicTradeListJsonAdapter;
  private final JsonAdapter<Ticker> tickerJsonAdapter;
  private final JsonAdapter<List<Ticker>> tickerListJsonAdapter;
  private final JsonAdapter<List<SummaryMin>> summaryMinListJsonAdapter;
  private final JsonAdapter<List<Summary>> summaryListJsonAdapter;
  private final JsonAdapter<EarliestTickTime> earliestTickTimeJsonAdapter;
  private final JsonAdapter<List<LastTrade>> lastTradeListJsonAdapter;
  private final JsonAdapter<DailyTicker> dailyTickerJsonAdapter;
  private final JsonAdapter<OrderBookRaw> orderBookRawJsonAdapter;
  private final JsonAdapter<Order> orderJsonAdapter;
  private final JsonAdapter<SendOrderResponse> sendOrderJsonAdapter;
  private final JsonAdapter<SendOrderListResponse> sendOrderListJsonAdapter;
  private final JsonAdapter<CancelReplaceOrderResponse> cancelReplaceOrderResponseJsonAdapter;
  private final JsonAdapter<SendCancelListResponse> sendCancelListResponseJsonAdapter;
  private final JsonAdapter<SendCancelReplaceListResponse> sendCancelReplaceListResponseJsonAdapter;
  private final JsonAdapter<ModifyOrderResponse> modifyOrderResponseJsonAdapter;
  private final JsonAdapter<CancelAllOrdersResponse> cancelAllOrdersResponseJsonAdapter;

  public TradingServiceResponseAdapter(Moshi moshi) {
    this.errorHandler = ErrorHandler.Factory.createApErrorHandler(moshi);
    ParameterizedType BalanceListType = Types.newParameterizedType(List.class, Balance.class);
    this.balanceListJsonAdapter = moshi.adapter(BalanceListType);
    ParameterizedType TradeListType = Types.newParameterizedType(List.class, Trade.class);
    this.tradeListJsonAdapter = moshi.adapter(TradeListType);
    ParameterizedType OrderListType = Types.newParameterizedType(List.class, Order.class);
    this.orderListJsonAdapter = moshi.adapter(OrderListType);
    this.level2SnapshotJsonAdapter = moshi.adapter(Level2Snapshot.class);
    this.simpleUserAccountListJsonAdapter = moshi.adapter(SimpleUserAccounts.class);
    ParameterizedType SummaryMinListType = Types.newParameterizedType(List.class, SummaryMin.class);
    this.summaryMinListJsonAdapter = moshi.adapter(SummaryMinListType);
    ParameterizedType SummaryListType = Types.newParameterizedType(List.class, SummaryMin.class);
    this.summaryListJsonAdapter = moshi.adapter(SummaryListType);
    ParameterizedType StringListType = Types.newParameterizedType(List.class, String.class);
    this.stringListJsonAdapter = moshi.adapter(StringListType);
    this.level1JsonAdapter = moshi.adapter(Level1.class);
    ParameterizedType Level1ListType = Types.newParameterizedType(List.class, Level1.class);
    this.level1ListJsonAdapter = moshi.adapter(Level1ListType);
    ParameterizedType Level2ListType = Types.newParameterizedType(List.class, Level2Ticker.class);
    this.level2ListJsonAdapter = moshi.adapter(Level2ListType);
    this.publicTradeJsonAdapter = moshi.adapter(PublicTrade.class);
    ParameterizedType PublicTradeListType = Types.newParameterizedType(List.class, PublicTrade.class);
    this.publicTradeListJsonAdapter = moshi.adapter(PublicTradeListType);
    this.tickerJsonAdapter = moshi.adapter(Ticker.class);
    ParameterizedType TickerListType = Types.newParameterizedType(List.class, Ticker.class);
    this.tickerListJsonAdapter = moshi.adapter(TickerListType);
    this.earliestTickTimeJsonAdapter = moshi.adapter(EarliestTickTime.class);
    ParameterizedType LastTradeListType = Types.newParameterizedType(List.class, LastTrade.class);
    this.lastTradeListJsonAdapter = moshi.adapter(LastTradeListType);
    this.dailyTickerJsonAdapter = moshi.adapter(DailyTicker.class);

    this.orderBookRawJsonAdapter = moshi.adapter(OrderBookRaw.class);
    this.orderJsonAdapter = moshi.adapter(Order.class);
    this.sendOrderJsonAdapter = moshi.adapter(SendOrderResponse.class);
    this.sendOrderListJsonAdapter = moshi.adapter(SendOrderListResponse.class);
    this.cancelReplaceOrderResponseJsonAdapter = moshi.adapter(CancelReplaceOrderResponse.class);
    this.sendCancelListResponseJsonAdapter = moshi.adapter(SendCancelListResponse.class);
    this.sendCancelReplaceListResponseJsonAdapter = moshi.adapter(SendCancelReplaceListResponse.class);
    this.modifyOrderResponseJsonAdapter = moshi.adapter(ModifyOrderResponse.class);
    this.cancelAllOrdersResponseJsonAdapter = moshi.adapter(CancelAllOrdersResponse.class);
  }

  public Either<NotbankException, Void> toNone(String jsonStr) {
    return errorHandler.toNone(jsonStr);
  }

  private <T> Either<NotbankException, T> handle(String jsonString, JsonAdapter<T> jsonAdapter) {
    return errorHandler.handleAndThen(jsonAdapter).apply(jsonString);
  }

  public Either<NotbankException, List<Balance>> toBalanceList(String jsonStr) {
    return handle(jsonStr, balanceListJsonAdapter);
  }

  public Either<NotbankException, List<Trade>> toTradeList(String jsonStr) {
    return handle(jsonStr, tradeListJsonAdapter);
  }

  public Either<NotbankException, List<Order>> toOrderList(String jsonStr) {
    return handle(jsonStr, orderListJsonAdapter);
  }

  public Either<NotbankException, Level2Snapshot> toLevel2Snapshot(String jsonStr) {
    return handle(jsonStr, level2SnapshotJsonAdapter);
  }

  public Either<NotbankException, List<SummaryMin>> toSummaryMinList(String jsonStr) {
    return handle(jsonStr, summaryMinListJsonAdapter);
  }

  public Either<NotbankException, List<Summary>> toSummaryList(String jsonStr) {
    return handle(jsonStr, summaryListJsonAdapter);
  }

  public Either<NotbankException, SimpleUserAccounts> toSimpleAccountList(String jsonStr) {
    return handle(jsonStr, simpleUserAccountListJsonAdapter);
  }

  public Either<NotbankException, List<String>> toStringList(String jsonStr) {
    return handle(jsonStr, stringListJsonAdapter);
  }

  public Either<NotbankException, Level1> toLevel1(String jsonStr) {
    return handle(jsonStr, level1JsonAdapter);
  }

  public Either<NotbankException, List<Level1>> toLevel1List(String jsonStr) {
    return handle(jsonStr, level1ListJsonAdapter);
  }

  public Either<NotbankException, List<Level1>> toLevel1Summary(String jsonStr) {
    var stringList = handle(jsonStr, stringListJsonAdapter);
    if (stringList.isLeft()) {
      return Either.left(stringList.getLeft());
    }

    List<NotbankException> errors = new ArrayList<>();
    List<Level1> result = stringList.get().stream().map(level1String -> {
      var level1 = this.toLevel1(level1String);
      if (level1.isLeft()) {
        errors.add(level1.getLeft());
      }
      return level1.get();
    }).toList();

    if (!errors.isEmpty()) {
      return Either.left(errors.get(0));
    }

    return Either.right(result);
  }

  public Either<NotbankException, List<Level2Ticker>> toLevel2List(String jsonStr) {
    return handle(jsonStr, level2ListJsonAdapter);
  }

  public Either<NotbankException, PublicTrade> toPublicTrade(String jsonStr) {
    return handle(jsonStr, publicTradeJsonAdapter);
  }

  public Either<NotbankException, List<PublicTrade>> toPublicTradeList(String jsonStr) {
    return handle(jsonStr, publicTradeListJsonAdapter);
  }

  public Either<NotbankException, Ticker> toTicker(String jsonStr) {
    return handle(jsonStr, tickerJsonAdapter);
  }

  public Either<NotbankException, List<Ticker>> toTickerList(String jsonStr) {
    return handle(jsonStr, tickerListJsonAdapter);
  }

  public Either<NotbankException, EarliestTickTime> toEarliestTickTime(String jsonStr) {
    return handle(jsonStr, earliestTickTimeJsonAdapter);
  }

  public Either<NotbankException, List<LastTrade>> toLastTradeList(String jsonStr) {
    return handle(jsonStr, lastTradeListJsonAdapter);
  }

  public Either<NotbankException, DailyTicker> toDailyTicker(String jsonStr) {
    return handle(jsonStr, dailyTickerJsonAdapter);
  }

  public Either<NotbankException, OrderBook> toOrderBook(String jsonStr) {
    var orderbookRaw = handle(jsonStr, orderBookRawJsonAdapter);
    return orderbookRaw.map(orderbook -> new OrderBook(
        orderbook.timestamp,
        orderbook.bids.stream().map(bid -> new Level(bid.get(0), bid.get(1))).toList(),
        orderbook.asks.stream().map(ask -> new Level(ask.get(0), ask.get(1))).toList()));

  }

  public Either<NotbankException, Order> toOrder(String jsonStr) {
    return handle(jsonStr, orderJsonAdapter);
  }

  public Either<NotbankException, SendOrderResponse> toSendOrderResponse(String jsonStr) {
    return handle(jsonStr, sendOrderJsonAdapter);
  }

  public Either<NotbankException, SendOrderListResponse> toSendOrderListResponse(String jsonStr) {
    return handle(jsonStr, sendOrderListJsonAdapter);
  }

  public Either<NotbankException, CancelReplaceOrderResponse> toCancelReplaceOrderResponse(String jsonStr) {
    return handle(jsonStr, cancelReplaceOrderResponseJsonAdapter);
  }

  public Either<NotbankException, SendCancelListResponse> toSendCancelListResponse(String jsonStr) {
    return handle(jsonStr, sendCancelListResponseJsonAdapter);
  }

  public Either<NotbankException, SendCancelReplaceListResponse> toSendCancelReplaceListResponse(String jsonStr) {
    return handle(jsonStr, sendCancelReplaceListResponseJsonAdapter);
  }

  public Either<NotbankException, ModifyOrderResponse> toModifyOrderResponse(String jsonStr) {
    return handle(jsonStr, modifyOrderResponseJsonAdapter);
  }

  public Either<NotbankException, CancelAllOrdersResponse> toCancelAllOrdersResponse(String jsonStr) {
    return handle(jsonStr, cancelAllOrdersResponseJsonAdapter);
  }
}
