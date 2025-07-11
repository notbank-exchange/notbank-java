package exchange.notbank;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import exchange.notbank.account.AccountService;
import exchange.notbank.core.NotbankConnection;
import exchange.notbank.core.NotbankConnectionAuthenticator;
import exchange.notbank.fee.FeeService;
import exchange.notbank.instrument.InstrumentService;
import exchange.notbank.product.ProductService;
import exchange.notbank.subscription.SubscriptionService;
import exchange.notbank.system.SystemService;
import exchange.notbank.trading.TradingService;
import exchange.notbank.users.UserService;
import exchange.notbank.wallet.WalletService;

public class NotbankClient {
  public final Supplier<NotbankConnection> connectionSupplier;
  public final AccountService accountService;
  public final FeeService feeService;
  public final InstrumentService instrumentService;
  public final ProductService productService;
  public final SubscriptionService subscriptionService;
  public final SystemService systemService;
  public final TradingService tradingService;
  public final UserService userService;
  public final WalletService walletService;

  public NotbankClient(Supplier<NotbankConnection> connectionSupplier, AccountService accountService,
      FeeService feeService, InstrumentService instrumentService, ProductService productService,
      SubscriptionService subscriptionService, SystemService systemService, TradingService tradingService,
      UserService userService, WalletService walletService) {
    this.connectionSupplier = connectionSupplier;
    this.accountService = accountService;
    this.feeService = feeService;
    this.instrumentService = instrumentService;
    this.productService = productService;
    this.subscriptionService = subscriptionService;
    this.systemService = systemService;
    this.tradingService = tradingService;
    this.userService = userService;
    this.walletService = walletService;
  }

  public static class Factory extends NotbankClientFactory {
  }

  public NotbankConnection getNotbankConnection() {
    return connectionSupplier.get();
  }

  public CompletableFuture<Void> authenticate(String userId, String userApiKey, String userSecretKey) {
    return NotbankConnectionAuthenticator.authenticate(connectionSupplier.get()::authenticateUser, userId, userApiKey,
        userSecretKey);
  }

  public CompletableFuture<Void> authenticate(Integer userId, String userApiKey, String userSecretKey) {
    return authenticate(userId.toString(), userApiKey, userSecretKey);
  }

  public CompletableFuture<Void> logOut() {
    return connectionSupplier.get().logOut();
  }

  public FeeService getFeeService() {
    return feeService;
  }

  public TradingService getTradingService() {
    return tradingService;
  }

  public UserService getUserService() {
    return userService;
  }

  public SystemService getSystemService() {
    return systemService;
  }

  public AccountService getAccountService() {
    return accountService;
  }

  public InstrumentService getInstrumentService() {
    return instrumentService;
  }

  public ProductService getProductService() {
    return productService;
  }

  public SubscriptionService getSubscriptionService() {
    return subscriptionService;
  }

  public WalletService getWalletService() {
    return walletService;
  }
}
