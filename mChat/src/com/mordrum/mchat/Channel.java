package com.mordrum.mchat;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 3/10/13
 * Time: 5:41 PM
 */
public class Channel {
	private String name;
	private ChatColor color;
	private String format;
	private Boolean hidden;
	private Double speakCost;
	private Set<String> listeners;
	private String nickname;

	public Channel(String channelName, ChatColor channelColor, String channelFormat, Boolean isChannelHidden, String channelNickname) {
		this.name = channelName;
		this.color = channelColor;
		this.format = channelFormat;
		this.hidden = isChannelHidden;
		this.nickname = channelNickname;
		listeners = new HashSet<>();
	}

	public Channel(String channelName, ChatColor channelColor, String channelFormat, Boolean isChannelHidden, String channelNickname, Double channelSpeakCost) {
		this.name = channelName;
		this.color = channelColor;
		this.format = channelFormat;
		this.hidden = isChannelHidden;
		this.speakCost = channelSpeakCost;
		this.nickname = channelNickname;
		listeners = new HashSet<>();
	}

	public Boolean getHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public ChatColor getColor() {
		return color;
	}

	public void setColor(ChatColor color) {
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getSpeakCost() {
		return speakCost;
	}

	public void setSpeakCost(Double speakCost) {
		this.speakCost = speakCost;
	}

	public ArrayList<String> getListeners() {
		List<String> safelist = new ArrayList<String>();
		safelist.addAll(listeners);
		return (ArrayList<String>) safelist;
	}

	public void removeListener(String listenerToRemove) {
		listeners.remove(listenerToRemove);
	}

	public void addListener(String listenerToAdd) {
		listeners.add(listenerToAdd);
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
