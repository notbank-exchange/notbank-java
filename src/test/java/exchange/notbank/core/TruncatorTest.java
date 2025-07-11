package exchange.notbank.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

public class TruncatorTest {
  
  @Test
  public void truncates_1() {
    var value = new BigDecimal("1.2345678");
    var decimals = new BigDecimal("0.1");
    var truncated = Truncator.truncate(value, decimals);
    assertEquals(0, truncated.compareTo(new BigDecimal("1.2")));
  }
  @Test
  public void truncates_2() {
    var value = new BigDecimal("1.2345678");
    var decimals = new BigDecimal("0.01");
    var truncated = Truncator.truncate(value, decimals);
    assertEquals(0, truncated.compareTo(new BigDecimal("1.23")));
  }
  @Test
  public void truncates_3() {
    var value = new BigDecimal("1.2345678");
    var decimals = new BigDecimal("0.001");
    var truncated = Truncator.truncate(value, decimals);
    assertEquals(0, truncated.compareTo(new BigDecimal("1.234")));
  }
  @Test
  public void truncates_4() {
    var value = new BigDecimal("1.2345678");
    var decimals = new BigDecimal("0.000000001");
    var truncated = Truncator.truncate(value, decimals);
    assertEquals(0, truncated.compareTo(new BigDecimal("1.2345678")));
  }
  @Test
  public void truncates_5() {
    var value = new BigDecimal("1.2345678");
    var decimals = new BigDecimal("0.00000000001");
    var truncated = Truncator.truncate(value, decimals);
    assertEquals(0, truncated.compareTo(new BigDecimal("1.2345678")));
  }

  @Test
  public void truncates_6() {
    var value = new BigDecimal("0.12345678");
    var decimals = new BigDecimal("1");
    var truncated = Truncator.truncate(value, decimals);
    assertEquals(0, truncated.compareTo(new BigDecimal("0")));
  }

  @Test
  public void truncates_7() {
    var value = new BigDecimal("12");
    var decimals = new BigDecimal("0.000001");
    var truncated = Truncator.truncate(value, decimals);
    assertEquals(0, truncated.compareTo(new BigDecimal("12")));
  }
}
