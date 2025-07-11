package exchange.notbank.subscription.responses;

import java.math.BigDecimal;

import com.squareup.moshi.Json;

public class DepositEvent {
  @Json(name = "OMSId")
  public final Integer omsId;
  @Json(name = "AccountId")
  public final Integer accountId;
  @Json(name = "ProductId")
  public final Integer productId;
  @Json(name = "Quantity")
  public final BigDecimal quantity;
  @Json(name = "SubAccountId")
  public final Integer subAccountId;

  public DepositEvent(Integer omsId, Integer accountId, Integer productId, BigDecimal quantity, Integer subAccountId) {
    this.omsId = omsId;
    this.accountId = accountId;
    this.productId = productId;
    this.quantity = quantity;
    this.subAccountId = subAccountId;
  }
}
