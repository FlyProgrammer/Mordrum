package com.mordrum.mdota.commands;

import com.mordrum.mdota.mDota;
import com.mordrum.mdota.util.DotaGame;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamChatCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			if (args.length > 0) {
				String playerName = sender.getName();
				if (mDota.playerList.containsKey(playerName)) {
					DotaGame game = mDota.activeGames.get(mDota.playerList.get(playerName));
					StringBuilder sb = new StringBuilder();
					sb.append(ChatColor.GREEN);
					sb.append(playerName);
					sb.append(": ");
					sb.append(StringUtils.join(args, " "));
					game.BroadCastMessage(sb.toString(), game.getPlayerTeam().get(playerName));
					return true;
				}
			} else return false; //They didn't supply a message for their team
		} else {
			sender.sendMessage(mDota.tag + "You cannot use this command from the console");
			return true;
		}
		return false;
	}
}
