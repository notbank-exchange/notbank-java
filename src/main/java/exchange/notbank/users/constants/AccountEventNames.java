package exchange.notbank.users.constants;

import java.util.List;

public class AccountEventNames {
  public static final String WITHDRAW_TICKET_UPDATE_EVENT = "WithdrawTicketUpdateEvent";
  public static final String TRANSACTION_EVENT = "TransactionEvent";
  public static final String ORDER_TRADE_EVENT = "OrderTradeEvent";
  public static final String ORDER_STATE_EVENT = "OrderStateEvent";
  public static final String DEPOSIT_TICKET_UPDATE_EVENT = "DepositTicketUpdateEvent";
  public static final String ACCOUNT_INFO_UPDATE_EVENT = "AccountInfoUpdateEvent";
  public static final String ACCOUNT_POSITION_EVENT = "AccountPositionEvent";
  public static final String CANCEL_ORDER_REJECT_EVENT = "CancelOrderRejectEvent";
  public static final String DEPOSIT_EVENT = "DepositEvent";
  public static final List<String> ALL_EVENTS = List.of(
      WITHDRAW_TICKET_UPDATE_EVENT,
      TRANSACTION_EVENT,
      ORDER_TRADE_EVENT,
      ORDER_STATE_EVENT,
      DEPOSIT_TICKET_UPDATE_EVENT,
      ACCOUNT_INFO_UPDATE_EVENT,
      ACCOUNT_POSITION_EVENT,
      CANCEL_ORDER_REJECT_EVENT,
      DEPOSIT_EVENT);
}
