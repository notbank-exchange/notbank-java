package exchange.notbank.core;

public class UserCredentials {
  public final Integer userId;
  public final String apiPublicKey;
  public final String apiSecretKey;

  public UserCredentials(Integer userId, String apiPublicKey, String apiSecretKey) {
    this.userId = userId;
    this.apiPublicKey = apiPublicKey;
    this.apiSecretKey = apiSecretKey;
  }
}
