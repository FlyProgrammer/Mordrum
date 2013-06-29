package com.mordrum.mchat;

import com.mordrum.mchat.util.Replacement;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 3/10/13
 * Time: 5:32 PM
 */
public class mChat {

	protected static List<Replacement> replacements = new ArrayList<>();
	protected static HashMap<String, Channel> channels;
	protected static HashMap<String, String> activeChannel;
	static String globalChannel = "global";

	protected static void Initialize() {
		replacements = new ArrayList<>();
		channels = new HashMap<>();
		activeChannel = new HashMap<>();
	}

	public static void RegisterNewReplacement(Replacement replacement) {
		replacements.add(replacement);
	}

	public static String ParseString(String stringToParse, Player chatter) {
		return ParseString(stringToParse, chatter, null);
	}

	public static String ParseString(String stringToParse, Player chatter, AsyncPlayerChatEvent event) {
		for (Replacement r : replacements) {    //Loop through replacements
			Matcher matcher = r.getPattern().matcher(stringToParse);
			StringBuffer sb = new StringBuffer();
			while (matcher.find()) {
				matcher.appendReplacement(sb, r.call(chatter, event));
			}
			matcher.appendTail(sb);
			stringToParse = sb.toString();
		}
		stringToParse = ChatColor.translateAlternateColorCodes('&', stringToParse);
		stringToParse = stringToParse.replaceAll(Pattern.quote("msg"), Matcher.quoteReplacement(event.getMessage()));
		if (chatter.hasPermission("mchat." + activeChannel.get(chatter.getName()) + ".colorcodes")) {
			stringToParse = ChatColor.translateAlternateColorCodes('&', stringToParse);
		}
		stringToParse = stringToParse.replaceAll("[{}]", "");
		return stringToParse;
	}

	public static List<Replacement> getReplacements() {
		return replacements;
	}

	public static void RegisterNewChannel(Channel ch) {
		channels.put(ch.getName(), ch);
	}

	public static void AddListenerToChannel(String listener, String channel) {
		Channel ch = channels.get(channel);
		if (!ch.getListeners().contains(listener)) ch.addListener(listener);
	}

	public static void AddListenerToChannel(Player listener, String channel) {
		AddListenerToChannel(listener.getName(), channel);
	}

	public static void AddListenerToChannel(String listener, Channel channel) {
		AddListenerToChannel(listener, channel.getName());
	}

	public static void AddListenerToChannel(Player listener, Channel channel) {
		AddListenerToChannel(listener.getName(), channel.getName());
	}
}