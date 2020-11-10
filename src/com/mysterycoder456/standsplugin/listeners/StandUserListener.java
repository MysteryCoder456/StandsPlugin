package com.mysterycoder456.standsplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
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
	
	
//	Stand Abilities
	
	private void starPlatinum(Player player, Entity rightClickedEntity) {
		
		double damagePerHit = Double.parseDouble(config.getString("SP_damagePerHit"));;
		int hitCount = Integer.parseInt(config.getString("SP_hitCount"));
		long attackDuration = 20;
		double knockbackMultiplier = Double.parseDouble(config.getString("SP_knockbackMultiplier"));
		
		Vector entityVelocity = rightClickedEntity.getVelocity();
		Vector knockbackVector = player.getLocation().getDirection();
		knockbackVector.setX((knockbackVector.getX() * knockbackMultiplier) / hitCount + entityVelocity.getX());
		knockbackVector.setY((knockbackVector.getY() * knockbackMultiplier + 1) / hitCount + entityVelocity.getY());
		knockbackVector.setZ((knockbackVector.getZ() * knockbackMultiplier) / hitCount + entityVelocity.getZ());
		
		Bukkit.broadcastMessage(Utils.chat("&a" + player.getDisplayName() + "'s Star Platinum: &7ORA ORA ORA ORA ORA!"));
		
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
	
	
	private void magiciansRed(Player player) {
		
		int attackPower = Integer.parseInt(config.getString("MR_attackPower"));
		double fbSpeedMultiplier = Double.parseDouble(config.getString("MR_fireballSpeed"));
		
		int id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

			@Override
			public void run() {
				Location fbSpawnLocation = player.getEyeLocation();
				
				Vector fbDirection = fbSpawnLocation.getDirection();
				fbDirection.multiply(fbSpeedMultiplier);
				
				Fireball fb = (Fireball) fbSpawnLocation.getWorld().spawnEntity(fbSpawnLocation, EntityType.FIREBALL);
				
				fb.setShooter(player);
				fb.setDirection(fbDirection);
				fb.setVelocity(fbDirection);
			}
			
		}, 0, 2);
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

			@Override
			public void run() {
				Bukkit.getScheduler().cancelTask(id);
			}
			
		}, attackPower * 2);
		
	}
	
	
//	Stand Ability listener on Entities
	@EventHandler
	public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent e) {
		
		Player player = e.getPlayer();
		Entity rightClickedEntity = e.getRightClicked();

		@SuppressWarnings("deprecation")
		ItemStack itemInPlayerHand = player.getItemInHand();
		
//		Star Platinum
		if (itemInPlayerHand.getType() == Material.IRON_INGOT) {
			starPlatinum(player, rightClickedEntity);
			itemInPlayerHand.setAmount(itemInPlayerHand.getAmount() - 1);
		}
		
		return;
	}
	
//	Stand Ability listener on Air
	@EventHandler
	public void onPlayerInteractAirEvent(PlayerInteractEvent e) {
		
		if (e.getAction() == Action.RIGHT_CLICK_AIR) {
		
			Player player = e.getPlayer();
	
			@SuppressWarnings("deprecation")
			ItemStack itemInPlayerHand = player.getItemInHand();
			
//			Magician's Red
			if (itemInPlayerHand.getType() == Material.BLAZE_ROD) {
				magiciansRed(player);
				itemInPlayerHand.setAmount(itemInPlayerHand.getAmount() - 1);
			}
		
		}
		
		return;
	}

}
