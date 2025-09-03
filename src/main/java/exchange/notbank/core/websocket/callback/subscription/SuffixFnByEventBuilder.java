package exchange.notbank.core.websocket.callback.subscription;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import com.squareup.moshi.JsonAdapter;

import exchange.notbank.subscription.constants.AccountEventNames;
import exchange.notbank.subscription.constants.Endpoints;
import io.vavr.control.Either;

public class SuffixFnByEventBuilder {
  private final JsonAdapter<List<Object>> objectListAdapter;
  private final JsonAdapter<InstrumentIdAndAccountId> idsAdapter;

  public SuffixFnByEventBuilder(JsonAdapter<List<Object>> objectListAdapter,
      JsonAdapter<InstrumentIdAndAccountId> idsAdapter) {
    this.objectListAdapter = objectListAdapter;
    this.idsAdapter = idsAdapter;
  }

  public Map<String, Function<String, String>> build() {
    Map<String, Function<String, String>> suffixFnByEvent = new HashMap<>();
    suffixFnByEvent.put(Endpoints.SUBSCRIBE_LEVEL_1, this::getLevel1TickerSuffix);
    suffixFnByEvent.put(Endpoints.UPDATE_LEVEL_1, this::getLevel1TickerSuffix);
    suffixFnByEvent.put(Endpoints.SUBSCRIBE_LEVEL_2, this::getLevel2TickerSuffix);
    suffixFnByEvent.put(Endpoints.UPDATE_LEVEL_2, this::getLevel2TickerSuffix);
    suffixFnByEvent.put(Endpoints.SUBSCRIBE_TICKER, this::getTickerName);
    suffixFnByEvent.put(Endpoints.SUBSCRIBE_TRADES, this::getSocketTradeSuffix);
    suffixFnByEvent.put(Endpoints.ORDER_STATE_EVENT, this::getOrderEventSuffix);
    AccountEventNames.ALL_EVENTS.stream().forEach(
        eventName -> suffixFnByEvent.put(eventName, this::getAccountEventSuffix));
    return suffixFnByEvent;
  }

  private String getTickerName(String payload) {
    var instrumentId = getValueFromList(payload, 8);
    return Suffixer.toSuffix(instrumentId);
  }

  private String getLevel1TickerSuffix(String payload) {
    return Suffixer.toSuffix(getInstrumentedId(payload));
  }

  private String getLevel2TickerSuffix(String payloadStr) {
    var instrumentId = getValueFromList(payloadStr, 7);
    return Suffixer.toSuffix(instrumentId);
  }

  private String getSocketTradeSuffix(String payload) {
    var instrumentId = getValueFromList(payload, 1);
    return Suffixer.toSuffix(instrumentId);
  }

  private String getAccountEventSuffix(String payload) {
    var accountId = getAccountId(payload);
    if (accountId.isRight()) {
      return Suffixer.toSuffix(accountId);
    }
    accountId = getAccountIdFromAccount(payload);
    if (accountId.isRight()) {
      return Suffixer.toSuffix(accountId);
    }
    return Suffixer.EMPTY_SUFFIX;
  }

  private String getOrderEventSuffix(String payload) {
    var ids = getIds(payload);
    if (ids.isLeft()) {
      return Suffixer.EMPTY_SUFFIX;
    }
    var accountId = Optional.ofNullable(ids.get().accountId);
    var instrumentId = Optional.ofNullable(ids.get().instrumentId);
    return Suffixer.toSuffix(accountId) + Suffixer.toSuffix(instrumentId);
  }

  private Either<String, String> getValueFromList(String payload, Integer valueindex) {
    var objectList = getObjectList(payload);
    if (objectList.isLeft()) {
      return Either.left(objectList.getLeft());
    }
    if (objectList.get().size() < valueindex - 1) {
      return Either.left("unable to get value from list. object list is too short");
    }
    var value = objectList.get().get(valueindex).toString();
    return Either.right(value);
  }

  private Either<String, String> getInstrumentedId(String payload) {
    return getIdFromIds(payload, ids -> ids.instrumentId);
  }

  private Either<String, String> getInstrumentedIdFromInstrument(String payload) {
    return getIdFromIds(payload, ids -> ids.instrument);
  }

  private Either<String, String> getAccountId(String payload) {
    return getIdFromIds(payload, ids -> ids.accountId);
  }

  private Either<String, String> getAccountIdFromAccount(String payload) {
    return getIdFromIds(payload, ids -> ids.account);

  }

  private Either<String, String> getIdFromIds(String payload, Function<InstrumentIdAndAccountId, String> getId) {
    return getIds(payload)
        .map(getId)
        .map(Optional::ofNullable)
        .map(id -> id.orElse(""));
  }

  private Either<String, InstrumentIdAndAccountId> getIds(String payload) {
    try {
      var ids = idsAdapter.fromJson(payload);
      return Either.right(ids);
    } catch (IOException e) {
      return Either.left("unable to parse ids." + e.getMessage());
    }
  }

  private Either<String, List<Object>> getObjectList(String payload) {
    try {
      var objectList = objectListAdapter.fromJson(payload);
      return Either.right(objectList);
    } catch (IOException e) {
      return Either.left("unable to parse ids." + e.getMessage());
    }
  }
}
