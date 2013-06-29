package com.mordrum.mgame;

import com.mordrum.mgame.util.MainConfig;
import com.mordrum.mgame.util.PluginLogger;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.File;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 6/8/13
 * Time: 1:02 PM
 */
public class Main extends Plugin {

	private MainConfig config;
	private File configdir;
	protected static Logger log = null;

	public void onEnable() {
		configdir = new File("plugins" + File.separator + this.getDescription().getName()); //Our config directory
		log = new PluginLogger(this); //Set the logger

		if (!configdir.exists()) configdir.mkdirs(); //Make the config dir if it doesn't exist

		config = new MainConfig(this); //New instance of the config
	}

	public void onDisable() {

	}
}
