package exchange.notbank.core;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import exchange.notbank.core.NotbankException.ErrorType;

import io.vavr.control.Either;

/**
 * Generates the credential for authenticated communication with the server
 */
public class HMAC {
  private static String HMAC_SHA256 = "HmacSHA256";
  private static Charset charset = Charset.forName("US-ASCII");
  private final String apiKey;
  private final String apiSecret;
  private final String userId;

  public HMAC(String apiKey, String apiSecret, String userId) {
    this.apiKey = apiKey;
    this.apiSecret = apiSecret;
    this.userId = userId;
  }

  public String getApiKey() {
    return apiKey;
  }

  public Either<NotbankException, String> sign(String nonce) {
    try {
      var message = nonce + userId + apiKey;
      SecretKeySpec keySpec = new SecretKeySpec(apiSecret.getBytes(charset), HMAC_SHA256);
      Mac sha256Hmac = Mac.getInstance(HMAC_SHA256);
      sha256Hmac.init(keySpec);
      byte[] macData = sha256Hmac.doFinal(message.getBytes(charset));
      var signature = HexFormat.of().formatHex(macData);
      return Either.right(signature);
    } catch (NoSuchAlgorithmException e) {
      var error = NotbankException.Factory.create(ErrorType.CONFIGURATION_ERROR, "unable to build auth signature. " + e.getMessage());
      return Either.left(error);
    } catch (InvalidKeyException | IllegalArgumentException e) {
      var error = NotbankException.Factory.create(ErrorType.EXECUTION_ERROR, "unable to build auth signature. " + e.getMessage());
      return Either.left(error);
    }
  }
}
