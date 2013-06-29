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
}
