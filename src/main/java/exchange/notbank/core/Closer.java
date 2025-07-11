package exchange.notbank.core;

import java.util.ArrayList;
import java.util.List;

import io.vavr.control.Either;

public class Closer {
  private List<NamedCloseable> namedCloseables;
  private Boolean alreadyClosed;
  private List<String> errors;

  public Closer(List<NamedCloseable> namedCloseables, Boolean alreadyClosed, List<String> errors) {
    this.namedCloseables = namedCloseables;
    this.alreadyClosed = alreadyClosed;
    this.errors = errors;
  }

  public static class Factory {
    public static Closer create() {
      return new Closer(new ArrayList<>(), false, List.of());
    }
  }

  public Either<String, Void> addCloseable(String name, AutoCloseable closeable) {
    if (alreadyClosed) {
      return Either.left("unable to add closeable to closer. closer already closed its resources");
    }
    namedCloseables.add(new NamedCloseable(name, closeable));
    return Either.right(null);
  }

  public List<String> getCloseErrors() {
    return errors.stream().toList();
  }

  public void close() {
    if (alreadyClosed) {
      return;
    }
    errors = namedCloseables.stream()
        .map(this::closeNamedCloseable)
        .filter(Either::isLeft)
        .map(Either::getLeft)
        .toList();
  }

  private Either<String, Void> closeNamedCloseable(NamedCloseable namedCloseable) {
    try {
      namedCloseable.closeable.close();
      return Either.right(null);
    } catch (Exception e) {
      return Either.left("failed closing resource " + namedCloseable.name + ". " + e.getMessage());
    }
  }
}
