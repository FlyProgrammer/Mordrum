package com.mordrum.moverseer.listeners;

import com.google.common.eventbus.Subscribe;
import com.mordrum.moverseer.IOHandler;
import com.mordrum.moverseer.Main;
import com.mordrum.moverseer.PlayerRecord;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 5/31/13
 * Time: 12:47 AM
 */
public class BanListener implements Listener {

	Main plugin;
	IOHandler IO;

	public BanListener(Main instance) {
		plugin = instance;

	}

	@Subscribe
	public void onPlayerAttemptLogin(LoginEvent e) { //Called when a player has logged in
		PendingConnection pc = e.getConnection(); //Get the connection
		if (plugin.getIO().playerRecordMap.containsKey(pc.getName())) { //Check to see if the record exists
			PlayerRecord pr = plugin.getIO().playerRecordMap.get(pc.getName()); //Fetch the player record
			if (pr.getBanState()) pc.disconnect(pr.getBanReason()); //If they are banned, disconnect them
		}
	}

	@Subscribe
	public void onServerPing(ProxyPingEvent e) {
		e.setResponse(new ServerPing(Byte.parseByte("71"), "1.5.2", "Mordrum - New Features Inbound", 35, 40));
	}
}
