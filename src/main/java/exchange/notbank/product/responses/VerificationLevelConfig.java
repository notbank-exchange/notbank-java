package exchange.notbank.product.responses;

import java.util.List;

import com.squareup.moshi.Json;

public class VerificationLevelConfig {
  @Json(name = "Level")
  public final Integer level;
  @Json(name = "LevelName")
  public final String levelName;
  @Json(name = "OMSId")
  public final Integer omsId;
  @Json(name = "Products")
  public final List<ProductConfiguration> products;

  public VerificationLevelConfig(Integer level, String levelName, Integer omsId, List<ProductConfiguration> products) {
    this.level = level;
    this.levelName = levelName;
    this.omsId = omsId;
    this.products = products;
  }

  @Override
  public String toString() {
    return "VerificationLevelConfig [level=" + level + ", levelName=" + levelName + ", omsId=" + omsId + "]";
  }
}
