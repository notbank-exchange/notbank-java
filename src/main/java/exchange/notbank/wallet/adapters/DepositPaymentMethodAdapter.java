package exchange.notbank.wallet.adapters;

import com.squareup.moshi.ToJson;

import exchange.notbank.wallet.constants.DepositPaymentMethod;

public class DepositPaymentMethodAdapter {
  @ToJson
  int toJson(DepositPaymentMethod value) {
    return value.value;
  }
}
