package com.mordrum.mcommon;

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

	mCommon plugin;

	public MainConfig(mCommon instance) {
		CONFIG_FILE = new File("plugins" + File.separator + instance.getDescription().getName(), "config.yml");
		CONFIG_HEADER = "mGame Main Config";
		try {
			this.init();
		} catch (InvalidConfigurationException e) {
			plugin.getLogger().log(Level.SEVERE, "Could not load config!", e);
		}
		this.plugin = instance;
	}

	@Comment("Creative Instakill")
	public Boolean creativeInstantKill = true;
	@Comment("Remove connection messages")
	public Boolean removeConnectionMessages = true;
	@Comment("Disable /stop")
	public Boolean stopDisabled = true;
	@Comment("Disable /reload")
	public Boolean reloadDisabled = true;

	@Comment("Below are hookins for other plugins")
	public Boolean doesnothing;
	@Comment("mChat")
	public Boolean killChatMessages = false;
}
