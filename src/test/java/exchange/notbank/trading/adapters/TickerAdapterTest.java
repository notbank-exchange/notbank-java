package exchange.notbank.trading.adapters;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import exchange.notbank.trading.responses.Ticker;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

public class TickerAdapterTest {
  private static Moshi moshi;

  @BeforeAll
  public static void beforeAll() {
    moshi = new Moshi.Builder().add(new TickerAdapter()).build();
  }

  @Test
  public void fromJsonList() throws IOException {
    ParameterizedType listType = Types.newParameterizedType(List.class, Ticker.class);
    var adapter = moshi.adapter(listType);
    var jsonStr = "[[1749099360000,100,100,100,100,0,100,101,66,1749099300000],[1749099420000,100,100,100,100,0,100,101,66,1749099360000],[1749099480000,100,100,100,100,0,100,101,66,1749099420000]]";
    adapter.fromJson(jsonStr);
  }

  @Test
  public void fromJson() throws IOException {
    var adapter = moshi.adapter(Ticker.class);
    var jsonStr = "[1749099360000,100,100,100,100,0,100,101,66,1749099300000]";
    adapter.fromJson(jsonStr);
  }
}
