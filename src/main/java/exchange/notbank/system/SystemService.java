package exchange.notbank.system;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;

import exchange.notbank.core.EndpointCategory;
import exchange.notbank.core.NotbankConnection;
import exchange.notbank.core.NotbankException;
import exchange.notbank.core.ParamBuilder;
import exchange.notbank.system.constants.Endpoints;
import exchange.notbank.system.responses.HealthCheck;
import exchange.notbank.system.responses.Pong;

import io.vavr.control.Either;

public class SystemService {
  private final Supplier<CompletableFuture<NotbankConnection>> getNotbankConnection;
  private final SystemServiceResponseAdapter responseAdapter;

  public SystemService(Supplier<CompletableFuture<NotbankConnection>> getNotbankConnection,
      SystemServiceResponseAdapter responseAdapter) {
    this.getNotbankConnection = getNotbankConnection;
    this.responseAdapter = responseAdapter;
  }

  private <T> CompletableFuture<T> requestPost(String endpoint, ParamBuilder paramBuilder,
      Function<String, Either<NotbankException, T>> deserializeFn) {
    return getNotbankConnection.get()
        .thenCompose(
            connection -> connection.requestPost(EndpointCategory.AP, endpoint, paramBuilder, deserializeFn));
  }

  /**
   * https://apidoc.notbank.exchange/#ping
   */
  public CompletableFuture<Pong> ping() {
    return requestPost(Endpoints.PING, ParamBuilder.EMPTY, responseAdapter::toPong);
  }

  /**
   * https://apidoc.notbank.exchange/#healthcheck
   */
  public CompletableFuture<HealthCheck> healthCheck() {
    return requestPost(Endpoints.HEALTH_CHECK, ParamBuilder.EMPTY, responseAdapter::toHealthCheck);
  }
}
