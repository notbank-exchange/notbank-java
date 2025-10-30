package exchange.notbank.wallet.constants;

import com.squareup.moshi.Json;

public enum BankAccountKind {
  @Json(name = "corriente")
  CORRIENTE,
  @Json(name = "vista")
  VISTA,
  @Json(name = "ahorro")
  AHORRO,
  @Json(name = "electronic_checkbook")
  ELECTRONIC_CHECKBOOK,
  @Json(name = "ar_cbu")
  AR_CBU,
  @Json(name = "ar_cvu")
  AR_CVU,
  @Json(name = "ar_alias")
  AR_ALIAS,
  @Json(name = "br_corriente_fisica")
  BR_CORRIENTE_FISICA,
  @Json(name = "br_simple_fisica")
  BR_SIMPLE_FISICA,
  @Json(name = "br_corriente_juridica")
  BR_CORRIENTE_JURIDICA,
  @Json(name = "br_poupanca_fisica")
  BR_POUPANCA_FISICA,
  @Json(name = "br_poupanca_juridica")
  BR_POUPANCA_JURIDICA,
  @Json(name = "br_caixa_facil")
  BR_CAIXA_FACIL,
  @Json(name = "br_pix")
  BR_PIX;
}
