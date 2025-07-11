package exchange.notbank.wallet.responses;

import java.util.List;

import com.squareup.moshi.Json;

public class Banks {
  public final Integer total;
  @Json(name = "data")
  public final List<Bank> banks;

  public Banks(Integer total, List<Bank> banks) {
    this.total = total;
    this.banks = banks;
  }

  @Override
  public String toString() {
    return "Banks [total=" + total + ", banks=" + banks + "]";
  }
}
