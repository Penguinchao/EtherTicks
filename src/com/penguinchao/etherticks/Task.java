package com.penguinchao.etherticks;

import java.util.List;

import org.bukkit.entity.Player;

public class Task {
	private static EtherTicks main;
	private static Integer interval;
	private List<String> commands;
	private Boolean allPlayers;
	private String name;
	public Task(EtherTicks etherTicks, Integer givenInterval, List<String> commands2, Boolean allPlayersGiven, String givenName){
		main = etherTicks;
		name = givenName;
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
			main.logExtraMessage(name+" contains no commands.");
			return;
		}else if(main.getServer().getOnlinePlayers().length == 0 && allPlayers){
			main.logExtraMessage("["+name+"] No players online. Doing nothing...");
			return;
		}
		for(String key : commands){
			fireCommand(key);
		}
	}
	@SuppressWarnings("deprecation")
	private void fireCommand(String singleCommand){
		if(allPlayers){
			for(Player player : main.getServer().getOnlinePlayers()){
				String playerName = player.getName();
				String fireMe = singleCommand.replace("@p", playerName);
				main.getServer().dispatchCommand(main.getServer().getConsoleSender(), fireMe);
			}
		}else{
			main.getServer().dispatchCommand(main.getServer().getConsoleSender(), singleCommand);
		}
	}
}
