package com.mysterycoder456.standsplugin.commands;

import com.mysterycoder456.standsplugin.Main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelloCommand implements CommandExecutor {
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public HelloCommand(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("hello").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only a player can use this command!");
			return true;
		}
		
		Player p = (Player) sender;
		
		if (p.hasPermission("hello.use")) {
			p.sendMessage("Hello World!");
			return true;
		} else {
			p.sendMessage("You do not have permission to use this command!");
		}
		
		return false;
	}

}
