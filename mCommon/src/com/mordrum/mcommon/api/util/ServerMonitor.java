package com.mordrum.mcommon.api.util;

import com.mordrum.mcommon.mCommon;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 6/24/13
 * Time: 9:43 PM
 */
public class ServerMonitor {

	static mCommon plugin;

	public static void Initialize(mCommon instance) {
		plugin = instance;
	}

	public static void PrintAllWorldInfo(CommandSender sender) {
		for (World w : plugin.getServer().getWorlds()) {
			final int numOfPlayers = w.getPlayers().size();
			final int numOfChunks = w.getLoadedChunks().length;
			final int totalEntities = w.getEntities().size();
			final int livingEntities = (w.getLivingEntities().size() - numOfPlayers);
			final int nonLivingEntities = (totalEntities - livingEntities - numOfPlayers);

			sender.sendMessage(ChatColor.GOLD + "World '" + w.getName() + "':");
			sender.sendMessage(ChatColor.GREEN + "  " + totalEntities + " Total Entities");
			sender.sendMessage(ChatColor.AQUA + "    " + numOfPlayers + " Players");
			sender.sendMessage(ChatColor.AQUA + "    " + livingEntities + " Living Entities");
			sender.sendMessage(ChatColor.AQUA + "    " + nonLivingEntities + " Non-Living Entities");
			sender.sendMessage(ChatColor.GREEN + "  " + numOfChunks + " Loaded Chunks");
		}
	}

	public static void PrintDetailedWorldInfo(CommandSender sender, String worldName) {
		final World w = plugin.getServer().getWorld(worldName);
		if (w == null) sender.sendMessage(ChatColor.GOLD + "World '" + worldName + "' does not exist");
		else {
			final int numOfPlayers = w.getPlayers().size();
			final int numOfChunks = w.getLoadedChunks().length;
			final int totalEntities = w.getEntities().size();
			final int livingEntities = (w.getLivingEntities().size() - numOfPlayers);
			final int nonLivingEntities = (totalEntities - livingEntities - numOfPlayers);

			final String humanReadablePlayerList = w.getPlayers().toString();
			humanReadablePlayerList.replaceAll(Pattern.compile("[CraftPlayer{name=").toString(), "");
			humanReadablePlayerList.replaceAll(Pattern.compile("}").toString(), "");

			sender.sendMessage(ChatColor.GOLD + "World '" + w.getName() + "':");
			sender.sendMessage(ChatColor.GREEN + "  " + totalEntities + " Total Entities");
			sender.sendMessage(ChatColor.AQUA + "    " + numOfPlayers + " Players");
			sender.sendMessage(ChatColor.LIGHT_PURPLE + "      " + humanReadablePlayerList);
			sender.sendMessage(ChatColor.AQUA + "    " + livingEntities + " Living Entities");
			sender.sendMessage(ChatColor.AQUA + "    " + nonLivingEntities + " Non-Living Entities");
			sender.sendMessage(ChatColor.GREEN + "  " + numOfChunks + " Loaded Chunks");
		}
	}
}
