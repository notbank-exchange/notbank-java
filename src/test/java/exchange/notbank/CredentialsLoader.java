package exchange.notbank;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

public class CredentialsLoader {
  public static UserCredentials load(String path) {
    var userApiKeyJsonAdapter = getUserApiKeyJsonAdapter();
    try {
      String content = Files.readString(Path.of(path), StandardCharsets.UTF_8);
      return userApiKeyJsonAdapter.fromJson(content);
    } catch (IOException e) {
      throw new RuntimeException("unable to load keys file " + path, e);
    }
  }

  private static JsonAdapter<UserCredentials> getUserApiKeyJsonAdapter() {
    var moshi = new Moshi.Builder().build();
    return moshi.adapter(UserCredentials.class);
  }

  public static class UserCredentials {
    public final Integer userId;
    public final Integer accountId;
    public final String apiPublicKey;
    public final String apiSecretKey;

    public UserCredentials(Integer userId, Integer accountId, String apiPublicKey, String apiSecretKey) {
      this.userId = userId;
      this.accountId = accountId;
      this.apiPublicKey = apiPublicKey;
      this.apiSecretKey = apiSecretKey;
    }
  }
}
