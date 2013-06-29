package com.mordrum.mdota.listeners;

import com.mordrum.mdota.mDota;
import com.mordrum.mdota.util.DotaGame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kitteh.tag.PlayerReceiveNameTagEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 6/25/13
 * Time: 2:06 PM
 */
public class NameplateListener implements Listener {

	mDota plugin;

	public NameplateListener(mDota instance) {
		plugin = instance;
	}

	@EventHandler
	public void onPlayerReceiveNameplate(PlayerReceiveNameTagEvent e) {
		int gameID = mDota.playerList.get(e.getNamedPlayer().getName());
		DotaGame dg = mDota.activeGames.get(gameID);
		int teamID = dg.getPlayerTeam().get(e.getNamedPlayer().getName());
		if (teamID == 1) { //Red team
			e.setTag(dg.getTeam1().getChatColor() + e.getNamedPlayer().getDisplayName());
		} else if (teamID == 2) { //Blue team
			e.setTag(dg.getTeam1().getChatColor() + e.getNamedPlayer().getDisplayName());
		} else { //Some other team
			mDota.log.severe("Something went wrong when trying to give a nameplate to " + e.getPlayer().getName());
		}
	}
}
