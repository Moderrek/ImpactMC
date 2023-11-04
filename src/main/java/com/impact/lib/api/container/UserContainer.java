package com.impact.lib.api.container;

import java.util.Optional;
import java.util.UUID;

public class UserContainer<E extends User> implements IUserContainer<E, UUID> {

  @Override
  public Optional<E> get(UUID uid) {
    return Optional.empty();
  }

  @Override
  public E load(UUID uid) {
    return null;
  }

  @Override
  public boolean remove(E user) {
    return false;
  }

  @Override
  public boolean save(E user) {
    return false;
  }
}
