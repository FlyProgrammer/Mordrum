package com.mordrum.mchat;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import se.ranzdo.bukkit.methodcommand.Arg;
import se.ranzdo.bukkit.methodcommand.Command;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 3/15/13
 * Time: 7:25 PM
 */
public class CommandSet {

	@Command(identifier = "channel", description = "Channel top level command", onlyPlayers = true)
	public void getCurrentChannel(Player sender) {
		String currentChannel = mChat.activeChannel.get(sender.getName());
		sender.sendMessage(ChatColor.GOLD + "You are currently speaking in channel " + currentChannel);
	}

	@Command(identifier = "channel join", description = "Channel join command", onlyPlayers = true)
	public void JoinChannel(Player sender, @Arg(name = "channel") String chName) {
		if (mChat.channels.containsKey(chName)) { //If the channel exists
			if (sender.hasPermission("mChat." + chName + ".Speak")) { //If player has permission to join
				Channel ch = mChat.channels.get(chName);
				if (ch.getListeners().contains(sender.getName())) { //Check to make sure they aren't already in the channel
					sender.sendMessage(ChatColor.GOLD + "You are already in channel " + chName);
				} else { //Player is not in the channel, add them
					ch.addListener(sender.getName());
					sender.sendMessage(ChatColor.GOLD + "Joined channel " + chName);
				}
			} else { //Player does not have permission to join channel
				sender.sendMessage(ChatColor.GOLD + "You do not have permission to join channel " + chName);
			}
		} else { //Channel does not exist
			sender.sendMessage(ChatColor.GOLD + "Channel " + chName + " does not exist");
		}
	}

	@Command(identifier = "channel speak", description = "Channel speak command", onlyPlayers = true)
	public void SpeakInChannel(Player sender, @Arg(name = "channel") String chName) {
		if (mChat.channels.containsKey(chName)) {  //If the channel exists
			if (sender.hasPermission("mChat." + chName + ".Speak")) { //If player has permission to
				Channel ch = mChat.channels.get(chName);
				if (!ch.getListeners().contains(sender.getName())) { //Check if they have joined the channel prior
					ch.addListener(sender.getName());
				}
				if (mChat.activeChannel.get(sender.getName()).equalsIgnoreCase(chName)) {
					sender.sendMessage(ChatColor.GOLD + "You are already speaking in channel " + chName);
				} else {
					mChat.activeChannel.put(sender.getName(), chName);
					sender.sendMessage(ChatColor.GOLD + "Now speaking in channel " + chName);
				}
			} else { //Player does not have permission to speak in channel
				sender.sendMessage(ChatColor.GOLD + "You do not have permission to speak in channel " + chName);
			}
		} else { //Channel does not exist
			sender.sendMessage(ChatColor.GOLD + "Channel " + chName + " does not exist");
		}
	}

	@Command(identifier = "channel leave", description = "Channel speak command", onlyPlayers = true)
	public void LeaveChannel(Player sender, @Arg(name = "channel") String chName) {
		if (mChat.channels.containsKey(chName)) {  //If the channel exists
			if (sender.hasPermission("mChat." + chName + ".Leave")) { //If player has permission to
				Channel ch = mChat.channels.get(chName);
				if (ch.getListeners().contains(sender.getName())) {
					ch.removeListener(sender.getName());
					sender.sendMessage(ChatColor.GOLD + "You have left channel " + chName);
				} else {
					sender.sendMessage(ChatColor.GOLD + "You are not in channel " + chName);
				}
			} else { //Player does not have permission to speak in channel
				sender.sendMessage(ChatColor.GOLD + "You do not have permission to leave channel " + chName);
			}
		} else { //Channel does not exist
			sender.sendMessage(ChatColor.GOLD + "Channel " + chName + " does not exist");
		}
	}

	@Command(identifier = "channel list", description = "Channel list command", onlyPlayers = true)
	public void ListChannels(Player sender) {
		String channels = "";
		for (Channel ch : mChat.channels.values()) {
			if (!ch.getHidden()) {
				channels = channels + ch.getName() + ", ";
			}
		}
		channels = channels.substring(0, channels.length() - 2);
		sender.sendMessage(ChatColor.GOLD + "Available channels: " + channels);
	}

	@Command(identifier = "channel qm", description = "Channel quick message command", onlyPlayers = true)
	public void QuickMessage(Player sender, @Arg(name = "channel") String chName, @Arg(name = "message") String message) {
		if (mChat.channels.containsKey(chName)) {  //If the channel exists
			if (sender.hasPermission("mChat." + chName + ".Speak")) { //If player has permission to
				Channel ch = mChat.channels.get(chName);
				if (!ch.getListeners().contains(sender.getName())) { //Check if they have joined the channel prior
					ch.addListener(sender.getName());
				}
				String parsedString = mChat.ParseString(ch.getFormat(), sender);
				for (String s : ch.getListeners()) {
					Player p = Main.server.getPlayerExact(s);
					if (p == null) {
						ch.removeListener(s);
						mChat.activeChannel.remove(s);
					} else {
						p.sendMessage(parsedString);
					}
				}
			} else { //Player does not have permission to speak in channel
				sender.sendMessage(ChatColor.GOLD + "You do not have permission to speak in channel " + chName);
			}
		} else { //Channel does not exist
			sender.sendMessage(ChatColor.GOLD + "Channel " + chName + " does not exist");
		}
	}
}
