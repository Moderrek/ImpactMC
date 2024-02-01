package com.impact.lib.api.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Consumer;

public class Result<T, E extends Throwable> {

  private final T ok;
  private final E err;

  public Result(T ok, E err) {
    this.ok = ok;
    this.err = err;
  }

  @Contract(" -> new")
  public static <E extends Throwable> @NotNull Result<Empty, E> Ok() {
    return new Result<>(Empty.create(), null);
  }

  @Contract(value = "_ -> new", pure = true)
  public static <T, E extends Throwable> @NotNull Result<T, E> Ok(T ok) {
    return new Result<>(ok, null);
  }

  @Contract(value = "_ -> new", pure = true)
  public static <T, E extends Throwable> @NotNull Result<T, E> Err(E err) {
    return new Result<>(null, err);
  }

  @Contract("_ -> new")
  public static <T> @NotNull Result<T, RuntimeException> Err(String msg) {
    return new Result<>(null, new RuntimeException(msg));
  }

  public static <T> Result<T, NullPointerException> ofNullable(T ok) {
    if (ok == null) return Result.Err(new NullPointerException("Presented value is null"));
    return Result.Ok(ok);
  }

  public boolean isOk() {
    return ok != null;
  }

  public T unwrap() throws E {
    if (ok != null) return ok;
    throw err;
  }

  public T unwrap_or(T default_value) {
    if (ok != null) return ok;
    return default_value;
  }

  public void ifOk(Consumer<T> consumer) {
    if (ok != null) consumer.accept(ok);
  }

  public void ifErr(Consumer<E> consumer) {
    if (err != null) consumer.accept(err);
  }

  public Optional<T> get() {
    return Optional.ofNullable(ok);
  }

  public Optional<E> err() {
    return Optional.ofNullable(err);
  }

  public T expect(@NotNull String message) {
    if (ok != null) return ok;
    throw new RuntimeException(message);
  }

}
