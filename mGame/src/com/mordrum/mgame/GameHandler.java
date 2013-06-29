package com.mordrum.mgame;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 6/8/13
 * Time: 1:36 PM
 */
public class GameHandler {

	private Map<String, GameTemplate> availableGames;
	private Map<String, GameInstanceTemplate> gameInstanceTemplates;
	private Map<String, List<GameInstanceTemplate>> gameInstances;

	protected void Initialize(Main instance) {
		availableGames = new HashMap<>(); //The games we have available
		gameInstanceTemplates = new HashMap<>(); //
		gameInstances = new HashMap<>();
	}

	public Set<String> getPlayableGames() {
		return availableGames.keySet();
	}

	public String AddPlayerToGame(ProxiedPlayer PlayerToAdd, String GameToJoin) {
		if (!availableGames.containsKey(GameToJoin)) return "Game not found!";

	}
}
