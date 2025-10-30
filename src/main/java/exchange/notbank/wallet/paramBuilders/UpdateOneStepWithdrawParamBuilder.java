package exchange.notbank.wallet.paramBuilders;

import java.util.HashMap;
import java.util.Map;

import exchange.notbank.core.HttpConfiguration;
import exchange.notbank.core.ParamBuilder;
import exchange.notbank.wallet.constants.UpdateOneStepWithdrawAction;

public class UpdateOneStepWithdrawParamBuilder implements ParamBuilder {
  protected final Map<String, Object> params;
  protected HttpConfiguration httpConfiguration;

  public UpdateOneStepWithdrawParamBuilder(Integer accountId, UpdateOneStepWithdrawAction action, String otp) {
    this.httpConfiguration = new HttpConfiguration();
    this.params = new HashMap<>();
    this.params.put("account_id", accountId);
    this.params.put("action", action);
    this.params.put("otp", otp);
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public HttpConfiguration getHttpConfiguration() {
    return httpConfiguration;
  }

  public UpdateOneStepWithdrawParamBuilder setHttpConfiguration(HttpConfiguration httpConfiguration) {
    this.httpConfiguration = httpConfiguration;
    return this;
  }
}
