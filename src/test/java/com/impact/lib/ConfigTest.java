package com.impact.lib;

import com.impact.lib.api.config.ConfigField;
import com.impact.lib.api.config.ConfigFile;
import com.impact.lib.api.config.ConfigType;
import org.junit.jupiter.api.Test;

public class ConfigTest {

  @ConfigFile
  public static class PlayerConfig {
    @ConfigField
    public String name = "abc";
  }

  @ConfigFile(fileName = "hello world", type = ConfigType.JSON)
  public static class ExampleConfig extends PlayerConfig {
    @ConfigField
    public boolean isPlayer = true;
    @ConfigField
    public boolean invalid = false;
  }

  @Test
  public void findFields() {
    ExampleConfig config = new ExampleConfig();
    try {
      ImpactConfig.Load(config);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
    ImpactConfig.Reload(config);
  }

}
