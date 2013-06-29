package com.mordrum.mcommon;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.mordrum.mcommon.api.question.Questioner;
import com.mordrum.mcommon.api.util.ServerMonitor;
import com.mordrum.mcommon.microfeatures.CommandListener;
import com.mordrum.mcommon.microfeatures.ConnectionMessagesListener;
import com.mordrum.mcommon.microfeatures.CreativeOverkillListener;
import com.mordrum.mcommon.microfeatures.LongGrassRemoveListener;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 2/24/13
 * Time: 2:40 PM
 */
public class mCommon extends JavaPlugin implements PluginMessageListener {
	protected static String name = "mCommon";

	public static Server server;
	protected static Questioner questioner;
	protected static Logger log;
	private static String globalAnnouncementChannel = "GlobalAnnouncement";

	@Override
	public void onEnable() {
		server = getServer();
		PluginManager pm = server.getPluginManager();
		this.saveDefaultConfig();

		//Initialize micro-features
		if (getConfig().getBoolean("CreativeInstantKill"))
			pm.registerEvents(new ConnectionMessagesListener(), this); //Remove connection messages
		if (getConfig().getBoolean("RemoveConnectionMessages"))
			pm.registerEvents(new CreativeOverkillListener(), this); //Kill mobs instantly when in creative
		if (getConfig().getBoolean("RemoveLongGrass"))
			pm.registerEvents(new LongGrassRemoveListener(this), this); //Removes long grass and handles seeds

		//Initialize anti-clusterfuck command blocker
		pm.registerEvents(new CommandListener(this), this); //Prevents /stop and /reload

		//The following listener will enable other micro-features when the plugins that they interact with are enabled
		pm.registerEvents(new WaitForPluginsListener(this), this);

		//Initialize APIs
		questioner = new Questioner(this); //Question API
		server.getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this); //Announce API

		PluginDescriptionFile desc = getDescription();
		RegisterCommands();
		ServerMonitor.Initialize(this);
		Log.info("mCommon " + desc.getVersion() + " initialized");
		Log.info("Current API includes: Stream, Question, Reward");
	}

	@Override
	public void onDisable() {
		Log.info("mCommon shutting down");
	}

	private void RegisterCommands() {
		getCommand("ekg").setExecutor(new Commands());
	}

	public static Questioner getQuestioner() {
		return questioner;
	}

	public void BroadcastGlobalNetworkMessage(String message) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();

		out.writeUTF("Forward");
		out.writeUTF("ALL"); // Target Server
		out.writeUTF(globalAnnouncementChannel);
		out.writeUTF(message); // Write out the rest of the data.

		server.getOnlinePlayers()[0].sendPluginMessage(this, "BungeeCord", out.toByteArray());
	}

	@Override
	public void onPluginMessageReceived(String s, Player player, byte[] bytes) {
		ByteArrayDataInput in = ByteStreams.newDataInput(bytes);

		String channelIn = in.readUTF();
		if (!channelIn.equalsIgnoreCase(globalAnnouncementChannel)) {
			return; // avoid indentation, and return early.
		}
		String messageToBroadcast = in.readUTF();
		server.broadcastMessage(messageToBroadcast);
	}
}

