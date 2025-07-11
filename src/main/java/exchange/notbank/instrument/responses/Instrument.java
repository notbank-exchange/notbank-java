package exchange.notbank.instrument.responses;

import java.math.BigDecimal;

import exchange.notbank.instrument.constants.InstrumentStatus;
import com.squareup.moshi.Json;

public class Instrument {
  @Json(name = "OMSId")
  public final Integer omsId;
  @Json(name = "InstrumentId")
  public final Integer instrumentId;
  @Json(name = "Symbol")
  public final String symbol;
  @Json(name = "Product1")
  public final Integer product1;
  @Json(name = "Product1Symbol")
  public final String product1Symbol;
  @Json(name = "Product2")
  public final Integer product2;
  @Json(name = "Product2Symbol")
  public final String product2Symbol;
  @Json(name = "InstrumentType")
  public final String instrumentType;
  @Json(name = "VenueInstrumentId")
  public final Integer venueInstrumentId;
  @Json(name = "VenueId")
  public final Integer venueId;
  @Json(name = "SortIndex")
  public final Integer sortIndex;
  @Json(name = "SessionStatus")
  public final InstrumentStatus sessionStatus;
  @Json(name = "PreviousSessionStatus")
  public final InstrumentStatus previousSessionStatus;
  @Json(name = "SessionStatusDateTime")
  public final String sessionStatusDateTime;
  @Json(name = "SelfTradePrevention")
  public final Boolean selfTradePrevention;
  @Json(name = "QuantityIncrement")
  public final BigDecimal quantityIncrement;
  @Json(name = "PriceIncrement")
  public final BigDecimal priceIncrement;
  @Json(name = "MinimumQuantity")
  public final BigDecimal minimumQuantity;
  @Json(name = "MinimumPrice")
  public final BigDecimal minimumPrice;
  @Json(name = "VenueSymbol")
  public final String venueSymbol;
  @Json(name = "IsDisable")
  public final Boolean isDisable;
  @Json(name = "MasterDataId")
  public final Integer masterDataId;
  @Json(name = "PriceCollarThreshold")
  public final BigDecimal priceCollarThreshold;
  @Json(name = "PriceCollarPercent")
  public final BigDecimal priceCollarPercent;
  @Json(name = "PriceCollarEnabled")
  public final Boolean priceCollarEnabled;
  @Json(name = "PriceFloorLimit")
  public final BigDecimal priceFloorLimit;
  @Json(name = "PriceFloorLimitEnabled")
  public final Boolean priceFloorLimitEnabled;
  @Json(name = "PriceCeilingLimit")
  public final BigDecimal priceCeilingLimit;
  @Json(name = "PriceCeilingLimitEnabled")
  public final Boolean priceCeilingLimitEnabled;
  @Json(name = "CreateWithMarketRunning")
  public final Boolean createWithMarketRunning;
  @Json(name = "AllowOnlyMarketMakerCounterParty")
  public final Boolean allowOnlyMarketMakerCounterParty;
  @Json(name = "PriceCollarIndexDifference")
  public final BigDecimal priceCollarIndexDifference;
  @Json(name = "PriceCollarConvertToOtcEnabled")
  public final Boolean priceCollarConvertToOtcEnabled;
  @Json(name = "PriceCollarConvertToOtcClientUserId")
  public final Integer priceCollarConvertToOtcClientUserId;
  @Json(name = "PriceCollarConvertToOtcAccountId")
  public final Integer priceCollarConvertToOtcAccountId;
  @Json(name = "PriceCollarConvertToOtcThreshold")
  public final BigDecimal priceCollarConvertToOtcThreshold;
  @Json(name = "OtcConvertSizeThreshold")
  public final BigDecimal otcConvertSizeThreshold;
  @Json(name = "OtcConvertSizeEnabled")
  public final Boolean otcConvertSizeEnabled;
  @Json(name = "OtcTradesPublic")
  public final Boolean otcTradesPublic;
  @Json(name = "PriceTier")
  public final Integer priceTier;

  public Instrument(Integer omsId, Integer instrumentId, String symbol, Integer product1, String product1Symbol,
      Integer product2,
      String product2Symbol, String instrumentType, Integer venueInstrumentId, Integer venueId, Integer sortIndex,
      InstrumentStatus sessionStatus, InstrumentStatus previousSessionStatus, String sessionStatusDateTime,
      Boolean selfTradePrevention, BigDecimal quantityIncrement, BigDecimal priceIncrement, BigDecimal minimumQuantity,
      BigDecimal minimumPrice, String venueSymbol, Boolean isDisable, Integer masterDataId,
      BigDecimal priceCollarThreshold, BigDecimal priceCollarPercent, Boolean priceCollarEnabled,
      BigDecimal priceFloorLimit, Boolean priceFloorLimitEnabled, BigDecimal priceCeilingLimit,
      Boolean priceCeilingLimitEnabled, Boolean createWithMarketRunning, Boolean allowOnlyMarketMakerCounterParty,
      BigDecimal priceCollarIndexDifference, Boolean priceCollarConvertToOtcEnabled,
      Integer priceCollarConvertToOtcClientUserId, Integer priceCollarConvertToOtcAccountId,
      BigDecimal priceCollarConvertToOtcThreshold, BigDecimal otcConvertSizeThreshold, Boolean otcConvertSizeEnabled,
      Boolean otcTradesPublic, Integer priceTier) {
    this.omsId = omsId;
    this.instrumentId = instrumentId;
    this.symbol = symbol;
    this.product1 = product1;
    this.product1Symbol = product1Symbol;
    this.product2 = product2;
    this.product2Symbol = product2Symbol;
    this.instrumentType = instrumentType;
    this.venueInstrumentId = venueInstrumentId;
    this.venueId = venueId;
    this.sortIndex = sortIndex;
    this.sessionStatus = sessionStatus;
    this.previousSessionStatus = previousSessionStatus;
    this.sessionStatusDateTime = sessionStatusDateTime;
    this.selfTradePrevention = selfTradePrevention;
    this.quantityIncrement = quantityIncrement;
    this.priceIncrement = priceIncrement;
    this.minimumQuantity = minimumQuantity;
    this.minimumPrice = minimumPrice;
    this.venueSymbol = venueSymbol;
    this.isDisable = isDisable;
    this.masterDataId = masterDataId;
    this.priceCollarThreshold = priceCollarThreshold;
    this.priceCollarPercent = priceCollarPercent;
    this.priceCollarEnabled = priceCollarEnabled;
    this.priceFloorLimit = priceFloorLimit;
    this.priceFloorLimitEnabled = priceFloorLimitEnabled;
    this.priceCeilingLimit = priceCeilingLimit;
    this.priceCeilingLimitEnabled = priceCeilingLimitEnabled;
    this.createWithMarketRunning = createWithMarketRunning;
    this.allowOnlyMarketMakerCounterParty = allowOnlyMarketMakerCounterParty;
    this.priceCollarIndexDifference = priceCollarIndexDifference;
    this.priceCollarConvertToOtcEnabled = priceCollarConvertToOtcEnabled;
    this.priceCollarConvertToOtcClientUserId = priceCollarConvertToOtcClientUserId;
    this.priceCollarConvertToOtcAccountId = priceCollarConvertToOtcAccountId;
    this.priceCollarConvertToOtcThreshold = priceCollarConvertToOtcThreshold;
    this.otcConvertSizeThreshold = otcConvertSizeThreshold;
    this.otcConvertSizeEnabled = otcConvertSizeEnabled;
    this.otcTradesPublic = otcTradesPublic;
    this.priceTier = priceTier;
  }

  @Override
  public String toString() {
    return "Instrument [omsId=" + omsId + ", instrumentId=" + instrumentId + ", symbol=" + symbol + ", product1="
        + product1
        + ", product1Symbol=" + product1Symbol + ", product2=" + product2 + ", product2Symbol=" + product2Symbol
        + ", instrumentType=" + instrumentType
        + ", venueInstrumentId=" + venueInstrumentId + ", venueId=" + venueId + ", sortIndex=" + sortIndex
        + ", sessionStatus=" + sessionStatus + ", previousSessionStatus=" + previousSessionStatus
        + ", sessionStatusDateTime=" + sessionStatusDateTime + ", selfTradePrevention=" + selfTradePrevention
        + ", quantityIncrement=" + quantityIncrement.toPlainString() + ", priceIncrement="
        + priceIncrement.toPlainString() + ", minimumQuantity="
        + minimumQuantity.toPlainString() + ", minimumPrice=" + minimumPrice.toPlainString() + ", venueSymbol="
        + venueSymbol + ", isDisable="
        + isDisable + ", masterDataId=" + masterDataId + ", priceCollarThreshold="
        + priceCollarThreshold.toPlainString()
        + ", priceCollarPercent=" + priceCollarPercent.toPlainString() + ", priceCollarEnabled=" + priceCollarEnabled
        + ", priceFloorLimit=" + priceFloorLimit.toPlainString() + ", priceFloorLimitEnabled=" + priceFloorLimitEnabled
        + ", priceCeilingLimit=" + priceCeilingLimit.toPlainString() + ", priceCeilingLimitEnabled="
        + priceCeilingLimitEnabled
        + ", createWithMarketRunning=" + createWithMarketRunning + ", allowOnlyMarketMakerCounterParty="
        + allowOnlyMarketMakerCounterParty + ", priceCollarIndexDifference="
        + priceCollarIndexDifference.toPlainString()
        + ", priceCollarConvertToOtcEnabled=" + priceCollarConvertToOtcEnabled
        + ", priceCollarConvertToOtcClientUserId=" + priceCollarConvertToOtcClientUserId
        + ", priceCollarConvertToOtcAccountId=" + priceCollarConvertToOtcAccountId
        + ", priceCollarConvertToOtcThreshold=" + priceCollarConvertToOtcThreshold.toPlainString()
        + ", otcConvertSizeThreshold="
        + otcConvertSizeThreshold.toPlainString() + ", otcConvertSizeEnabled=" + otcConvertSizeEnabled
        + ", otcTradesPublic="
        + otcTradesPublic + ", priceTier=" + priceTier + "]";
  }
}
