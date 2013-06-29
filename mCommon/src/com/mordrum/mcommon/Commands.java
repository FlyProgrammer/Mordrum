package com.mordrum.mcommon;

import com.mordrum.mcommon.api.util.ServerMonitor;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 6/24/13
 * Time: 9:56 PM
 */
public class Commands implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
		if (command.getName().equalsIgnoreCase("ekg")) {
			if (args.length == 0) {
				String[] availableCommands = new String[1];
				availableCommands[0] = ChatColor.GOLD + "worlds - Displays info for all worlds";
				commandSender.sendMessage(availableCommands);
				return true;
			} else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("worlds")) {
					ServerMonitor.PrintAllWorldInfo(commandSender);
					return true;
				}
				if (args[0].equalsIgnoreCase("world")) {
					commandSender.sendMessage(ChatColor.GOLD + "Please specify a world");
					return true;
				}
			} else if (args.length == 2) {
				if (args[0].equalsIgnoreCase("world")) {
					ServerMonitor.PrintDetailedWorldInfo(commandSender, args[1]);
					return true;
				}
			}
		}
		return true;
	}
}
