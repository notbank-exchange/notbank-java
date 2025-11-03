package exchange.notbank.users;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;

import exchange.notbank.core.EndpointCategory;
import exchange.notbank.core.NotbankConnection;
import exchange.notbank.core.NotbankException;
import exchange.notbank.core.ParamBuilder;
import exchange.notbank.users.constants.Endpoints;
import exchange.notbank.users.paramBuilders.GetUserAccountsParamBuilder;
import exchange.notbank.users.paramBuilders.GetUserDevicesParamBuilder;
import exchange.notbank.users.responses.Device;
import io.vavr.control.Either;

public class UserService {
  private final Supplier<CompletableFuture<NotbankConnection>> getNotbankConnection;
  private final UserServiceResponseAdapter responseAdapter;

  public UserService(Supplier<CompletableFuture<NotbankConnection>> getNotbankConnection,
      UserServiceResponseAdapter userResponseAdapter) {
    this.getNotbankConnection = getNotbankConnection;
    this.responseAdapter = userResponseAdapter;
  }

  private <T> CompletableFuture<T> requestPost(String endpoint, ParamBuilder paramBuilder,
      Function<String, Either<NotbankException, T>> deserializeFn) {
    return getNotbankConnection.get()
        .thenCompose(
            connection -> connection.requestPost(EndpointCategory.AP, endpoint, paramBuilder, deserializeFn));
  }

  /**
   * https://apidoc.notbank.exchange/#getuseraccounts
   */
  public CompletableFuture<List<Integer>> getUserAccounts(GetUserAccountsParamBuilder paramBuilder) {
    return requestPost(Endpoints.GET_USER_ACCOUNTS, paramBuilder, responseAdapter::toIntegerList);
  }

  /**
   * https://apidoc.notbank.exchange/#getuserdevices
   */
  public CompletableFuture<List<Device>> getUserDevices(GetUserDevicesParamBuilder paramBuilder) {
    return requestPost(Endpoints.GET_USER_DEVICES, paramBuilder, responseAdapter::toDeviceList);
  }

}
