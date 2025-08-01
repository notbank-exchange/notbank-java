package exchange.notbank.wallet.responses;

import java.math.BigDecimal;
import java.util.UUID;

import com.squareup.moshi.Json;

import exchange.notbank.wallet.constants.Direction;
import exchange.notbank.wallet.constants.TransactionStatus;
import exchange.notbank.wallet.constants.TransactionSubType;
import exchange.notbank.wallet.constants.TransactionType;

public class Transaction {
  public final UUID id;
  public final String currency;
  public final Direction direction;
  public final BigDecimal amount;
  public final BigDecimal fee;
  public final BigDecimal balance;
  public final String address;
  public final String hash;
  public final TransactionType type;
  @Json(name = "sub_type")
  public final TransactionSubType subType;
  public final TransactionStatus status;
  @Json(name = "created_at")
  public final String createdAt;
  @Json(name = "updated_at")
  public final String updatedAt;

  public Transaction(UUID id, String currency, Direction direction, BigDecimal amount, BigDecimal fee,
      BigDecimal balance, String address, String hash, TransactionType type, TransactionSubType subType,
      TransactionStatus status, String createdAt, String updatedAt) {
    this.id = id;
    this.currency = currency;
    this.direction = direction;
    this.amount = amount;
    this.fee = fee;
    this.balance = balance;
    this.address = address;
    this.hash = hash;
    this.type = type;
    this.subType = subType;
    this.status = status;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  @Override
  public String toString() {
    return "Transaction [id=" + id + ", currency=" + currency + ", direction=" + direction + ", amount=" + amount
        + ", fee=" + fee + ", balance=" + balance + ", address=" + address + ", hash=" + hash + ", type=" + type
        + ", subType=" + subType + ", status=" + status + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
  }
}
