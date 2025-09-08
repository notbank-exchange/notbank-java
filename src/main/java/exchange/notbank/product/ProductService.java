package exchange.notbank.product;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.function.Function;
import java.util.function.Supplier;

import exchange.notbank.core.EndpointCategory;
import exchange.notbank.core.HashMapCache;
import exchange.notbank.core.NotbankConnection;
import exchange.notbank.core.NotbankException;
import exchange.notbank.core.ParamBuilder;
import exchange.notbank.product.constants.Endpoints;
import exchange.notbank.product.paramBuilders.GetProductParamBuilder;
import exchange.notbank.product.paramBuilders.GetProductsParamBuilder;
import exchange.notbank.product.paramBuilders.GetVerificationLevelConfigParamBuilder;
import exchange.notbank.product.responses.Product;
import exchange.notbank.product.responses.VerificationLevelConfig;
import io.vavr.control.Either;

public class ProductService {
  private final Supplier<CompletableFuture<NotbankConnection>> getNotbankConnection;
  private final ProductResponseAdapter responseAdapter;
  private final HashMapCache<Product> productCache;

  public ProductService(Supplier<CompletableFuture<NotbankConnection>> getNotbankConnection,
      ProductResponseAdapter responseAdapter, HashMapCache<Product> productCache) {
    this.getNotbankConnection = getNotbankConnection;
    this.responseAdapter = responseAdapter;
    this.productCache = productCache;
  }

  public static class Factory {
    public static ProductService create(
        Supplier<CompletableFuture<NotbankConnection>> getNotbankConnection,
        ProductResponseAdapter responseAdapter) {
      return new ProductService(getNotbankConnection, responseAdapter, HashMapCache.Factory.create());
    }
  }

  private <T> CompletableFuture<T> requestPost(String endpoint, ParamBuilder paramBuilder,
      Function<String, Either<NotbankException, T>> deserializeFn) {
    return getNotbankConnection.get()
        .thenCompose(connection -> connection.requestPost(EndpointCategory.AP, endpoint, paramBuilder, deserializeFn));
  }

  /**
   * https://apidoc.notbank.exchange/#getproducts
   */
  public CompletableFuture<List<Product>> getProducts(GetProductsParamBuilder paramBuilder) {
    return requestPost(Endpoints.GET_PRODUCTS, paramBuilder, responseAdapter::toProductList);
  }

  /**
   * https://apidoc.notbank.exchange/#getproduct
   */
  public CompletableFuture<Product> getProduct(GetProductParamBuilder paramBuilder) {
    return requestPost(Endpoints.GET_PRODUCT, paramBuilder, responseAdapter::toProduct);
  }

  public CompletableFuture<Product> getProduct(String symbol) {
    var product = productCache.get(symbol);
    if (product.isPresent()) {
      return CompletableFuture.completedFuture(product.get());
    }
    return getProducts(new GetProductsParamBuilder())
        .thenAccept(products -> productCache.update(products, aProduct -> aProduct.product))
        .thenApply(o -> {
          var newProduct = productCache.get(symbol);
          if (newProduct.isEmpty()) {
            throw new CompletionException(NotbankException.Factory.create(NotbankException.ErrorType.REQUEST_ERROR,
                "no product exists for symbol: " + symbol));
          }
          return newProduct.get();
        });
  }

  public CompletableFuture<Integer> getProductId(String symbol) {
    var product = getProduct(symbol);
    return product.thenApply(aInstrument -> aInstrument.productId);
  }

  /**
   * https://apidoc.notbank.exchange/#getverificationlevelconfig
   */
  public CompletableFuture<VerificationLevelConfig> getVerificationLevelConfig(
      GetVerificationLevelConfigParamBuilder paramBuilder) {
    return requestPost(
        Endpoints.GET_VERIFICATION_LEVEL_CONFIG,
        paramBuilder,
        responseAdapter::toVerificationLevelConfig);
  }

}
