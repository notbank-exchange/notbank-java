package exchange.notbank.trading.responses;

import java.math.BigDecimal;

public class InstrumentSnapshot {
    public final Integer MDUpdateID;
    public final Integer NUniqueAccounts;
    public final Long ActionDateTime;
    public final Integer ActionType;
    public final BigDecimal LastTradePrice;
    public final Integer NumberOfOrders;
    public final BigDecimal Price;
    public final Integer ProductPairCode;
    public final BigDecimal Quantity;
    public final Integer Side;

    public InstrumentSnapshot(
        Integer MDUpdateID,
        Integer NUniqueAccounts,
        Long ActionDateTime,
        Integer ActionType,
        BigDecimal LastTradePrice,
        Integer NumberOfOrders,
        BigDecimal Price,
        Integer ProductPairCode,
        BigDecimal Quantity,
        Integer Side
    ){
        this.MDUpdateID = MDUpdateID;
        this.NUniqueAccounts = NUniqueAccounts;
        this.ActionType = ActionType;
        this.ActionDateTime = ActionDateTime;
        this.LastTradePrice = LastTradePrice;
        this.NumberOfOrders = NumberOfOrders;
        this.Price = Price;
        this.ProductPairCode = ProductPairCode;
        this.Quantity = Quantity;
        this.Side = Side;
    }
}
