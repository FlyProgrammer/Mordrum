package com.mordrum.mlib.com.mordrum.mlib.stream;

import com.mordrum.mlib.mLib;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 2/24/13
 * Time: 4:33 PM
 */
public class StreamHandler {

	private ArrayList<Stream> streamList;

	protected void Initialize() {
		streamList = new ArrayList<Stream>();
	}

	public void RegisterStream(Stream streamToRegister) {
		for (Stream s : streamList) { //Check for stream conflict
			if (streamToRegister.name.equalsIgnoreCase(s.name)) {
				s = streamToRegister;
				return;
			}
		}
		streamList.add(streamToRegister); //There was no conflicting stream
	}

	public void TransmitMessage(String streamName, String message) throws StreamNotFoundException {
		Stream streamToUse = null;
		for (Stream s : streamList) { //Find the stream
			if (s.name.equalsIgnoreCase(streamName)) {
				streamToUse = s;
				break;
			}
		}
		if (streamToUse == null) throw new StreamNotFoundException(); //Stream was not found
		else { //Start working with stream
			for (Player player : mLib.server.getOnlinePlayers()) {
				player.sendMessage(streamToUse.color + message);
			}
			;
		}
	}
}
