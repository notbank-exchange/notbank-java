package exchange.notbank.trading.adapters;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import exchange.notbank.core.adapters.BigDecimalAdapter;
import exchange.notbank.core.adapters.BigIntegerAdapter;
import exchange.notbank.core.adapters.UUIDAdapter;
import exchange.notbank.trading.responses.Order;
import com.squareup.moshi.Moshi;

public class OrderFlagAdapterTest {
  private static final String orderJson = " {\n" + //
      "    \"Side\": \"Buy\",\n" + //
      "    \"OrderId\": 6459,\n" + //
      "    \"Price\": 1.3638,\n" + //
      "    \"Quantity\": 0.0,\n" + //
      "    \"DisplayQuantity\": 0.0,\n" + //
      "    \"Instrument\": 9,\n" + //
      "    \"Account\": 7,\n" + //
      "    \"AccountName\": \"sample_user\",\n" + //
      "    \"OrderType\": \"Limit\",\n" + //
      "    \"ClientOrderId\": 0,\n" + //
      "    \"OrderState\": \"FullyExecuted\",\n" + //
      "    \"ReceiveTime\": 1678263954878,\n" + //
      "    \"ReceiveTimeTicks\": 638138607548778980,\n" + //
      "    \"LastUpdatedTime\": 1678263960020,\n" + //
      "    \"LastUpdatedTimeTicks\": 638138607600197010,\n" + //
      "    \"OrigQuantity\": 18307.63,\n" + //
      "    \"QuantityExecuted\": 18307.63,\n" + //
      "    \"GrossValueExecuted\": 24966.439664,\n" + //
      "    \"ExecutableValue\": 0.0,\n" + //
      "    \"AvgPrice\": 1.3637177321149706433874837977,\n" + //
      "    \"CounterPartyId\": 0,\n" + //
      "    \"ChangeReason\": \"Trade\",\n" + //
      "    \"OrigOrderId\": 6455,\n" + //
      "    \"OrigClOrdId\": 0,\n" + //
      "    \"EnteredBy\": 6,\n" + //
      "    \"UserName\": \"sample_superuser\",\n" + //
      "    \"IsQuote\": false,\n" + //
      "    \"InsideAsk\": 1.3646,\n" + //
      "    \"InsideAskSize\": 8959.3,\n" + //
      "    \"InsideBid\": 1.3638,\n" + //
      "    \"InsideBidSize\": 10776.98,\n" + //
      "    \"LastTradePrice\": 1.3636,\n" + //
      "    \"RejectReason\": \"\",\n" + //
      "    \"IsLockedIn\": false,\n" + //
      "    \"CancelReason\": \"\",\n" + //
      "    \"OrderFlag\": \"AddedToBook, RemovedFromBook\",\n" + //
      "    \"UseMargin\": false,\n" + //
      "    \"StopPrice\": 0.0,\n" + //
      "    \"PegPriceType\": \"Last\",\n" + //
      "    \"PegOffset\": 0.0,\n" + //
      "    \"PegLimitOffset\": 0.0,\n" + //
      "    \"IpAddress\": \"69.10.61.175\",\n" + //
      "    \"IPv6a\": 0,\n" + //
      "    \"IPv6b\": 0,\n" + //
      "    \"ClientOrderIdUuid\": null,\n" + //
      "    \"OMSId\": 1\n" + //
      "  }";

  @Test
  public void fromJson() throws IOException {
    var moshi = new Moshi.Builder()
        .add(new OrdersFlagAdapter())
        .add(new BigDecimalAdapter())
        .add(new UUIDAdapter())
        .add(new BigIntegerAdapter())
        .build();
    var orderAdapter = moshi.adapter(Order.class);
    orderAdapter.fromJson(orderJson);
  }
}
