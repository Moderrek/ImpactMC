package com.impact.lib.api.commandfile.exception;

import java.text.MessageFormat;

public class CommandFileNotLoadedException extends CommandFileException {

  private final String identifier;

  public CommandFileNotLoadedException(String identifier) {
    super(MessageFormat.format("CommandFile with identifier {0} is not loaded", identifier));
    this.identifier = identifier;
  }

  public String getIdentifier() {
    return identifier;
  }
}
