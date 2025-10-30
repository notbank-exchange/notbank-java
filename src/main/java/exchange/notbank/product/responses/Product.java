package exchange.notbank.product.responses;

import java.math.BigDecimal;

import exchange.notbank.product.constants.ProductType;
import com.squareup.moshi.Json;

public class Product {
  @Json(name = "OMSId")
  public final Integer omsId;
  @Json(name = "ProductId")
  public final Integer productId;
  @Json(name = "Product")
  public final String product;
  @Json(name = "ProductFullName")
  public final String productFullName;
  @Json(name = "MasterDataUniqueProductSymbol")
  public final String masterDataUniqueProductSymbol;
  @Json(name = "ProductType")
  public final ProductType productType;
  @Json(name = "DecimalPlaces")
  public final Integer decimalPlaces;
  @Json(name = "TickSize")
  public final BigDecimal tickSize;
  @Json(name = "DepositEnabled")
  public final Boolean depositEnabled;
  @Json(name = "WithdrawEnabled")
  public final Boolean withdrawEnabled;
  @Json(name = "NoFees")
  public final Boolean noFees;
  @Json(name = "IsDisabled")
  public final Boolean isDisabled;
  @Json(name = "MarginEnabled")
  public final Boolean marginEnabled;

  public Product(Integer omsId, Integer productId, String product, String productFullName,
      String masterDataUniqueProductSymbol, ProductType productType, Integer decimalPlaces, BigDecimal tickSize,
      Boolean depositEnabled, Boolean withdrawEnabled, Boolean noFees, Boolean isDisabled, Boolean marginEnabled) {
    this.omsId = omsId;
    this.productId = productId;
    this.product = product;
    this.productFullName = productFullName;
    this.masterDataUniqueProductSymbol = masterDataUniqueProductSymbol;
    this.productType = productType;
    this.decimalPlaces = decimalPlaces;
    this.tickSize = tickSize;
    this.depositEnabled = depositEnabled;
    this.withdrawEnabled = withdrawEnabled;
    this.noFees = noFees;
    this.isDisabled = isDisabled;
    this.marginEnabled = marginEnabled;
  }

  @Override
  public String toString() {
    return "Product [omsId=" + omsId + ", productId=" + productId + ", product=" + product + ", productFullName="
        + productFullName + ", masterDataUniqueProductSymbol=" + masterDataUniqueProductSymbol + ", productType="
        + productType + ", decimalPlaces=" + decimalPlaces + ", tickSize=" + tickSize.stripTrailingZeros().toPlainString()
        + ", depositEnabled="
        + depositEnabled + ", withdrawEnabled=" + withdrawEnabled + ", noFees=" + noFees + ", isDisabled=" + isDisabled
        + ", marginEnabled=" + marginEnabled + "]";
  }
}
