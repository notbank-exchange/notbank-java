package exchange.notbank.trading.adapters;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import exchange.notbank.trading.responses.InstrumentSnapshot;
import exchange.notbank.trading.responses.Level2Snapshot;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;

public class Level2SnapshotAdapter extends JsonAdapter<Level2Snapshot> {

    @Override
    public Level2Snapshot fromJson(JsonReader reader) throws IOException {
        List<InstrumentSnapshot> instruments = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            reader.beginArray();

            Integer MDUpdateID = reader.nextInt();
            Integer NUniqueAccounts = reader.nextInt();
            Long ActionDateTime = reader.nextLong();
            Integer ActionType = reader.nextInt();
            BigDecimal LastTradePrice = new BigDecimal(reader.nextDouble());
            Integer NumberOfOrders = reader.nextInt();
            BigDecimal Price = new BigDecimal(reader.nextDouble());
            Integer ProductPairCode = reader.nextInt();
            BigDecimal Quantity = new BigDecimal(reader.nextDouble());
            Integer Side = reader.nextInt();

            instruments.add(new InstrumentSnapshot(
                MDUpdateID,
                NUniqueAccounts,
                ActionDateTime,
                ActionType,
                LastTradePrice,
                NumberOfOrders,
                Price,
                ProductPairCode,
                Quantity,
                Side
            ));

            reader.endArray();
        }
        reader.endArray();

        return new Level2Snapshot(instruments);
    }
    
    @Override
    public void toJson(JsonWriter writer, Level2Snapshot value) throws IOException {
        writer.beginArray();
        for (InstrumentSnapshot s : value.instruments) {
            writer.beginArray();
            writer.value(s.MDUpdateID);
            writer.value(s.NUniqueAccounts);
            writer.value(s.ActionDateTime);
            writer.value(s.ActionType);
            writer.value(s.LastTradePrice);
            writer.value(s.NumberOfOrders);
            writer.value(s.Price);
            writer.value(s.ProductPairCode);
            writer.value(s.Quantity);
            writer.value(s.Side);
            writer.endArray();
        }
        writer.endArray();
    }
}
