package com.mordrum.mchat.util.listeners;

import com.google.common.eventbus.Subscribe;
import com.mordrum.mchat.Main;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 6/10/13
 * Time: 9:46 PM
 */
public class ChatListener implements Listener {

	private Main plugin;

	public ChatListener(Main instance) {
		plugin = instance;
	}

	@Subscribe
	public void onPlayerChat(ChatEvent e) {
		//plugin.getProxy().get
	}
}
