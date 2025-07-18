package exchange.notbank.quote.responses;

import java.math.BigDecimal;
import java.util.UUID;

import com.squareup.moshi.Json;

import exchange.notbank.quote.constants.QuoteState;
import exchange.notbank.quote.constants.QuoteType;

public class Quote {
  public final UUID id;
  @Json(name = "is_inverse")
  public final Boolean isInverse;
  public final QuoteType type;
  public final QuoteState state;
  @Json(name = "currency_in")
  public final String currencyIn;
  @Json(name = "currency_out")
  public final String currencyOut;
  @Json(name = "amount_in")
  public final BigDecimal amountIn;
  @Json(name = "amount_out")
  public final BigDecimal amountOut;
  @Json(name = "amount_usdt_out")
  public final BigDecimal amountUsdtOut;
  @Json(name = "fee_amount")
  public final BigDecimal feeAmount;
  @Json(name = "fee_detail")
  public final String feeDetail;

  public Quote(UUID id, Boolean isInverse, QuoteType type, QuoteState state, String currencyIn, String currencyOut,
      BigDecimal amountIn, BigDecimal amountOut, BigDecimal amountUsdtOut, BigDecimal feeAmount, String feeDetail) {
    this.id = id;
    this.isInverse = isInverse;
    this.type = type;
    this.state = state;
    this.currencyIn = currencyIn;
    this.currencyOut = currencyOut;
    this.amountIn = amountIn;
    this.amountOut = amountOut;
    this.amountUsdtOut = amountUsdtOut;
    this.feeAmount = feeAmount;
    this.feeDetail = feeDetail;
  }

  @Override
  public String toString() {
    return "Quote [id=" + id + ", isInverse=" + isInverse + ", type=" + type + ", state=" + state + ", currencyIn="
        + currencyIn + ", currencyOut=" + currencyOut + ", amountIn=" + amountIn + ", amountOut=" + amountOut
        + ", amountUsdtOut=" + amountUsdtOut + ", feeAmount=" + feeAmount + ", feeDetail=" + feeDetail + "]";
  }
}
