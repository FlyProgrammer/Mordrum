package com.mordrum.mdota.util;

import org.bukkit.ChatColor;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 6/25/13
 * Time: 9:52 PM
 */
public enum Team {

	RED, BLUE, GREEN, YELLOW, TEAL, GOLD, PURPLE, BLACK;

	private int count;

	public int getCount() {
		return count;
	}

	private int setCount(int newCount) {
		count = newCount;
		return count;
	}

	public int incrementCount() {
		return incrementCount(1);
	}

	public int incrementCount(int IncrementBy) {
		count += IncrementBy;
		return count;
	}

	public int decrementCount() {
		return decrementCount(1);
	}

	public int decrementCount(int DecrementBy) {
		count -= DecrementBy;
		return count;
	}

	public ChatColor getChatColor() {
		ChatColor cc;
		switch (this) {
			case RED:
				cc = ChatColor.RED;
			case BLUE:
				cc = ChatColor.BLUE;
			case GREEN:
				cc = ChatColor.GREEN;
			case YELLOW:
				cc = ChatColor.YELLOW;
			case TEAL:
				cc = ChatColor.AQUA;
			case GOLD:
				cc = ChatColor.GOLD;
			case PURPLE:
				cc = ChatColor.DARK_PURPLE;
			case BLACK:
				cc = ChatColor.BLACK;
			default:
				cc = ChatColor.WHITE;
		}
		return cc;
	}
}
