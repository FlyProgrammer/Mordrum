package com.mordrum.mlib;

import com.mordrum.mlib.question.Questioner;
import org.bukkit.Server;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 2/24/13
 * Time: 2:40 PM
 */
public class mLib extends JavaPlugin {
	protected static String name = "mLib";

	public static Server server;
	protected static Questioner questioner;
	protected static Logger log;

	@Override
	public void onEnable() {
		server = getServer();


		PluginDescriptionFile desc = getDescription();
		questioner = new Questioner(this);
		Log.info("mLib " + desc.getVersion() + " initialized");
		Log.info("Current API includes: Stream, Question, Reward");
	}

	@Override
	public void onDisable() {
		Log.info("mLib shutting down");
	}

	public static Questioner getQuestioner() {
		return questioner;
	}
}

