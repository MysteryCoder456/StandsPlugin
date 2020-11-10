package com.mysterycoder456.standsplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.mysterycoder456.standsplugin.Main;
import com.mysterycoder456.standsplugin.listeners.utils.Utils;

public class DeathListener implements Listener {
	
	private Main plugin;
	
	public DeathListener(Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		if (e.getEntity().getKiller() instanceof Player) {
			Player player = e.getEntity();
			Player killer = e.getEntity().getKiller();
			
			FileConfiguration config = plugin.getConfig();
			String deathMessage = config.getString("deathMessage").replace("<player>", player.getDisplayName()).replace("<killer>", killer.getDisplayName());
			
			e.setDeathMessage(Utils.chat(deathMessage));
		}
	}

}
