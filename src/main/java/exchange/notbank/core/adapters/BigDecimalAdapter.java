package exchange.notbank.core.adapters;

import java.math.BigDecimal;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

public class BigDecimalAdapter {
  @ToJson
  String toJson(BigDecimal value) {
    return value.toPlainString();
  }

  @FromJson
  BigDecimal fromJson(String value) {
    return new BigDecimal(String.valueOf(value));
  }
}
