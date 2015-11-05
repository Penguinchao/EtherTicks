package com.penguinchao.etherticks;

import org.bukkit.plugin.java.JavaPlugin;

public class EtherTicks extends JavaPlugin {
	public Configs configs;
	public Task[] tasks;
	private Boolean extraLogging;
	private Boolean debugEnabled;
	@Override
	public void onEnable(){
		//Initialize Plugin
		saveDefaultConfig();
		extraLogging = getConfig().getBoolean("console-logging");
		debugEnabled = getConfig().getBoolean("debug-enabled");
		logExtraMessage("Extra Logging is enabled", false);
		logExtraMessage("Debug is Enabled", true);
		configs = new Configs(this);
	}
	public void logExtraMessage(String message, Boolean debug){
		if(debug && debugEnabled){
			getLogger().info("[DEBUG] "+message);
		}else if(extraLogging){
			getLogger().info(message);
		}
	}
}
