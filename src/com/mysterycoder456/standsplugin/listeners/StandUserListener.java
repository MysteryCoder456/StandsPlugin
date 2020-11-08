package com.mysterycoder456.standsplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
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
	
	
//	Stand Abilities
	
	private void starPlatinum(Player player, Entity rightClickedEntity) {
		
		double damagePerHit = 4.5;
		int hitCount = 5;
		long attackDuration = 20;
		double knockbackMultiplier = Double.parseDouble(config.getString("knockbackMultiplier"));
		
		Vector entityVelocity = rightClickedEntity.getVelocity();
		Vector knockbackVector = player.getLocation().getDirection();
		knockbackVector.setX((knockbackVector.getX() * knockbackMultiplier) / hitCount + entityVelocity.getX());
		knockbackVector.setY((knockbackVector.getY() * knockbackMultiplier + 1) / hitCount + entityVelocity.getY());
		knockbackVector.setZ((knockbackVector.getZ() * knockbackMultiplier) / hitCount + entityVelocity.getZ());
		
		Bukkit.broadcastMessage(Utils.chat("&a" + player.getName() + "'s Star Platinum: &7ORA ORA ORA ORA ORA!"));
		
//		Apply damage and knockback
		int id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

			@Override
			public void run() {
				((Damageable) rightClickedEntity).damage(damagePerHit);
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
	
	
	private void magiciansRed(Player player, Entity rightClickedEntity) {
		
		int attackPower = 15;
		double fwSpeedMultiplier = 0.1;
		
		Location fwSpawnLocation = player.getLocation();
		Firework fw = (Firework) fwSpawnLocation.getWorld().spawnEntity(fwSpawnLocation, EntityType.FIREWORK);
		FireworkMeta fwm = fw.getFireworkMeta();
		
		fwm.setPower(attackPower);
		fwm.addEffect(FireworkEffect.builder().withColor(Color.ORANGE).flicker(true).build());
		
		fw.setFireworkMeta(fwm);
		
		Vector fwDirection = fwSpawnLocation.getDirection();
		fwDirection.multiply(fwSpeedMultiplier);
		
		fw.setFallDistance(0);
		fw.setVelocity(fwDirection);
		
	}
	
	
//	Stand Ability listener
	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEntityEvent e) {
		Player player = e.getPlayer();
		Entity rightClickedEntity = e.getRightClicked();

		@SuppressWarnings("deprecation")
		ItemStack itemInPlayerHand = player.getItemInHand();
		
//		Star Platinum
		if (itemInPlayerHand.getType() == Material.IRON_INGOT) {
			starPlatinum(player, rightClickedEntity);
			itemInPlayerHand.setAmount(itemInPlayerHand.getAmount() - 1);
		}
		
//		Magician's Red
		else if (itemInPlayerHand.getType() == Material.BLAZE_ROD) {
			magiciansRed(player, rightClickedEntity);
			itemInPlayerHand.setAmount(itemInPlayerHand.getAmount() - 1);
		}
		
		return;
	}

}
