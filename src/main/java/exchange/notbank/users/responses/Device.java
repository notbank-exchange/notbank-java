package exchange.notbank.users.responses;

import com.squareup.moshi.Json;

public class Device {
  @Json(name = "HashCode")
  public final Integer hashCode;
  @Json(name = "Location")
  public final String location;
  @Json(name = "DeviceName")
  public final String deviceName;
  @Json(name = "IpAddress")
  public final String ipAddress;
  @Json(name = "UserId")
  public final Integer userId;
  @Json(name = "IsTrusted")
  public final Boolean isTrusted;
  @Json(name = "ExpirationTime")
  public final Long expirationTime;

  public Device(Integer hashCode, String location, String deviceName, String ipAddress, Integer userId,
      Boolean isTrusted, Long expirationTime) {
    this.hashCode = hashCode;
    this.location = location;
    this.deviceName = deviceName;
    this.ipAddress = ipAddress;
    this.userId = userId;
    this.isTrusted = isTrusted;
    this.expirationTime = expirationTime;
  }

  @Override
  public String toString() {
    return "Device [hashCode=" + hashCode + ", location=" + location + ", deviceName=" + deviceName + ", ipAddress="
        + ipAddress + ", userId=" + userId + ", isTrusted=" + isTrusted + ", expirationTime=" + expirationTime + "]";
  }
}
