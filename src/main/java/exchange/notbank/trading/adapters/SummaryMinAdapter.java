package exchange.notbank.trading.adapters;

import java.io.IOException;
import java.math.BigDecimal;

import exchange.notbank.trading.responses.SummaryMin;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;

public class SummaryMinAdapter extends JsonAdapter<SummaryMin> {

  @Override
  public SummaryMin fromJson(JsonReader reader) throws IOException {
    reader.beginArray();
    Integer instrumentId = reader.nextInt();
    reader.setLenient(true);
    String instrumentSymbol = reader.nextString();
    reader.setLenient(false);
    BigDecimal lastTradedPx = new BigDecimal(reader.nextString());
    BigDecimal rolling24HrPxChange = new BigDecimal(reader.nextString());
    BigDecimal rolling24HrPxChangePercent = new BigDecimal(reader.nextString());
    BigDecimal rolling24HrVolume = new BigDecimal(reader.nextString());
    reader.endArray();
    return new SummaryMin(
        instrumentId,
        instrumentSymbol,
        lastTradedPx,
        rolling24HrVolume,
        rolling24HrPxChange,
        rolling24HrPxChangePercent);
  }

  @Override
  public void toJson(JsonWriter writer, SummaryMin value) throws IOException {
    writer.beginArray();
    writer.value(value.instrumentId);
    writer.value(value.instrumentSymbol);
    writer.value(value.lastTradedPX);
    writer.value(value.rolling24HrPxChange);
    writer.value(value.rolling24HrPxChangePercent);
    writer.value(value.rolling24HrVolume);
    writer.endArray();
  }
}
