package exchange.notbank.core.websocket;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

import exchange.notbank.core.NotbankConnection;

public record WebsocketNotbankConnectionConfiguration(
    String host,
    Function<NotbankConnection, CompletableFuture<NotbankConnection>> notbankConnectionInterceptor,
    WebsocketJsonAdapters jsonAdapters,
    Consumer<Throwable> onError,
    Consumer<String> peekMessageIn,
    Consumer<String> peekMessageOut) {
}
