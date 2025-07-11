package exchange.notbank.core;

import java.util.function.Function;

public class Composer {
  public static <A, B, C> Function<A, C> compose(Function<B, C> f1, Function<A, B> f2) {
    return f1.compose(f2);
  }

  public static <A, B, C> Function<A, C> andThen(Function<A, B> f1, Function<B, C> f2) {
    return f1.andThen(f2);
  }
}
