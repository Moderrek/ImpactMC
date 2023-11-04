package com.impact.lib.api.server;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

public interface FakeAccessor {
  void onPlayerJoin(PlayerJoinEvent event);

  void getUser(Player player);
}