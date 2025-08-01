package exchange.notbank.instrument;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.function.Function;
import java.util.function.Supplier;

import exchange.notbank.core.EndpointCategory;
import exchange.notbank.core.NotbankConnection;
import exchange.notbank.core.NotbankException;
import exchange.notbank.core.ParamBuilder;
import exchange.notbank.instrument.constants.Endpoints;
import exchange.notbank.instrument.paramBuilders.GetInstrumentParamBuilder;
import exchange.notbank.instrument.paramBuilders.GetInstrumentVerificationLevelConfigParamBuilder;
import exchange.notbank.instrument.paramBuilders.GetInstrumentsParamBuilder;
import exchange.notbank.instrument.responses.Instrument;
import exchange.notbank.instrument.responses.InstrumentVerificationLevelConfig;

import io.vavr.control.Either;

public class InstrumentService {
  private final Supplier<CompletableFuture<NotbankConnection>> getNotbankConnection;
  private final InstrumentResponseAdapter responseAdapter;
  private final InstrumentCache instrumentCache;

  public InstrumentService(Supplier<CompletableFuture<NotbankConnection>> getNotbankConnection,
      InstrumentResponseAdapter responseAdapter, InstrumentCache publicDataCache) {
    this.getNotbankConnection = getNotbankConnection;
    this.responseAdapter = responseAdapter;
    this.instrumentCache = publicDataCache;
  }

  public static class Factory {
    public static InstrumentService create(
        Supplier<CompletableFuture<NotbankConnection>> getNotbankConnection,
        InstrumentResponseAdapter responseAdapter) {
      return new InstrumentService(getNotbankConnection, responseAdapter, InstrumentCache.Factory.create());
    }
  }

  private <T> CompletableFuture<T> requestPost(String endpoint, ParamBuilder paramBuilder,
      Function<String, Either<NotbankException, T>> deserializeFn) {
    return getNotbankConnection.get()
        .thenCompose(
            connection -> connection.requestPost(EndpointCategory.AP, endpoint, paramBuilder, deserializeFn));
  }

  /**
   * https://apidoc.notbank.exchange/#getinstruments
   */
  public CompletableFuture<List<Instrument>> getInstruments(GetInstrumentsParamBuilder paramBuilder) {
    return requestPost(Endpoints.GET_INSTRUMENTS, paramBuilder, responseAdapter::toInstrumentList);
  }

  /**
   * https://apidoc.notbank.exchange/#getinstrument
   */
  public CompletableFuture<Instrument> getInstrument(GetInstrumentParamBuilder paramBuilder) {
    return requestPost(Endpoints.GET_INSTRUMENT, paramBuilder, responseAdapter::toInstrument);
  }

  /**
   * https://apidoc.notbank.exchange/#getinstrumentverificationlevelconfig
   */
  public CompletableFuture<InstrumentVerificationLevelConfig> getInstrumentVerificationLevelConfig(
      GetInstrumentVerificationLevelConfigParamBuilder paramBuilder) {
    return requestPost(Endpoints.GET_INSTRUMENT_VERIFICATION_LEVEL_CONFIG, paramBuilder,
        responseAdapter::toInstrumentVerificationLevelConfig);
  }

  public CompletableFuture<Integer> getInstrumentId(String symbol) {
    var instrument = getInstrument(symbol);
    return instrument.thenApply(aInstrument -> aInstrument.instrumentId);
  }

  public CompletableFuture<Instrument> getInstrument(String symbol) {
    var instrumentId = instrumentCache.getInstrument(symbol);
    if (instrumentId.isPresent()) {
      return CompletableFuture.completedFuture(instrumentId.get());
    }
    return getInstruments(new GetInstrumentsParamBuilder())
        .thenAccept(instruments -> instrumentCache.updateInstruments(instruments))
        .thenApply(o -> {
          var newInstrument = instrumentCache.getInstrument(symbol);
          if (newInstrument.isEmpty()) {
            throw new CompletionException(NotbankException.Factory.create(NotbankException.ErrorType.REQUEST_ERROR,
                "no instrument exists for symbol: " + symbol));
          }
          return newInstrument.get();
        });
  }
}
