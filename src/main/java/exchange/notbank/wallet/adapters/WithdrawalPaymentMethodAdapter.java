package exchange.notbank.wallet.adapters;

import com.squareup.moshi.ToJson;

import exchange.notbank.wallet.constants.WithdrawalPaymentMethod;

public class WithdrawalPaymentMethodAdapter {
  @ToJson
  int toJson(WithdrawalPaymentMethod value) {
    return value.value;
  }
}
