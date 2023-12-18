package com.impact.lib.api.commandfile.exception;

import java.text.MessageFormat;

public class CommandFileNotFoundException extends CommandFileException {

  private final String fileName;

  public CommandFileNotFoundException(String fileName) {
    super(MessageFormat.format("CommandFile Not Found {0}", fileName));
    this.fileName = fileName;
  }

  public String getFileName() {
    return fileName;
  }
}
