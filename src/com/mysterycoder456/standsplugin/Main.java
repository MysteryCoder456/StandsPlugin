package com.mysterycoder456.standsplugin;

import org.bukkit.plugin.java.JavaPlugin;
import com.mysterycoder456.standsplugin.commands.HelloCommand;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		new HelloCommand(this);
	}
	
}
