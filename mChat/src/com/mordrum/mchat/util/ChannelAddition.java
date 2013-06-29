package com.mordrum.mchat.util;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 6/10/13
 * Time: 9:38 PM
 */
public abstract class ChannelAddition {

	private String channelName;
	private String addition;
	private AdditionPosition position;

	public ChannelAddition(String ChannelName, String Addition, AdditionPosition Position) {
		this.channelName = ChannelName;
		this.addition = Addition;
		this.position = Position;
	}

	public String getChannelName() {
		return channelName;
	}

	public String getAddition() {
		return addition;
	}

	public AdditionPosition getPosition() {
		return position;
	}
}
