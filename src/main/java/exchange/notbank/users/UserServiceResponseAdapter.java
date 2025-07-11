package exchange.notbank.users;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import exchange.notbank.core.ErrorHandler;
import exchange.notbank.core.NotbankException;
import exchange.notbank.core.StandardApResponseHandler;
import exchange.notbank.users.responses.Device;
import exchange.notbank.users.responses.UserInfo;
import io.vavr.control.Either;

public class UserServiceResponseAdapter {
  private final ErrorHandler errorHandler;
  private final JsonAdapter<UserInfo> userInfoJsonAdapter;
  private final JsonAdapter<List<Integer>> integerListAdapter;
  private final JsonAdapter<List<String>> stringListAdapter;
  private final JsonAdapter<List<Device>> deviceListAdapter;

  public UserServiceResponseAdapter(StandardApResponseHandler responseHandler, Moshi moshi) {
    this.errorHandler = ErrorHandler.Factory.createApErrorHandler(moshi);
    this.userInfoJsonAdapter = moshi.adapter(UserInfo.class);
    ParameterizedType IntegerListType = Types.newParameterizedType(List.class, Integer.class);
    this.integerListAdapter = moshi.adapter(IntegerListType);
    ParameterizedType StringListType = Types.newParameterizedType(List.class, String.class);
    this.stringListAdapter = moshi.adapter(StringListType);
    ParameterizedType DeviceListType = Types.newParameterizedType(List.class, Device.class);
    this.deviceListAdapter = moshi.adapter(DeviceListType);
  }

  public Either<NotbankException, Void> toNone(String jsonStr) {
    return errorHandler.toNone(jsonStr);
  }

  private <T> Either<NotbankException, T> handle(String jsonString, JsonAdapter<T> jsonAdapter) {
    return errorHandler.handleAndThen(jsonAdapter).apply(jsonString);
  }

  public Either<NotbankException, UserInfo> toUserInfo(String jsonStr) {
    return handle(jsonStr, userInfoJsonAdapter);
  }

  public Either<NotbankException, List<Device>> toDeviceList(String jsonStr) {
    return handle(jsonStr, deviceListAdapter);
  }

  public Either<NotbankException, List<Integer>> toIntegerList(String jsonStr) {
    return handle(jsonStr, integerListAdapter);
  }

  public Either<NotbankException, List<String>> toStringList(String jsonStr) {
    return handle(jsonStr, stringListAdapter);
  }
}