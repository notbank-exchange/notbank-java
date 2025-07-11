package exchange.notbank.instrument;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import exchange.notbank.core.ErrorHandler;
import exchange.notbank.core.NotbankException;
import exchange.notbank.instrument.responses.Instrument;
import exchange.notbank.instrument.responses.InstrumentVerificationLevelConfig;
import io.vavr.control.Either;

public class InstrumentResponseAdapter {
  private final ErrorHandler errorHandler;
  private final JsonAdapter<Instrument> instrumentJsonAdapter;
  private final JsonAdapter<List<Instrument>> instrumentsJsonAdapter;
  private final JsonAdapter<InstrumentVerificationLevelConfig> instrumentVerificationLevelConfigJsonAdapter;

  public InstrumentResponseAdapter(Moshi moshi) {
    this.errorHandler = ErrorHandler.Factory.createApErrorHandler(moshi);
    this.instrumentJsonAdapter = moshi.adapter(Instrument.class);
    ParameterizedType instrumentListType = Types.newParameterizedType(List.class, Instrument.class);
    this.instrumentsJsonAdapter = moshi.adapter(instrumentListType);
    this.instrumentVerificationLevelConfigJsonAdapter = moshi.adapter(InstrumentVerificationLevelConfig.class);
  }

  public Either<NotbankException, Void> toNone(String jsonStr) {
    return errorHandler.toNone(jsonStr);
  }

  private <T> Either<NotbankException, T> handle(String jsonString, JsonAdapter<T> jsonAdapter) {
    return errorHandler.handleAndThen(jsonAdapter).apply(jsonString);
  }

  public Either<NotbankException, Instrument> toInstrument(String jsonStr) {
    return handle(jsonStr, instrumentJsonAdapter);
  }

  public Either<NotbankException, List<Instrument>> toInstrumentList(String jsonStr) {
    return handle(jsonStr, instrumentsJsonAdapter);
  }

  public Either<NotbankException, InstrumentVerificationLevelConfig> toInstrumentVerificationLevelConfig(
      String response) {
    return handle(response, instrumentVerificationLevelConfigJsonAdapter);
  }
}
