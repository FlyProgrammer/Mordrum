package com.mordrum.moverseer.listeners;

import com.google.common.eventbus.Subscribe;
import com.mordrum.moverseer.IOHandler;
import com.mordrum.moverseer.Main;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
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
	public void onPlayerJoin(PostLoginEvent e) { //Called when a player has logged in
		ProxiedPlayer p = e.getPlayer();

	}
}
