package com.impact.lib.api.registry.exception;

import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

public class UnregisteredObjectException extends RuntimeException {

  public UnregisteredObjectException(@NotNull Class<?> clazz) {
    super(MessageFormat.format("Object {0} is unregistered!", clazz.getSimpleName()));
  }

}
