package com.mordrum.mcommon;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.mordrum.mcommon.misc.ConnectionMessagesListener;
import com.mordrum.mcommon.misc.CreativeOverkillListener;
import com.mordrum.mcommon.question.Questioner;
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
	private String globalAnnouncementChannel = "GlobalAnnouncement";

	@Override
	public void onEnable() {
		server = getServer();
        PluginManager pm = server.getPluginManager();

        //Initialize micro-features
        pm.registerEvents(new ConnectionMessagesListener(), this); //Remove connection messages
        pm.registerEvents(new CreativeOverkillListener(), this); //Kill mobs instantly when in creative


        //The following listener will enable other micro-features when the plugins that they interact with are enabled
        pm.registerEvents(new WaitForPluginsListener(this), this);

        //Initialize APIs
        questioner = new Questioner(this); //Question API
        server.getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this); //Announce API

        PluginDescriptionFile desc = getDescription();
		Log.info("mCommon " + desc.getVersion() + " initialized");
		Log.info("Current API includes: Stream, Question, Reward");
	}

	@Override
	public void onDisable() {
		Log.info("mCommon shutting down");
	}

	public static Questioner getQuestioner() {
		return questioner;
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

