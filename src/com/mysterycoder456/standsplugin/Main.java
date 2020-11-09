package com.mysterycoder456.standsplugin;

import org.bukkit.plugin.java.JavaPlugin;

import com.mysterycoder456.standsplugin.commands.HelloCommand;
import com.mysterycoder456.standsplugin.commands.StandHelpCommands;
import com.mysterycoder456.standsplugin.listeners.JoinListener;
import com.mysterycoder456.standsplugin.listeners.StandUserListener;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		
//		Listeners
		new JoinListener(this);
		new StandUserListener(this);
		
//		Commands
		new HelloCommand(this);
		new StandHelpCommands(this);
	}
	
}
