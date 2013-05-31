package com.mordrum.moverseer;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 5/31/13
 * Time: 1:00 AM
 * <p/>
 * This class is the "API" for mOverseer.
 */
public class mOverseer {

	public List<String> bannedPlayers;

	private Main plugin;
	private IOHandler IO;

	protected mOverseer(Main instance) {
		plugin = instance;
		IO = plugin.getIO();
	}

	protected void Initialize() {

	}

	protected void ShutDown() {

	}

	public Boolean isPlayerBanned(ProxiedPlayer PlayerToCheck) {
		return isPlayerBanned(PlayerToCheck.getName());
	}

	public Boolean isPlayerBanned(String PlayerToCheck) {

	}

	public Boolean setBanStateForPlayer(ProxiedPlayer PlayerToSet, Boolean BanState) {
		return setBanStateForPlayer(PlayerToSet.getName(), BanState);
	}

	public Boolean setBanStateForPlayer(String PlayerToSet, Boolean BanState) {

	}
}
