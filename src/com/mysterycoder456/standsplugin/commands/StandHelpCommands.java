package com.mysterycoder456.standsplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mysterycoder456.standsplugin.Main;

public class StandHelpCommands implements CommandExecutor {
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public StandHelpCommands(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("starplatinum").setExecutor(this);
		plugin.getCommand("magiciansred").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only a player can use this command!");
			return true;
		}
		
		Player p = (Player) sender;
		String cmdName = cmd.getName().toLowerCase();
		
		if (cmdName.equals("starplatinum")) {
			if(p.hasPermission("starplatinum.use")) {
				p.sendMessage("Star Platinum's ability is an incredibly powerful punch!");
				p.sendMessage("To use it, right-click any mob or player with an Iron Ingot.");
				p.sendMessage("COST: 1 Iron Ingot");
				
				return true;
			} else {
				p.sendMessage("You do not have permission to use this command!");
			}
		}
		
		else if (cmdName.equals("magiciansred")) {
			if(p.hasPermission("magiciansred.use")) {
				p.sendMessage("Magician's Red's ability is throwing fireballs!");
				p.sendMessage("To use it, right-click the air with a Blaze Rod in the direction you want to throw the fireball.");
				p.sendMessage("COST: 1 Blaze Rod");
				
				return true;
			} else {
				p.sendMessage("You do not have permission to use this command!");
			}
		}
		
		return false;
	}

}
