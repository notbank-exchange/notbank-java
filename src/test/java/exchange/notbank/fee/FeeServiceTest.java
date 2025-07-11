package exchange.notbank.fee;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import exchange.notbank.TestHelper;
import exchange.notbank.fee.constants.IntOrderType;
import exchange.notbank.fee.constants.IntSide;
import exchange.notbank.fee.constants.MakerTaker;
import exchange.notbank.fee.paramBuilders.GetDepositFeeParamBuilder;
import exchange.notbank.fee.paramBuilders.GetOrderFeeParamBuilder;
import exchange.notbank.fee.paramBuilders.GetWithdrawFeeParamBuilder;

public class FeeServiceTest {
  private static FeeService feeService;

  @BeforeAll
  public static void beforeAll() {
    var serviceFactory = TestHelper.newRestClient();
    feeService = serviceFactory.getFeeService();
  }

  @Test
  public void getDepositFee() {
    var params = new GetDepositFeeParamBuilder(1, 1, new BigDecimal(100), 1);
    var futureResponse = feeService.getDepositFee(params);
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getWithdrawFee() {
    var params = new GetWithdrawFeeParamBuilder(1, 1, new BigDecimal(100));
    var futureResponse = feeService.getWithdrawFee(params);
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getOrderFee() {
    var params = new GetOrderFeeParamBuilder(1, 1, new BigDecimal(0.5), new BigDecimal(1000), IntOrderType.LIMIT,
        MakerTaker.Maker, IntSide.Buy);
    var futureResponse = feeService.getOrderFee(params);
    TestHelper.checkNoError(futureResponse);
  }
}