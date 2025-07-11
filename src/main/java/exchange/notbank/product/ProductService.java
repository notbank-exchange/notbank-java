package exchange.notbank.product;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;

import exchange.notbank.core.EndpointCategory;
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

  public ProductService(Supplier<CompletableFuture<NotbankConnection>> getNotbankConnection,
      ProductResponseAdapter responseAdapter) {
    this.getNotbankConnection = getNotbankConnection;
    this.responseAdapter = responseAdapter;
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
