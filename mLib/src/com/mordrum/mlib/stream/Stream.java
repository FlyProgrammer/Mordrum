package com.mordrum.mlib.com.mordrum.mlib.stream;

import org.bukkit.ChatColor;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 2/24/13
 * Time: 4:33 PM
 */
public class Stream {
	String name;
	ChatColor color;
	Boolean hasTag;
	String tag;
	ChatColor tagColor;
	Boolean canSubscribe;

	protected Stream(String streamName, ChatColor streamColor, Boolean isSubscription) {
		name = streamName;
		color = streamColor;
		hasTag = false;
		canSubscribe = isSubscription;
	}

	protected Stream(String streamName, ChatColor streamColor, Boolean isSubscription, String streamTag, ChatColor streamTagColor) {
		name = streamName;
		color = streamColor;
		hasTag = true;
		tag = streamTag;
		tagColor = streamTagColor;
		canSubscribe = isSubscription;
	}
}
