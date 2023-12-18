package com.impact.lib.api.commandfile;

import com.impact.lib.api.commandfile.exception.CommandFileException;
import com.impact.lib.api.util.Empty;
import com.impact.lib.api.util.Result;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static com.impact.lib.api.util.Result.Ok;

public class CommandFiles {

  private CommandFiles() {}

  @Contract("_ -> new")
  public static @NotNull Result<Empty, CommandFileException> Load(String fileName) {
    return Ok();
  }

  @Contract("_ -> new")
  public static @NotNull Result<Empty, CommandFileException> Unload(String identifier) {
    return Ok();
  }

  @Contract("_ -> new")
  public static @NotNull Result<Empty, CommandFileException> Reload(String identifier) {
    return Ok();
  }

  @Contract("_ -> new")
  public static @NotNull Result<CommandFile, CommandFileException> Get(String identifier) {
    return Ok(new CommandFile());
  }

}
