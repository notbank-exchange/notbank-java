package exchange.notbank.account.responses;

import java.math.BigDecimal;

import exchange.notbank.subscription.responses.AccountEvent;
import exchange.notbank.users.constants.ReferenceType;
import exchange.notbank.users.constants.TransactionType;
import com.squareup.moshi.Json;

public class Transaction implements AccountEvent {
  @Json(name = "TransactionId")
  public final Integer transactionId;
  @Json(name = "ReferenceId")
  public final Integer referenceId;
  @Json(name = "OMSId")
  public final Integer omsId;
  @Json(name = "AccountId")
  public final Integer accountId;
  @Json(name = "CR")
  public final BigDecimal cr;
  @Json(name = "DR")
  public final BigDecimal dr;
  @Json(name = "Counterparty")
  public final Long counterparty;
  @Json(name = "TransactionType")
  public final TransactionType transactionType;
  @Json(name = "ReferenceType")
  public final ReferenceType referenceType;
  @Json(name = "ProductId")
  public final Integer productId;
  @Json(name = "Balance")
  public final BigDecimal balance;
  @Json(name = "TimeStamp")
  public final Long timeStamp;

  public Transaction(Integer transactionId, Integer referenceId, Integer omsId, Integer accountId, BigDecimal cr,
      BigDecimal dr, Long counterparty, TransactionType transactionType, ReferenceType referenceType, Integer productId,
      BigDecimal balance, Long timeStamp) {
    this.transactionId = transactionId;
    this.referenceId = referenceId;
    this.omsId = omsId;
    this.accountId = accountId;
    this.cr = cr;
    this.dr = dr;
    this.counterparty = counterparty;
    this.transactionType = transactionType;
    this.referenceType = referenceType;
    this.productId = productId;
    this.balance = balance;
    this.timeStamp = timeStamp;
  }

  @Override
  public String toString() {
    return "Transaction [transactionId=" + transactionId + ", referenceId=" + referenceId + ", omsId=" + omsId
        + ", accountId=" + accountId + ", cr=" + cr.stripTrailingZeros().toPlainString() + ", dr=" + dr.stripTrailingZeros().toPlainString() + ", counterparty=" + counterparty
        + ", transactionType=" + transactionType + ", referenceType=" + referenceType + ", productId=" + productId
        + ", balance=" + balance.stripTrailingZeros().toPlainString() + ", timeStamp=" + timeStamp + "]";
  }
}
