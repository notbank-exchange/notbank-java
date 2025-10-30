package exchange.notbank.product;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import exchange.notbank.CredentialsLoader.UserCredentials;
import exchange.notbank.NotbankClient;
import exchange.notbank.TestHelper;
import exchange.notbank.product.paramBuilders.GetProductParamBuilder;
import exchange.notbank.product.paramBuilders.GetProductsParamBuilder;
import exchange.notbank.product.paramBuilders.GetVerificationLevelConfigParamBuilder;

public class ProductServiceTest {
  private static NotbankClient client;
  private static UserCredentials credentials;

  @BeforeAll
  public static void beforeAll() {
    client = TestHelper.newRestClient();
    credentials = TestHelper.getUserCredentials();
  }

  @Test
  public void getProducts() {
    var futureResponse = client.getProductService().getProducts(new GetProductsParamBuilder());
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getProductsIncludingDisabled() {
    var futureResponse = client.getProductService().getProducts(new GetProductsParamBuilder().getDisabled(true));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getProducts_2() {
    var futureResponse = client.getProductService().getProducts(new GetProductsParamBuilder()
        .getDisabled(false)
        .depth(10)
        .startIndex(0));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getFilteredProducts() {
    var futureResponse = client.getProductService().getProducts(new GetProductsParamBuilder()
        .attribute("Pegged")
        .attributeValue(true));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getProduct() {
    var futureResponse = client.getProductService().getProduct(new GetProductParamBuilder(1));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getVerificationLevelConfigParamBuilder() {
    var futureResponse = client.getProductService()
        .getVerificationLevelConfig(new GetVerificationLevelConfigParamBuilder(credentials.accountId));
    TestHelper.checkNoError(futureResponse);
  }

}
