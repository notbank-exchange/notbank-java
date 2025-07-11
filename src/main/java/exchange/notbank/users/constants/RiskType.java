package exchange.notbank.users.constants;

import com.squareup.moshi.Json;

public enum RiskType {
  @Json(name = "Unknown")
  UNKNOWN,
  @Json(name = "Normal")
  NORMAL,
  @Json(name = "NoRiskCheck")
  NO_RISK_CHECK,
  @Json(name = "NoTrading")
  NO_TRADING,
  @Json(name = "Credit")
  CREDIT;
}
