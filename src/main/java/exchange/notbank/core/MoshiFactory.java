package exchange.notbank.core;

import exchange.notbank.core.adapters.BigDecimalAdapter;
import exchange.notbank.core.adapters.BigIntegerAdapter;
import exchange.notbank.core.adapters.LocalDatetimeAdapter;
import exchange.notbank.core.adapters.MessageTypeAdapter;
import exchange.notbank.core.adapters.UUIDAdapter;
import exchange.notbank.fee.adapters.IntOrderTypeAdapter;
import exchange.notbank.fee.adapters.IntSideAdapter;
import exchange.notbank.fee.adapters.MakerTakerAdapter;
import exchange.notbank.trading.SimpleAccountsAdapter;
import exchange.notbank.trading.adapters.LastTradeAdapter;
import exchange.notbank.trading.adapters.Level2Adapter;
import exchange.notbank.trading.adapters.Level2SnapshotAdapter;
import exchange.notbank.trading.adapters.OrdersFlagAdapter;
import exchange.notbank.trading.adapters.SocketTradeAdapter;
import exchange.notbank.trading.adapters.SummaryMinAdapter;
import exchange.notbank.trading.adapters.TickerAdapter;
import exchange.notbank.trading.responses.Level2Snapshot;
import exchange.notbank.trading.responses.SimpleUserAccounts;
import exchange.notbank.trading.responses.SummaryMin;
import com.squareup.moshi.Moshi;

public class MoshiFactory {
  public static Moshi create() {
    return new Moshi.Builder()
        .add(new BigDecimalAdapter())
        .add(new UUIDAdapter())
        .add(new MessageTypeAdapter())
        .add(new BigIntegerAdapter())
        .add(new LocalDatetimeAdapter())
        .add(SimpleUserAccounts.class, new SimpleAccountsAdapter())
        .add(SummaryMin.class, new SummaryMinAdapter())
        .add(Level2Snapshot.class, new Level2SnapshotAdapter())
        .add(new Level2Adapter())
        .add(new TickerAdapter())
        .add(new LastTradeAdapter())
        .add(new OrdersFlagAdapter())
        .add(new IntSideAdapter())
        .add(new IntOrderTypeAdapter())
        .add(new MakerTakerAdapter())
        .add(new SocketTradeAdapter())
        .build();
  }
}
