package exchange.notbank.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class HMACTest {
  @Test
  public void testSign1() throws NotbankException {
    var hmac = new HMAC(
        "c6d7799743c01403eabf995e5b9f5748",
        "fb13ef8c3c136fbdd8b75f2a3cdd8f9b",
        "9");
    var signature = hmac.sign("767e47ce1c982d48bd6529a81c6f621c0db3a8db8f41fc58559787f0b8bb99a8");
    assertEquals("466869ba901a276ee0dddf31a4a7497748fc5c846c36582273fd5ab14af30582", signature.get());
  }

  @Test
  public void testSign2() throws NotbankException {
    var hmac = new HMAC(
        "c6d7799743c01403eabf995e5b9f5748",
        "fb13ef8c3c136fbdd8b75f2a3cdd8f9b",
        "9");
    var signature = hmac.sign("1633445739");
    assertEquals("767e47ce1c982d48bd6529a81c6f621c0db3a8db8f41fc58559787f0b8bb99a8", signature.get());
  }

  @Test
  public void testSignThrowsOnEmptyKey() throws NotbankException {
    var hmac = new HMAC(
        "c6d7799743c01403eabf995e5b9f5748",
        "",
        "9");
    var signature = hmac.sign("1633445739");
    assertTrue(signature.isLeft());
  }
}