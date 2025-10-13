package exchange.notbank.fee;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import exchange.notbank.CredentialsLoader.UserCredentials;
import exchange.notbank.NotbankClient;
import exchange.notbank.TestHelper;
import exchange.notbank.fee.constants.IntOrderType;
import exchange.notbank.fee.constants.IntSide;
import exchange.notbank.fee.constants.MakerTaker;
import exchange.notbank.fee.paramBuilders.GetAccountFeesParamBuilder;
import exchange.notbank.fee.paramBuilders.GetDepositFeeParamBuilder;
import exchange.notbank.fee.paramBuilders.GetOmsDepositFeesParamsBuilder;
import exchange.notbank.fee.paramBuilders.GetOmsWithdrawFeesParamsBuilder;
import exchange.notbank.fee.paramBuilders.GetOrderFeeParamBuilder;
import exchange.notbank.fee.paramBuilders.GetWithdrawFeeParamBuilder;

public class FeeServiceTest {
  private static UserCredentials credentials;
  private static NotbankClient client;

  @BeforeAll
  public static void beforeAll() {
    credentials = TestHelper.getUserCredentials();
    client = TestHelper.newRestClient();
  }

  @Test
  public void getDepositFee() {
    var futureResponse = client.getFeeService()
        .getDepositFee(new GetDepositFeeParamBuilder(credentials.accountId, 1, new BigDecimal(100), 1));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getWithdrawFee() {
    var futureResponse = client.getFeeService()
        .getWithdrawFee(new GetWithdrawFeeParamBuilder(credentials.accountId, 1, new BigDecimal(100)));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getOrderFee() {
    var futureResponse = client.getFeeService()
        .getOrderFee(new GetOrderFeeParamBuilder(1, 1, new BigDecimal(0.5), new BigDecimal(1000), IntOrderType.LIMIT,
            MakerTaker.Maker, IntSide.Buy));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getOmsWithdrawtFees() {
    var futureResponse = client.getFeeService()
        .getOmsWithdrawtFees(new GetOmsWithdrawFeesParamsBuilder().productId(1).accountProviderId(8));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getOmsDepositFees() {
    
    var futureResponse = client.getFeeService()
        .getOmsDepositFees(new GetOmsDepositFeesParamsBuilder().productId(1).accountProviderId(5));
    TestHelper.checkNoError(futureResponse);
  }

  @Test
  public void getAccountFees() {
    var futureResponse = client.getFeeService()
        .getAccountFees(new GetAccountFeesParamBuilder(credentials.accountId));
    TestHelper.checkNoError(futureResponse);
  }

}