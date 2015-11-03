package com.penguinchao.etherticks;

import java.util.List;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;

public class Configs {
	private EtherTicks main;
	@SuppressWarnings("unchecked")
	public Configs(EtherTicks etherTicks) {
		main = etherTicks;
		Set<String> commandSections = getCommandSections();
		if(commandSections.size() == 0){
			main.getLogger().info("Task List is empty");
			return;
			//main.disablePlugin();
		}
		main.getLogger().info("Task List contains items. Setting up...");
		main.tasks = new Task[commandSections.size()];
		Integer i = 0;
		for(String key : commandSections){
			main.getLogger().info("Looking up task: "+key);
			String name = key;
			Integer interval;
			List<String> commands;
			Boolean allPlayers;
			commands = (List<String>) main.getConfig().getList("tasks."+name+".commands");
			main.getLogger().info("["+name+"] "+"Command Count: "+commands.size());
			interval = main.getConfig().getInt("tasks."+name+".interval");
			main.getLogger().info("["+name+"] "+"Interval: "+interval+" seconds");
			allPlayers = main.getConfig().getBoolean("tasks."+name+".allplayers");
			if(allPlayers){
				main.getLogger().info("["+name+"] "+"This task will fire for each player");
			}else{
				main.getLogger().info("["+name+"] "+"This task will not fire for each player");
			}
			main.getLogger().info("["+name+"] "+"Gathered information. Creating task.");
			main.tasks[i] = new Task(etherTicks, interval, commands, allPlayers, name);
			i++;
		}
		main.getLogger().info("Finished setting up Tasks!");
	}
	public Set<String> getCommandSections(){
		main.getLogger().info("Getting Command Sections");
		ConfigurationSection keySections = main.getConfig().getConfigurationSection("tasks");
		Set<String> returnMe = keySections.getKeys(false);
		main.getLogger().info("Successfully retrieved command sections");
		return returnMe;
	}
}
