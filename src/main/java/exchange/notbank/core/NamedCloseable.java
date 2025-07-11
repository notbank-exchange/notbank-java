package exchange.notbank.core;

public class NamedCloseable {
  public final String name;
  public final AutoCloseable closeable;

  public NamedCloseable(String name, AutoCloseable closeable) {
    this.name = name;
    this.closeable = closeable;
  }
}
