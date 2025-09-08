package exchange.notbank.core.websocket.callback.subscription;

import com.squareup.moshi.Json;

public class InstrumentIdAndAccountId {
  @Json(name = "InstrumentId")
  public final String instrumentId;
  @Json(name = "Instrument")
  public final String instrument;
  @Json(name = "AccountId")
  public final String accountId;
  @Json(name = "Account")
  public final String account;

  public InstrumentIdAndAccountId(String instrumentId, String instrument, String accountId, String account) {
    this.instrumentId = instrumentId;
    this.instrument = instrument;
    this.accountId = accountId;
    this.account = account;
  }
}
