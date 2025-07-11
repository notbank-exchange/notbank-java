package exchange.notbank.fee.responses;

import java.math.BigDecimal;

import com.squareup.moshi.Json;

public class WithdrawFee {
  @Json(name = "FeeAmount")
  public final BigDecimal feeAmount;
  @Json(name = "TicketAmount")
  public final BigDecimal ticketAmount;

  public WithdrawFee(BigDecimal feeAmount, BigDecimal ticketAmount) {
    this.feeAmount = feeAmount;
    this.ticketAmount = ticketAmount;
  }

  @Override
  public String toString() {
    return "WithdrawFee [feeAmount=" + feeAmount + ", ticketAmount=" + ticketAmount + "]";
  }
}
