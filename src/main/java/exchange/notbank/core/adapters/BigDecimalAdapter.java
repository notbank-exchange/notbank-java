package exchange.notbank.core.adapters;

import java.math.BigDecimal;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

public class BigDecimalAdapter {
  @ToJson
  float toJson(BigDecimal value) {
    return value.floatValue();
  }

  @FromJson
  BigDecimal fromJson(float value) {
    return new BigDecimal(String.valueOf(value));
  }
}
