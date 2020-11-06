package com.mysterycoder456.standsplugin;

import org.bukkit.plugin.java.JavaPlugin;

import com.mysterycoder456.standsplugin.commands.HelloCommand;
import com.mysterycoder456.standsplugin.listeners.JoinListener;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		new JoinListener(this);
		new HelloCommand(this);
	}
	
}
