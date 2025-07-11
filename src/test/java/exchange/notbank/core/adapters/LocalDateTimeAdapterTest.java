package exchange.notbank.core.adapters;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.squareup.moshi.Moshi;

public class LocalDateTimeAdapterTest {
  @Test
  public void parseZuluIso8601() throws IOException {
    var dateTimeIso8601 = "2025-04-22T14:39:44.079Z";
    var dummyJson = "{\"date\":\"" + dateTimeIso8601 + "\"}";
    var moshi = new Moshi.Builder().add(new LocalDatetimeAdapter()).build();
    var mapJsonAdapter = moshi.adapter(DummyClass.class);
    var readedDateTime = mapJsonAdapter.fromJson(dummyJson);
    var serializedDummyJson = mapJsonAdapter.toJson(readedDateTime);
    assertTrue(serializedDummyJson.contains(dateTimeIso8601));
  }

  public static class DummyClass {
    public final LocalDateTime date;

    public DummyClass(LocalDateTime date) {
      this.date = date;
    }

    @Override
    public String toString() {
      return "DummyClass [date=" + date + "]";
    }
  }
}
