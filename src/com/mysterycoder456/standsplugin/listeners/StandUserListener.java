package com.mysterycoder456.standsplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.mysterycoder456.standsplugin.Main;
import com.mysterycoder456.standsplugin.listeners.utils.Utils;

public class StandUserListener implements Listener {
	
	private Main plugin;
	private FileConfiguration config;
	
	public StandUserListener(Main plugin) {
		this.plugin = plugin;
		config = plugin.getConfig();
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEntityEvent e) {
		Player player = e.getPlayer();
		
		@SuppressWarnings("deprecation")
		ItemStack itemInPlayerHand = player.getItemInHand();
		
//		Star Platinum
		if (itemInPlayerHand.getType() == Material.IRON_INGOT) {
			double totalDamage = 16;
			int hitCount = 4;
			long attackDuration = 25;
			double knockbackMultiplier = Double.parseDouble(config.getString("knockbackMultiplier"));
			
			Entity rightClickedEntity = e.getRightClicked();
			
			Vector knockbackVector = player.getLocation().getDirection().normalize();
			knockbackVector.setX((knockbackVector.getX() * knockbackMultiplier) / hitCount);
			knockbackVector.setY((knockbackVector.getY() * knockbackMultiplier + 1) / hitCount);
			knockbackVector.setZ((knockbackVector.getZ() * knockbackMultiplier) / hitCount);
			
			Bukkit.broadcastMessage(Utils.chat("&a" + player.getName() + "'s Star Platinum: &7ORA ORA ORA ORA ORA!"));
			
//			Apply damage and knockback
			int id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

				@Override
				public void run() {
					((Damageable) rightClickedEntity).damage(totalDamage / hitCount);
					rightClickedEntity.setVelocity(knockbackVector);
				}
			}, 0, attackDuration / hitCount);
			
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

				@Override
				public void run() {
					Bukkit.getScheduler().cancelTask(id);
				}
			}, attackDuration);
		}
	}

}