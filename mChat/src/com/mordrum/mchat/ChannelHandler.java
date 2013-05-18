package com.mordrum.mchat;

import net.milkbowl.vault.chat.Chat;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 3/10/13
 * Time: 5:38 PM
 */
public class ChannelHandler implements Listener {

	private Main plugin;
	public static Chat chat = null;

	public ChannelHandler(Main instance) {
		plugin = instance;
		setupPermissions();

		Channel chG = new Channel("global", ChatColor.WHITE, "{vote}{townname}{permprefix}{permsuffix}{playermodname}{color}: {msg}", false, "G");
		Channel chM = new Channel("mod", ChatColor.GRAY, "{color}[{nick}]-{permprefix}{permsuffix}}{playermodname}{color}: {msg}", true, "MOD");
		Channel chA = new Channel("admin", ChatColor.RED, "{color}[{nick}]-{permprefix}{permsuffix}{playermodname}{color}: {msg}", true, "ADM");

		mChat.RegisterNewChannel(chG);
		mChat.RegisterNewChannel(chM);
		mChat.RegisterNewChannel(chA);

		RegisterDefaultReplacements();
	}

	/*@EventHandler
	public void onPlayerChat(PlayerChatEvent e) {
		if (e.isCancelled()) {
			return;
		}
			Channel ch = getActiveChannel(e.getPlayer().getName());
			String parsedString = mChat.ParseString(ch.getFormat(), e.getPlayer(), e);
			ArrayList<String> listenerList = ch.getListeners();
			for (String s : ch.getListeners()) {
				Player p = Main.server.getPlayerExact(s);
				if (p == null) {
					ch.removeListener(s);
					mChat.activeChannel.remove(s);
				}
				else {
					p.sendMessage(parsedString);
				}
			}
			e.setCancelled(true);
	}*/

	@EventHandler
	public void onPlayerChat(PlayerChatEvent e) {
		if (e.isCancelled()) {
			return;
		}
		Channel ch = getActiveChannel(e.getPlayer().getName());
		String parsedString = mChat.ParseString(ch.getFormat(), e.getPlayer(), e);
		ArrayList<String> listenerList = ch.getListeners();
		for (String s : ch.getListeners()) {
			Player p = Main.server.getPlayerExact(s);
			if (p == null) {
				ch.removeListener(s);
				mChat.activeChannel.remove(s);
			} else {
				p.sendMessage(parsedString);
			}
		}
		e.setCancelled(true);
	}

	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
		Player sender = e.getPlayer();
		//Main.server.broadcastMessage(e.getMessage());
		for (Channel ch : mChat.channels.values()) {
			//Main.server.broadcastMessage("/" + ch.getNickname().toLowerCase());
			if (e.getMessage().trim().split(" ")[0].toLowerCase().startsWith("/" + ch.getNickname().toLowerCase()) && e.getMessage().trim().split(" ")[0].length() == ch.getNickname().length() + 1) {
				if (!sender.hasPermission("mchat." + ch.getName() + ".Speak"))
					sender.sendMessage(ChatColor.GOLD + "You do not have permission to speak in channel " + ch.getName());
				else {
					if (!ch.getListeners().contains(sender.getName())) { //Check to make sure they aren't already in the channel
						ch.addListener(sender.getName());
					}
					if (e.getMessage().trim().length() == ch.getNickname().length() + 1) {
						mChat.activeChannel.put(sender.getName(), ch.getName());
						sender.sendMessage(ChatColor.GOLD + "Now speaking in channel " + ch.getName());
					} else {
						String message = e.getMessage().substring(ch.getNickname().length() + 1).trim();
						String parsedString = ch.getFormat();
						parsedString = parsedString.replaceAll(Pattern.quote("color"), ch.getColor() + "");
						parsedString = parsedString.replaceAll(Pattern.quote("nick"), ch.getNickname());
						parsedString = mChat.ParseString(parsedString, sender);
						parsedString = parsedString.replaceAll(Pattern.quote("msg"), message);
						for (String s : ch.getListeners()) {
							Player p = Main.server.getPlayerExact(s);
							if (p == null) {
								ch.removeListener(s);
								mChat.activeChannel.remove(s);
							} else {
								p.sendMessage(parsedString);
							}
						}
					}
					e.setCancelled(true);
					break;
				}
			}
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		String activeChName = mChat.activeChannel.get(p.getName());
		if (activeChName == null || activeChName.equalsIgnoreCase("")) {
			mChat.activeChannel.put(p.getName(), "global");
			mChat.channels.get("global").addListener(p.getName());
		}

	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {

	}

	private Channel getActiveChannel(Player p) {
		return getActiveChannel(p.getName());
	}

	private Channel getActiveChannel(String s) {
		String chName = mChat.activeChannel.get(s);
		Channel ch = mChat.channels.get(chName);
		return ch;
	}

	private void setActiveChannel(Player p, String ch) {
		setActiveChannel(p, ch);
	}

	private void setActiveChannel(String s, String ch) {
		mChat.activeChannel.put(s, ch);
	}

	private void RegisterDefaultReplacements() {
		mChat.RegisterNewReplacement(new Replacement("playername") { //Register {playername}
			@Override
			public String call(Player chatter, PlayerChatEvent event) {
				return chatter.getName() + ChatColor.RESET;
			}
		});
		mChat.RegisterNewReplacement(new Replacement("playermodname") { //Register {playername}
			@Override
			public String call(Player chatter, PlayerChatEvent event) {
				return chatter.getDisplayName() + ChatColor.RESET;
			}
		});
		mChat.RegisterNewReplacement(new Replacement("world") { //Register {world}
			@Override
			public String call(Player chatter, PlayerChatEvent event) {
				return chatter.getWorld().getName() + ChatColor.RESET;
			}
		});
		mChat.RegisterNewReplacement(new Replacement("color") { //Register {color}
			@Override
			public String call(Player chatter, PlayerChatEvent event) {
				return mChat.channels.get(mChat.activeChannel.get(chatter.getName())).getColor() + "";
			}
		});
		mChat.RegisterNewReplacement(new Replacement("nick") { //Register {nick}
			@Override
			public String call(Player chatter, PlayerChatEvent event) {
				return mChat.channels.get(mChat.activeChannel.get(chatter.getName())).getNickname();
			}
		});

		mChat.RegisterNewReplacement(new Replacement("permprefix") { //Register {permprefix}
			@Override
			public String call(Player chatter, PlayerChatEvent event) {
				String s = chat.getPlayerPrefix(chatter);
				if (s.equalsIgnoreCase("")) s = chat.getGroupPrefix(chatter.getWorld(), chat.getPrimaryGroup(chatter));
				return s;
			}
		});
		mChat.RegisterNewReplacement(new Replacement("permsuffix") { //Register {permsuffix}
			@Override
			public String call(Player chatter, PlayerChatEvent event) {
				String s = chat.getPlayerSuffix(chatter);
				if (s.equalsIgnoreCase("")) chat.getGroupSuffix(chatter.getWorld(), chat.getPrimaryGroup(chatter));
				return s;
			}
		});
	}

	private void SaveChannels() {
		//TODO save channel logic, along with /ch create, /ch modify, /ch delete
	}

	private boolean setupPermissions() {
		RegisteredServiceProvider<Chat> permissionProvider = Main.server.getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
		if (permissionProvider != null) {
			chat = permissionProvider.getProvider();
		}
		return (chat != null);
	}
}
