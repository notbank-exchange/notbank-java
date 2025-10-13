package exchange.notbank.fee.responses;

import java.math.BigDecimal;

import com.squareup.moshi.Json;

public class OrderFee {
  @Json(name = "OrderFee")
  public final BigDecimal orderFee;
  @Json(name = "ProductId")
  public final Integer productId;

  public OrderFee(BigDecimal orderFee, Integer productId) {
    this.orderFee = orderFee;
    this.productId = productId;
  }

  @Override
  public String toString() {
    return "OrderFee [orderFee=" + orderFee.stripTrailingZeros().toPlainString() + ", productId=" + productId + "]";
  }
}
