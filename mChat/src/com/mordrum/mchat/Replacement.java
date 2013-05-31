package com.mordrum.mchat;

import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 3/11/13
 * Time: 1:31 PM
 */
public abstract class Replacement {

	private Pattern pattern;

	public Replacement(String regex) {
		this.pattern = Pattern.compile(regex);
	}

	public abstract String call(Player chatter, AsyncPlayerChatEvent event);

	public Pattern getPattern() {
		return pattern;
	}
}
