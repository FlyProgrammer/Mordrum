package com.mordrum.mgame;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 6/8/13
 * Time: 1:44 PM
 */
public abstract class GameInstanceTemplate {

	Integer instanceID;
	String gameName;
	String[] currentPlayers;
	Map<String, Map<String, Integer>> scoreMap;

	public abstract void EndGame();

	public abstract Map<String, Map<String, Integer>> GetScore();

	public abstract Map<String, Integer> GetPlayerScore();

	public abstract void AddPlayerToGame(ProxiedPlayer PlayerToAdd);

	public abstract void RemovePlayerFromGame(ProxiedPlayer PlayerToRemove);

	public abstract void PauseGame();

	public abstract void ResumeGame();
}
