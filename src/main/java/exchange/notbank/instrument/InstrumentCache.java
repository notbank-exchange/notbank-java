package exchange.notbank.instrument;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import exchange.notbank.instrument.responses.Instrument;

public class InstrumentCache {
  public final Map<String, Instrument> instrumentsBySymbol;

  public InstrumentCache(Map<String, Instrument> instrumentIdBySymbol) {
    this.instrumentsBySymbol = instrumentIdBySymbol;
  }

  public static class Factory {
    public static InstrumentCache create() {
      return new InstrumentCache(new HashMap<>());
    }
  }

  public void updateInstruments(List<Instrument> instruments) {
    instruments.stream().forEach(instrument -> instrumentsBySymbol.put(instrument.symbol, instrument));
  }

  public Optional<Instrument> getInstrument(String symbol) {
    return Optional.ofNullable(instrumentsBySymbol.get(symbol));
  }
}
