package exchange.notbank.fee.responses;

import java.math.BigDecimal;

import com.squareup.moshi.Json;

public class DepositFee {
  @Json(name = "FeeAmount")
  public final BigDecimal feeAmount;
  @Json(name = "TicketAmount")
  public final BigDecimal ticketAmount;

  public DepositFee(BigDecimal feeAmount, BigDecimal ticketAmount) {
    this.feeAmount = feeAmount;
    this.ticketAmount = ticketAmount;
  }

  @Override
  public String toString() {
    return "DepositFee [feeAmount=" + feeAmount + ", ticketAmount=" + ticketAmount + "]";
  }
}
