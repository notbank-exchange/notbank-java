package exchange.notbank.product;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import exchange.notbank.core.ErrorHandler;
import exchange.notbank.core.NotbankException;
import exchange.notbank.product.responses.Product;
import exchange.notbank.product.responses.VerificationLevelConfig;
import io.vavr.control.Either;

public class ProductResponseAdapter {
  private final ErrorHandler errorHandler;
  private final JsonAdapter<Product> productJsonAdapter;
  private final JsonAdapter<List<Product>> productsJsonAdapter;
  private final JsonAdapter<VerificationLevelConfig> verificationLevelConfigJsonAdapter;

  public ProductResponseAdapter(Moshi moshi) {
    this.errorHandler = ErrorHandler.Factory.createApErrorHandler(moshi);
    this.productJsonAdapter = moshi.adapter(Product.class);
    ParameterizedType productListType = Types.newParameterizedType(List.class, Product.class);
    this.productsJsonAdapter = moshi.adapter(productListType);
    this.verificationLevelConfigJsonAdapter = moshi.adapter(VerificationLevelConfig.class);
  }

  public Either<NotbankException, Void> toNone(String jsonStr) {
    return errorHandler.toNone(jsonStr);
  }

  private <T> Either<NotbankException, T> handle(String jsonString, JsonAdapter<T> jsonAdapter) {
    return errorHandler.handleAndThen(jsonAdapter).apply(jsonString);
  }

  public Either<NotbankException, Product> toProduct(String jsonStr) {
    return handle(jsonStr, productJsonAdapter);
  }

  public Either<NotbankException, List<Product>> toProductList(String jsonStr) {
    return handle(jsonStr, productsJsonAdapter);
  }

  public Either<NotbankException, VerificationLevelConfig> toVerificationLevelConfig(String response) {
    return handle(response, verificationLevelConfigJsonAdapter);
  }
}
