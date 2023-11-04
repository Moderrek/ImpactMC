package com.impact.lib.api.registry;

import com.impact.lib.api.block.CustomBlock;
import com.impact.lib.api.command.MCommand;
import com.impact.lib.api.gui.Gui;
import com.impact.lib.api.item.CustomItem;

public abstract class ImpactRegistries {

  public static final ImpactRegistry<MCommand<?>> COMMAND;
  public static final ImpactRegistry<CustomItem> CUSTOM_ITEM;
  public static final ImpactRegistry<CustomBlock> CUSTOM_BLOCK;
  public static final ImpactRegistry<Gui> GUI;

  static {
    COMMAND = new CommandRegistry();
    CUSTOM_ITEM = new CustomItemRegistry();
    CUSTOM_BLOCK = new CustomBlockRegistry();
    GUI = new GuiRegistry();
  }

  private ImpactRegistries() {
  }

}
