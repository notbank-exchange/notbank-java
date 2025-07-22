package exchange.notbank.report.constants;

import com.squareup.moshi.Json;

public enum ReportFrequency {
  @Json(name = "onDemand")
  ON_DEMAND,
  @Json(name = "Hourly")
  HOURLY,
  @Json(name = "Daily")
  DAILY,
  @Json(name = "Weekly")
  WEEKLY,
  @Json(name = "Monthly")
  MONTHLY,
  @Json(name = "Annually")
  ANNUALLY;
}
