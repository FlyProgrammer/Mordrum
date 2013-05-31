package com.mordrum.mchat;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 5/25/13
 * Time: 10:25 PM
 */
public class BungeeCommunicator implements PluginMessageListener {

	static Main plugin;
	static String channel = "mChatGlobal";

	public BungeeCommunicator(Main instance) {
		this.plugin = instance;
		Bukkit.getMessenger().registerIncomingPluginChannel(plugin, "BungeeCord", this);
		Bukkit.getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
	}

	public static void TransmitChatMessage(String message) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();

		out.writeUTF("Forward");
		out.writeUTF("ALL"); // Target Server
		out.writeUTF(channel);
		out.writeUTF(message); // Write out the rest of the data.

		Main.server.getOnlinePlayers()[0].sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
	}

	@Override
	public void onPluginMessageReceived(String s, Player player, byte[] bytes) {
		ByteArrayDataInput in = ByteStreams.newDataInput(bytes);

		String channelIn = in.readUTF();
		if (!channelIn.equalsIgnoreCase(channel)) {
			return; // avoid indentation, and return early.
		}
		String messageToPlayer = in.readUTF();
		//messageToPlayer = ChatColor.translateAlternateColorCodes('&', messageToPlayer); //Translate color codes
		for (String pName : mChat.channels.get(mChat.globalChannel).getListeners()) {
			Player p = Main.server.getPlayer(pName);
			p.sendMessage(messageToPlayer);
		}
	}

	/*
	public static void TransmitChatMessage(String message) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		byte[] data = message.getBytes();
		try {
			out.writeUTF("Forward");
			out.writeUTF("ALL"); // Target Server

			/* A "subchannel" much like Forward, Connect, etc. Think of it as a way of identifying what plugin sent the message to Bungee, I guess? It's mainly for plugin communication between servers I'd say
			out.writeUTF(channel);
			out.writeShort(data.length); // The length of the rest of the data.
			out.write(data); // Write out the rest of the data.
		} catch (IOException e) {
			// Can never happen
		}

		Main.server.getOnlinePlayers()[0].sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
	}*/
	/*
	@Override
	public void onPluginMessageReceived(String s, Player player, byte[] bytes) {
		DataInputStream in = new DataInputStream(new ByteArrayInputStream(bytes));
		try {
			String channelIn = in.readUTF();
			if (channelIn.equalsIgnoreCase(channel)) {
				short dataLength = in.readShort();
				byte[] data = new byte[dataLength]; //Make a new byte array
				in.readFully(data); //Fill it with our message in byte form
				String packedDecimalArray = Arrays.toString(data).replaceAll(" ", ""); //Convert the byte array into a packed array of ASCII decimals (With no spaces)
				String[] decimalArray = packedDecimalArray.substring(1, packedDecimalArray.length() - 1).split(","); //Convert to an actual array
				StringBuilder sb = new StringBuilder();
				for (String decimalInArray : decimalArray) {
					int decimal = Integer.parseInt(decimalInArray);
					char charFromDecimal;
					if (decimal == -89) charFromDecimal = '&';
					else charFromDecimal = (char) decimal; //Convert decimal to character
					sb.append(charFromDecimal); //Add the char to the message being formed
				}
				String messageToPlayer = sb.toString(); //Convert the stringbuilder to a human readable message
				messageToPlayer = ChatColor.translateAlternateColorCodes('&', messageToPlayer); //Translate color codes
				for (String pName : mChat.channels.get(mChat.globalChannel).getListeners()) {
					Player p = Main.server.getPlayer(pName);
					p.sendMessage(messageToPlayer);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}*/
}
