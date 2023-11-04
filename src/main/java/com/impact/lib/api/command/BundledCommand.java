package com.impact.lib.api.command;

import java.util.List;

public abstract class BundledCommand {

  private List<SubCommand> subCommandList;

  public List<SubCommand> getSubCommandList() {
    return subCommandList;
  }

  protected void setSubCommands(List<SubCommand> subCommandList) {

  }

}
