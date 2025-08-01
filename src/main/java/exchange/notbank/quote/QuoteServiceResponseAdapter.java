package exchange.notbank.quote;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import exchange.notbank.core.ErrorHandler;
import exchange.notbank.core.NotbankException;
import exchange.notbank.quote.responses.Quote;
import exchange.notbank.wallet.responses.IdResponse;
import io.vavr.control.Either;

public class QuoteServiceResponseAdapter {
  private final ErrorHandler errorHandler;
  private final JsonAdapter<Quote> quoteAdapter;
  private final JsonAdapter<List<Quote>> quoteListAdapter;
  private final JsonAdapter<IdResponse> idResponseAdapter;

  public QuoteServiceResponseAdapter(Moshi moshi) {
    this.errorHandler = ErrorHandler.Factory.createApErrorHandler(moshi);
    this.quoteAdapter = moshi.adapter(Quote.class);
    ParameterizedType QuoteListType = Types.newParameterizedType(List.class, Quote.class);
    this.quoteListAdapter = moshi.adapter(QuoteListType);
    this.idResponseAdapter = moshi.adapter(IdResponse.class);
  }

  public Either<NotbankException, Void> toNone(String jsonStr) {
    return errorHandler.toNone(jsonStr);
  }

  private <T> Either<NotbankException, T> handle(String jsonString, JsonAdapter<T> jsonAdapter) {
    return errorHandler.handleAndThen(jsonAdapter).apply(jsonString);
  }

  public Either<NotbankException, Quote> toQuote(String jsonStr) {
    return handle(jsonStr, quoteAdapter);
  }

  public Either<NotbankException, List<Quote>> toQuoteList(String jsonStr) {
    return handle(jsonStr, quoteListAdapter);
  }

  public Either<NotbankException, IdResponse> toIdResponse(String jsonStr) {
    return handle(jsonStr, idResponseAdapter);
  }
}
