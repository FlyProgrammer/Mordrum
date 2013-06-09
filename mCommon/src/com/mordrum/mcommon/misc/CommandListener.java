package com.mordrum.mcommon.misc;

import com.mordrum.mcommon.mCommon;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 6/9/13
 * Time: 5:18 PM
 */
public class CommandListener implements Listener {

	mCommon plugin;

	public CommandListener(mCommon instance) {
		plugin = instance;
	}

	public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
		String command = e.getMessage();
		command = command.split(" ")[0];
		command = command.substring(1);
		if ((command.equalsIgnoreCase("stop") && plugin.getPluginConfig().stopDisabled) ||
				(command.equalsIgnoreCase("reload") && plugin.getPluginConfig().reloadDisabled)) {
			Player p = e.getPlayer();
			p.sendMessage(ChatColor.RED + "~~~CLUSTERFUCK ALERT~~~");
			p.sendMessage(ChatColor.GOLD + "A crisis has been averted! /" + command + " causes extreme autism");
			e.setCancelled(true);
		}
	}
}
