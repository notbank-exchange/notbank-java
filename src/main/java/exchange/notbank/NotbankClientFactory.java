package exchange.notbank;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import exchange.notbank.account.AccountService;
import exchange.notbank.account.AccountServiceResponseAdapter;
import exchange.notbank.core.MoshiFactory;
import exchange.notbank.core.NotbankConnection;
import exchange.notbank.core.StandardApResponseHandler;
import exchange.notbank.core.responses.StandardResponse;
import exchange.notbank.core.rest.AuthenticationResponseAdapter;
import exchange.notbank.core.rest.HttpClient;
import exchange.notbank.core.rest.HttpNotbankConnection;
import exchange.notbank.core.websocket.WebsocketNotbankConnection;
import exchange.notbank.fee.FeeService;
import exchange.notbank.fee.FeeServiceResponseAdapter;
import exchange.notbank.instrument.InstrumentResponseAdapter;
import exchange.notbank.instrument.InstrumentService;
import exchange.notbank.product.ProductResponseAdapter;
import exchange.notbank.product.ProductService;
import exchange.notbank.report.ReportService;
import exchange.notbank.report.ReportServiceResponseAdapter;
import exchange.notbank.subscription.SubscriptionResponseAdapter;
import exchange.notbank.subscription.SubscriptionService;
import exchange.notbank.system.SystemService;
import exchange.notbank.system.SystemServiceResponseAdapter;
import exchange.notbank.trading.TradingService;
import exchange.notbank.trading.TradingServiceResponseAdapter;
import exchange.notbank.users.UserService;
import exchange.notbank.users.UserServiceResponseAdapter;
import exchange.notbank.wallet.WalletService;
import exchange.notbank.wallet.WalletServiceResponseAdapter;

public class NotbankClientFactory {
  private static final String HOST = "api.notbank.exchange";

  public static NotbankClient createRestClient() {
    return createRestClient(HOST);
  }

  public static NotbankClient createRestClient(String host) {
    return createRestClient(host, CompletableFuture::completedFuture, (o1, o2) -> {
      System.out.println(o1.headers());
    }, o -> {
    });
  }

  public static NotbankClient createRestClient(
      String host,
      Function<NotbankConnection, CompletableFuture<NotbankConnection>> notbankConnectionInterceptor,
      BiConsumer<HttpRequest, String> peekHttpRequest,
      Consumer<HttpResponse<String>> peekHttpResponse) {
    var moshi = MoshiFactory.create();
    var httpClient = HttpClient.Factory.newHttpClient(host, moshi, peekHttpRequest, peekHttpResponse);
    var notbankConnection = new HttpNotbankConnection(httpClient, new AuthenticationResponseAdapter(moshi));
    return create(notbankConnection, notbankConnectionInterceptor);

  }

  public static CompletableFuture<NotbankClient> createWebsocketClient() {
    return createWebsocketClient(HOST, CompletableFuture::completedFuture,
        o -> {
        },
        o -> {
        },
        o -> {
        });
  }

  public static CompletableFuture<NotbankClient> createWebsocketClient(
      String host,
      Function<NotbankConnection, CompletableFuture<NotbankConnection>> notbankConnectionInterceptor,
      Consumer<Throwable> onFailure,
      Consumer<String> peekMessageIn,
      Consumer<String> peekMessageOut) {
    var notbankConnection = WebsocketNotbankConnection.Factory.create(
        host,
        notbankConnectionInterceptor,
        onFailure,
        peekMessageIn,
        peekMessageOut);
    return notbankConnection.thenApply(connection -> create(connection, notbankConnectionInterceptor));
  }

  private static NotbankClient create(
      NotbankConnection notbankConnection,
      Function<NotbankConnection, CompletableFuture<NotbankConnection>> notbankConnectionInterceptor) {
    var moshi = MoshiFactory.create();
    var standardResponseJsonAdapter = moshi.adapter(StandardResponse.class);
    var standardResponseHandler = new StandardApResponseHandler(standardResponseJsonAdapter);
    var accountService = new AccountService(
        () -> notbankConnectionInterceptor.apply(notbankConnection),
        new AccountServiceResponseAdapter(standardResponseHandler, moshi));
    var feeService = new FeeService(
        () -> notbankConnectionInterceptor.apply(notbankConnection),
        new FeeServiceResponseAdapter(standardResponseHandler, moshi));
    var instrumentService = InstrumentService.Factory.create(
        () -> notbankConnectionInterceptor.apply(notbankConnection),
        new InstrumentResponseAdapter(moshi));
    var productService = new ProductService(
        () -> notbankConnectionInterceptor.apply(notbankConnection),
        new ProductResponseAdapter(moshi));
    var subscriptionService = new SubscriptionService(
        () -> notbankConnectionInterceptor.apply(notbankConnection),
        new SubscriptionResponseAdapter(standardResponseHandler, moshi));
    var systemService = new SystemService(
        () -> notbankConnectionInterceptor.apply(notbankConnection),
        new SystemServiceResponseAdapter(moshi));
    var tradingService = new TradingService(
        () -> notbankConnectionInterceptor.apply(notbankConnection),
        new TradingServiceResponseAdapter(moshi));
    var userService = new UserService(
        () -> notbankConnectionInterceptor.apply(notbankConnection),
        new UserServiceResponseAdapter(standardResponseHandler, moshi));
    var walletService = new WalletService(
        () -> notbankConnectionInterceptor.apply(notbankConnection),
        new WalletServiceResponseAdapter(moshi));
    var reportService = new ReportService(
        () -> notbankConnectionInterceptor.apply(notbankConnection),
        new ReportServiceResponseAdapter(moshi));
    return new NotbankClient(
        () -> notbankConnection,
        accountService,
        feeService,
        instrumentService,
        productService,
        subscriptionService,
        systemService,
        tradingService,
        userService,
        walletService,
        reportService);
  }
}