package com.mysterycoder456.standsplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.mysterycoder456.standsplugin.Main;
import com.mysterycoder456.standsplugin.listeners.utils.Utils;

public class JoinListener implements Listener {
	
	private Main plugin;
	
	public JoinListener(Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		FileConfiguration config = plugin.getConfig();
		
		if (!player.hasPlayedBefore()) {
			e.setJoinMessage(Utils.chat(config.getString("firstTimeJoinMessage").replace("<player>", player.getDisplayName())));
		}
		else {
			e.setJoinMessage(Utils.chat(config.getString("joinMessage").replace("<player>", player.getDisplayName())));
		}
	}

}
