package com.mordrum.mcommon;

import com.mordrum.mcommon.microfeatures.ChatEventListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Costco
 * Date: 03/06/13
 * Time: 6:33 PM
 */
public class WaitForPluginsListener implements Listener {

	mCommon plugin;

	public WaitForPluginsListener(mCommon instance) {
		plugin = instance;
	}

	@EventHandler
	public void onPluginEnabled(PluginEnableEvent e) {
		String pluginName = e.getPlugin().getName();
		if (pluginName.equalsIgnoreCase("mchat") && plugin.getConfig().getBoolean("KillChatMessages")) //If mCommon is enabled, kill chat events
			mCommon.server.getPluginManager().registerEvents(new ChatEventListener(), plugin);
	}
}
