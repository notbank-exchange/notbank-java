package exchange.notbank.core.adapters;

import java.math.BigInteger;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

public class BigIntegerAdapter {
  @ToJson
  String toJson(BigInteger value) {
    return value.toString();
  }

  @FromJson
  BigInteger fromJson(String value) {
    return new BigInteger(String.valueOf(value));
  }
}
