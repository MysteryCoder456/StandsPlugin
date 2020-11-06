package com.mysterycoder456.standsplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import com.mysterycoder456.standsplugin.Main;
import com.mysterycoder456.standsplugin.listeners.utils.Utils;

public class StandUserListener implements Listener {
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public StandUserListener(Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEntityEvent e) {
		double totalDamage = 16;
//		int damageCount = 6;
		Player player = e.getPlayer();
		Entity rightClickedEntity = e.getRightClicked();
		
		@SuppressWarnings("deprecation")
		ItemStack itemInPlayerHand = player.getItemInHand();
		
//		Star Platinum
		if (itemInPlayerHand.getType() == Material.IRON_INGOT) {
			Bukkit.broadcastMessage(Utils.chat("&a" + player.getName() + "'s Star Platinum: &7ORA ORA ORA ORA ORA!"));
			((Damageable) rightClickedEntity).damage(totalDamage);
			
//			TODO: Add repeated damage
//			for (int i = 0; i < damageCount; i++) {
//				((Damageable) rightClickedEntity).damage(totalDamage / damageCount);
//			}
		}
	}

}
