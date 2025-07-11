package exchange.notbank.trading.adapters;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import exchange.notbank.trading.responses.SummaryMin;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

public class SummaryMinAdapterTest {
  private static Moshi moshi;

  @BeforeAll
  public static void beforeAll() {
    moshi = new Moshi.Builder().add(SummaryMin.class, new SummaryMinAdapter()).build();
  }

  @Test
  public void fromJsonList() throws IOException {
    ParameterizedType SummaryMinListType = Types.newParameterizedType(List.class, SummaryMin.class);
    var summaryListJsonAdapter = moshi.adapter(SummaryMinListType);
    var jsonStr = "[[1,BTCUSD,0,0,0,0],[2,USDTDAI,0,0,0,0],[3,XRPBTC,0.0000239300000000000000000000,0,0,0.00000000000000000000000000]]";
    summaryListJsonAdapter.fromJson(jsonStr);
  }

  @Test
  public void fromJson() throws IOException {
    var summaryMinJsonAdapter = moshi.adapter(SummaryMin.class);
    var jsonStr = "[1,BTCUSD,0,0,0,0]";
    summaryMinJsonAdapter.fromJson(jsonStr);
  }
}
