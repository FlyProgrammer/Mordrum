package com.mordrum.mgame;

import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 6/8/13
 * Time: 1:38 PM
 */
public abstract class GameTemplate {

	Integer maxInstances = -1;
	String gameServer;
	String gameChannel;
	Integer maxPlayers;

	public abstract void NewGame();

	public abstract void AddPlayerToGame(ProxiedPlayer PlayerToAdd);

	public abstract void RemovePlayerFromGame(ProxiedPlayer PlayerToRemove);

	public abstract String[] GetInstructions();

	public abstract String[] GetRules();

}
