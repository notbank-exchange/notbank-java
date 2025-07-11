package exchange.notbank.core.adapters;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

public class LocalDatetimeAdapter {
  private static DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

  @ToJson
  public String toJson(LocalDateTime value) {
    var dateTimeStr = FORMATTER.format(value);
    if (dateTimeStr.length() == 19) {
      return dateTimeStr + ".000Z";
    }
    return dateTimeStr + "Z";
  }

  @FromJson
  public LocalDateTime fromJson(String value) {
    return LocalDateTime.parse(value, FORMATTER);
  }

}
