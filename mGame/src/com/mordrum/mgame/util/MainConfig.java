package com.mordrum.mgame.util;

import com.mordrum.mgame.Main;
import net.craftminecraft.bungee.bungeeyaml.bukkitapi.InvalidConfigurationException;
import net.craftminecraft.bungee.bungeeyaml.supereasyconfig.Comment;
import net.craftminecraft.bungee.bungeeyaml.supereasyconfig.Config;

import java.io.File;
import java.util.logging.Level;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 6/8/13
 * Time: 1:19 PM
 */
public class MainConfig extends Config {

	Main plugin;

	public MainConfig(Main instance) {
		CONFIG_FILE = new File("plugins" + File.separator + instance.getDescription().getName(), "config.yml");
		CONFIG_HEADER = "mGame Main Config";
		try {
			this.init();
		} catch (InvalidConfigurationException e) {
			plugin.getLogger().log(Level.SEVERE, "Could not load config!", e);
		}
		this.plugin = instance;
	}

	@Comment("jews")
	String banana;
}
