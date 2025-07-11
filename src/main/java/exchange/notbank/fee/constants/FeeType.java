package exchange.notbank.fee.constants;

import com.squareup.moshi.Json;

public enum FeeType {
  @Json(name = "Flat")
  FLAT,
  @Json(name = "Ladder")
  LADDER,
  @Json(name = "Exemption")
  EXEMPTION,
  @Json(name = "AdminFee")
  ADMIN_FEE,
  @Json(name = "AffiliateFee")
  AFFILIATE_FEE,
  @Json(name = "MakerFee")
  MAKER_FEE,
  @Json(name = "TakerFee")
  TAKER_FEE,
  @Json(name = "FlatPegtoProduct")
  FLAT_PEG_TO_PRODUCT;
}
