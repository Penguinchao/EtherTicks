package com.penguinchao.etherticks;

import java.util.List;

import org.bukkit.entity.Player;

public class Task {
	private static EtherTicks main;
	private static Integer interval;
	private List<String> commands;
	private Boolean allPlayers;
	private String name;
	private Boolean requirePermission;
	public Task(EtherTicks etherTicks, Integer givenInterval, List<String> commands2, Boolean allPlayersGiven, String givenName, Boolean givenRequirePermission){
		main = etherTicks;
		name = givenName;
		requirePermission = givenRequirePermission;
		if(givenInterval == 0){
			main.getLogger().info("Interval for task "+name+" is 0; using 1 second instead");
			interval = 20;
		}else{
			interval = givenInterval * 20; //Convert ticks to seconds
		}
		commands = commands2;
		allPlayers = allPlayersGiven;
		createRunnable();
	}
	private void createRunnable(){
		main.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
			public void run(){
				fireAllCommands();
				createRunnable();
			}
		}, interval);
	}
	@SuppressWarnings("deprecation")
	private void fireAllCommands(){
		if(commands.size() == 0 || commands == null){
			main.logExtraMessage(name+" contains no commands.", false);
			return;
		}else if(main.getServer().getOnlinePlayers().isEmpty() && allPlayers){
			main.logExtraMessage("["+name+"] No players online. Doing nothing...", false);
			return;
		}
		if(allPlayers){
			main.logExtraMessage("Executing task ["+name+"] for all players with permission node: etherticks.task."+name, true);
		}
		for(String key : commands){
			main.logExtraMessage("Executing Command: "+key, true);
			fireCommand(key);
		}
	}
	@SuppressWarnings("deprecation")
	private void fireCommand(String singleCommand){
		if(allPlayers){
			for(Player player : main.getServer().getOnlinePlayers()){
				if(player.hasPermission("etherticks.task."+name) || requirePermission == false){
					//Player has permission
					main.logExtraMessage(player.getDisplayName()+" has permission to receive command", true);
					String playerName = player.getName();
					String fireMe = singleCommand.replace("@p", playerName);
					main.getServer().dispatchCommand(main.getServer().getConsoleSender(), fireMe);
				}else{
					//Player does not have permission
					main.logExtraMessage(player.getDisplayName()+" does not have permission to receive command", true);
				}
			}
		}else{
			main.getServer().dispatchCommand(main.getServer().getConsoleSender(), singleCommand);
		}
	}
}
