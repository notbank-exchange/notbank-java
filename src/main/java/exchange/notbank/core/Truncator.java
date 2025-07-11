package exchange.notbank.core;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Truncator {
  public static BigDecimal truncate(BigDecimal value, BigDecimal decimals) {
    var exp = BigDecimal.ONE.divide(decimals);
    var intValue = value.multiply(exp).setScale(0, RoundingMode.DOWN);
    return intValue.divide(exp);
  }
}
