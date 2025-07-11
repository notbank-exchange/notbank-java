package exchange.notbank.trading.adapters;

import java.util.List;

import exchange.notbank.trading.constants.OrderFlag;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.ToJson;

public class OrdersFlagAdapter {
  public static List<OrderFlag> ORDER_FLAGS = List.of(OrderFlag.values());

  @ToJson
  String toJson(List<OrderFlag> flags) {
    return String.join(", ", flags.stream().map(flag -> flag.name).toList());
  }

  @FromJson
  List<OrderFlag> fromJson(String value) {
    return List.of(value.split(",")).stream().map(String::strip).map(this::getOrderFlag).toList();
  }

  private OrderFlag getOrderFlag(String value) {
    var flagByName = ORDER_FLAGS.stream().filter(flag -> flag.name.equals(value)).findFirst();
    if (flagByName.isPresent()) {
      return flagByName.get();
    }
    var flagByCode = ORDER_FLAGS.stream().filter(flag -> flag.code.toString().equals(value)).findFirst();
    if (flagByCode.isPresent()) {
      return flagByCode.get();
    }
    throw new JsonDataException("no order flag with matching code or name: " + value);
  }
}
