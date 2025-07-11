package exchange.notbank.product;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import exchange.notbank.NotbankClient;
import exchange.notbank.TestHelper;
import exchange.notbank.product.paramBuilders.GetProductParamBuilder;
import exchange.notbank.product.paramBuilders.GetProductsParamBuilder;

public class ProductServiceTest {
  private static ProductService service;

  @BeforeAll
  public static void beforeAll() {
    service = NotbankClient.Factory.createRestClient(TestHelper.HOST).getProductService();
  }

  @Test
  public void getProducts() {
    var futureResponse = service.getProducts(new GetProductsParamBuilder().depth(2));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getProduct() {
    var futureResponse = service.getProduct(new GetProductParamBuilder(1));
    TestHelper.checkNoError(futureResponse);
  }

}
