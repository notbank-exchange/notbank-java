package exchange.notbank.core.websocket;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Types;

import exchange.notbank.core.MoshiFactory;
import exchange.notbank.core.WebAuthenticateResponse;
import exchange.notbank.core.responses.MessageFrame;
import exchange.notbank.core.responses.StandardResponse;
import exchange.notbank.core.websocket.callback.subscription.InstrumentIdAndAccountId;

public record WebsocketJsonAdapters(
    JsonAdapter<StandardResponse> standardResponseJsonAdapter,
    JsonAdapter<MessageFrame> messageFrameJsonAdapter,
    JsonAdapter<WebAuthenticateResponse> webAuthenticateResponseJsonAdapter,
    JsonAdapter<Map<String, Object>> mapStringObjectJsonAdapter,
    JsonAdapter<List<Map<String, Object>>> listOfMapStringObjectJsonAdapter,
    JsonAdapter<InstrumentIdAndAccountId> instrumentIdAndAccountIdJsonAdapter,
    JsonAdapter<List<Object>> objectListAdapter) {

  public static class Factory {
    public static WebsocketJsonAdapters create() {
      var moshi = MoshiFactory.create();
      var standardResponseJsonAdapter = moshi.adapter(StandardResponse.class);
      var messageFrameJsonAdapter = moshi.adapter(MessageFrame.class);
      var webAuthenticateResponseJsonAdapter = moshi.adapter(WebAuthenticateResponse.class);
      Type MapStringObjectType = Types.newParameterizedType(Map.class, String.class, Object.class);
      Type ListOfMapStringObjectType = Types.newParameterizedType(List.class, MapStringObjectType);
      JsonAdapter<Map<String, Object>> mapStringObjectJsonAdapter = moshi.adapter(MapStringObjectType);
      JsonAdapter<List<Map<String, Object>>> listOfMapStringObjectJsonAdapter = moshi
          .adapter(ListOfMapStringObjectType);
      var instrumentIdAndAccountIdJsonAdapter = moshi.adapter(InstrumentIdAndAccountId.class);
      ParameterizedType ObjectListType = Types.newParameterizedType(List.class, Object.class);
      JsonAdapter<List<Object>> objectListAdapter = moshi.adapter(ObjectListType);
      return new WebsocketJsonAdapters(
          standardResponseJsonAdapter,
          messageFrameJsonAdapter,
          webAuthenticateResponseJsonAdapter,
          mapStringObjectJsonAdapter,
          listOfMapStringObjectJsonAdapter,
          instrumentIdAndAccountIdJsonAdapter,
          objectListAdapter);
    }
  }

}
