package exchange.notbank.instrument.responses;

import java.util.List;

import com.squareup.moshi.Json;

public class InstrumentVerificationLevelConfig {
  @Json(name = "Level")
  public final Integer level;
  @Json(name = "LevelName")
  public final String levelName;
  @Json(name = "OMSId")
  public final Integer omsId;
  @Json(name = "Instruments")
  public final List<InstrumentConfiguration> instruments;

  public InstrumentVerificationLevelConfig(Integer level, String levelName, Integer omsId, List<InstrumentConfiguration> instruments) {
    this.level = level;
    this.levelName = levelName;
    this.omsId = omsId;
    this.instruments = instruments;
  }

  @Override
  public String toString() {
    return "InstrumentVerificationLevelConfig [level=" + level + ", levelName=" + levelName + ", omsId=" + omsId + "]";
  }
}
