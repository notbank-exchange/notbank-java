package exchange.notbank.trading;

import exchange.notbank.trading.responses.SimpleUserAccount;
import exchange.notbank.trading.responses.SimpleUserAccounts;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimpleAccountsAdapter extends JsonAdapter<SimpleUserAccounts> {

  @Override
  public SimpleUserAccounts fromJson(JsonReader reader) throws IOException {
    List<SimpleUserAccount> accounts = new ArrayList<>();
    reader.beginArray();
    while (reader.hasNext()) {
      accounts.add(new SimpleUserAccount(reader.nextInt()));
    }
    reader.endArray();

    return new SimpleUserAccounts(accounts);
  }

  @Override
  public void toJson(JsonWriter writer, SimpleUserAccounts value) throws IOException {
    writer.beginArray();
    for (SimpleUserAccount account : value.accounts) {
      writer.value(account.AccountId);
    }
    writer.endArray();
  }
}
