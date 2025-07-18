package exchange.notbank.quote;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;

import exchange.notbank.core.EndpointCategory;
import exchange.notbank.core.NotbankConnection;
import exchange.notbank.core.NotbankException;
import exchange.notbank.core.ParamBuilder;
import exchange.notbank.quote.constants.Endpoints;
import exchange.notbank.quote.paramBuilders.CreateDirectQuoteParamBuilder;
import exchange.notbank.quote.paramBuilders.CreateInverseQuoteParamBuilder;
import exchange.notbank.quote.paramBuilders.ExecuteQuoteParamBuilder;
import exchange.notbank.quote.paramBuilders.GetQuoteParamBuilder;
import exchange.notbank.quote.responses.Quote;
import io.vavr.control.Either;

public class QuoteService {
  private final Supplier<CompletableFuture<NotbankConnection>> getNotbankConnection;
  private final QuoteServiceResponseAdapter responseAdapter;

  public QuoteService(Supplier<CompletableFuture<NotbankConnection>> getNotbankConnection,
      QuoteServiceResponseAdapter responseAdapter) {
    this.getNotbankConnection = getNotbankConnection;
    this.responseAdapter = responseAdapter;
  }

  private <T> CompletableFuture<T> requestPost(String endpoint, ParamBuilder paramBuilder,
      Function<String, Either<NotbankException, T>> deserializeFn) {
    return getNotbankConnection.get()
        .thenCompose(
            connection -> connection.requestPost(EndpointCategory.NB, endpoint, paramBuilder, deserializeFn));
  }

  private <T> CompletableFuture<T> requestGet(String endpoint, ParamBuilder paramBuilder,
      Function<String, Either<NotbankException, T>> deserializeFn) {
    return getNotbankConnection.get()
        .thenCompose(
            connection -> connection.requestGet(EndpointCategory.NB, endpoint, paramBuilder, deserializeFn));
  }

  /**
   * https://apidoc.notbank.exchange/?http#getquotes
   */
  public CompletableFuture<List<Quote>> getQuotes(GetQuoteParamBuilder paramBuilder) {
    return requestGet(Endpoints.QUOTES, paramBuilder, responseAdapter::toQuoteList);
  }

  /**
   * https://apidoc.notbank.exchange/?http#createdirectquote
   */
  public CompletableFuture<String> createDirectQuote(CreateDirectQuoteParamBuilder paramBuilder) {
    return requestPost(Endpoints.QUOTES_DIRECT, paramBuilder,
        jsonStr -> responseAdapter.toIdResponse(jsonStr).map(response -> response.id));
  }

  /**
   * https://apidoc.notbank.exchange/?http#createinversequote
   */
  public CompletableFuture<String> createInverseQuote(CreateInverseQuoteParamBuilder paramBuilder) {
    return requestPost(Endpoints.QUOTES_INVERSE, paramBuilder,
        jsonStr -> responseAdapter.toIdResponse(jsonStr).map(response -> response.id));
  }

  /**
   * https://apidoc.notbank.exchange/?http#getquote
   */
  public CompletableFuture<Quote> getQuote(GetQuoteParamBuilder paramBuilder) {
    return requestPost(Endpoints.QUOTES + "/" + paramBuilder.quoteId, paramBuilder, responseAdapter::toQuote);
  }

  /**
   * https://apidoc.notbank.exchange/?http#executequote
   */
  public CompletableFuture<Quote> executeQuote(ExecuteQuoteParamBuilder paramBuilder) {
    return requestPost(Endpoints.QUOTES + "/" + paramBuilder.quoteId, paramBuilder, responseAdapter::toQuote);
  }

}
