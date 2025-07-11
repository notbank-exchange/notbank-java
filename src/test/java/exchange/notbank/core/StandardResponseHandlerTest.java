package exchange.notbank.core;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import exchange.notbank.TestHelper;
import exchange.notbank.core.responses.StandardResponse;
import com.squareup.moshi.Moshi;

public class StandardResponseHandlerTest {
  private static StandardApResponseHandler standardResponseHandler;

  @BeforeAll
  public static void beforeAll() {
    var moshi = new Moshi.Builder().build();
    var standardResponseJsonAdapter = moshi.adapter(StandardResponse.class);
    standardResponseHandler = new StandardApResponseHandler(standardResponseJsonAdapter);
  }

  @Test
  public void goodStandardResponseHasNoError() {
    var jsonStr = "{\"result\":true,\"errormsg\":null,\"errorcode\":0,\"detail\":null}";
    var safeJsonStr = standardResponseHandler.handle(jsonStr);
    TestHelper.assertIsRight(safeJsonStr);
  }

  @Test
  public void badStandardResponseHasError() {
    var jsonStr = "{\"result\":false,\"errormsg\":null,\"errorcode\":0,\"detail\":null}";
    var safeJsonStr = standardResponseHandler.handle(jsonStr);
    TestHelper.assertIsRight(safeJsonStr);
  }

  @Test
  public void nullStandardResponseMeansIsNoStandardResponse() {
    var jsonStr = "{\"result\":null,\"errormsg\":null,\"errorcode\":0,\"detail\":null}";
    var safeJsonStr = standardResponseHandler.handle(jsonStr);
    TestHelper.assertIsRight(safeJsonStr);
  }

  @Test
  public void badlyFormedStandardResponseMeansIsNoStandardResponse() {
    var jsonStr = "{\"resulttsd\":true,\"errormsg\":null,\"errorcode\":0,\"detail\":null}";
    var safeJsonStr = standardResponseHandler.handle(jsonStr);
    TestHelper.assertIsRight(safeJsonStr);
  }
}
