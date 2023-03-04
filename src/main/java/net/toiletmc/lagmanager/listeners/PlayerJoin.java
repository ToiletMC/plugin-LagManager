package net.toiletmc.lagmanager.listeners;

import net.toiletmc.lagmanager.LagManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
    private LagManager plugin;

    public PlayerJoin(LagManager plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLogin(PlayerJoinEvent event) {
        plugin.getObserver().setSkip();
    }
}
