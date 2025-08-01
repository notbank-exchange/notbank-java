package exchange.notbank.wallet.adapters;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import exchange.notbank.wallet.constants.TransactionStatus;

public class TransactionStatusAdapter {
  @ToJson
  int toJson(TransactionStatus value) {
    return value.value;
  }

  @FromJson
  TransactionStatus fromJson(int value) {
    try {
      return TransactionStatus.values()[value];
    } catch (IndexOutOfBoundsException e) {
      return TransactionStatus.OTHER;
    }
  }
}
