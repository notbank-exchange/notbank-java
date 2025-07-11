package exchange.notbank.trading.responses;

import java.util.List;

public class Level2Snapshot {
    public final List<InstrumentSnapshot> instruments;

    public Level2Snapshot(List<InstrumentSnapshot> instruments){
        this.instruments = instruments;
    }
}
