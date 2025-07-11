package exchange.notbank.users;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import exchange.notbank.core.adapters.LocalDatetimeAdapter;
import exchange.notbank.users.responses.UserInfo;
import com.squareup.moshi.Moshi;

public class UserInfoAdapterTest {
  private final String userInfoStr = "{\"UserId\":36,\"UserName\":\"9c09d23e-0451-42d6-a178-d4f28f5dfa99\",\"Email\":\"nino+8@dysopsis.com\",\"PasswordHash\":\"\",\"PendingEmailCode\":\"\",\"EmailVerified\":true,\"AccountId\":0,\"DateTimeCreated\":\"2025-04-25T16:49:21.843Z\",\"AffiliateId\":37,\"RefererId\":0,\"OMSId\":1,\"Use2FA\":false,\"Salt\":\"\",\"PendingCodeTime\":\"2025-04-25T16:49:21.843Z\",\"Locked\":false,\"LockedTime\":\"0001-01-01T00:00:00.000Z\",\"NumberOfFailedAttempt\":0,\"OperatorId\":1,\"MarginBorrowerEnabled\":false,\"UserLevel\":0}";

  @Test
  public void readsAndWritesLockedTimeWell() throws IOException {
    var moshi = new Moshi.Builder().add(new LocalDatetimeAdapter()).build();
    var userInfoAdapter = moshi.adapter(UserInfo.class);
    var userInfo = userInfoAdapter.fromJson(userInfoStr);
    var writtenUserInfo = userInfoAdapter.toJson(userInfo);
    assertTrue(writtenUserInfo.contains("0001-01-01T00:00:00.000Z"));
  }
}
