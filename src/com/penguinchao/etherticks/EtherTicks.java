package com.penguinchao.etherticks;

import org.bukkit.plugin.java.JavaPlugin;

public class EtherTicks extends JavaPlugin {
	public Configs configs;
	public Task[] tasks;
	private Boolean extraLogging;
	@Override
	public void onEnable(){
		//Initialize Plugin
		saveDefaultConfig();
		extraLogging = getConfig().getBoolean("console-logging");
		logExtraMessage("Extra Logging is enabled");
		configs = new Configs(this);
	}
	public void logExtraMessage(String message){
		if(extraLogging){
			getLogger().info(message);
		}
	}
}
