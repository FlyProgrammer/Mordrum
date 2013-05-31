package com.mordrum.moverseer;

import net.craftminecraft.bungee.bungeeyaml.bukkitapi.InvalidConfigurationException;
import net.craftminecraft.bungee.bungeeyaml.supereasyconfig.Config;

import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 5/30/13
 * Time: 10:48 PM
 */
public class PluginConfig extends Config {

	/*
	The PluginConfig class handles all user configuration for mOverseer.
	 */
	public PluginConfig(Main instance) {
		CONFIG_FILE = new File("plugins" + File.separator + instance.getDescription().getName(), "config.yml");
		CONFIG_HEADER = "mOverseer Config Header";
		try {
			this.init();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	//Below are all of the keys that will be saved to the configuration file
	public Boolean debugEnabled = false;
	public String databaseType = "mysql";

	//Command related keys
	public String commands_teleport_name = "tp";
	public String commands_teleporthere_name = "tphere";
	public String commands_teleportask_name = "tpa";
	public String commands_teleportaskhere_name = "tpahere";
	public String commands_home_name = "home";
	public String commands_sethome_name = "sethome";
	public String commands_kick_name = "kick";
	public String commands_ban_name = "ban";
	public String commands_unban_name = "unban";
	public String commands_tempban_name = "tempban";
	public String commands_spawn_name = "spawn";
	public String commands_setspawn_name = "setspawn";

	public String commands_teleport_perm = "moverseer.moverseer.tp";
	public String commands_teleporthere_perm = "moverseer.tphere";
	public String commands_teleportask_perm = "moverseer.tpa";
	public String commands_teleportaskhere_perm = "moverseer.tpahere";
	public String commands_home_perm = "moverseer.home";
	public String commands_sethome_perm = "moverseer.sethome";
	public String commands_kick_perm = "moverseer.kick";
	public String commands_ban_perm = "moverseer.ban";
	public String commands_unban_perm = "moverseer.unban";
	public String commands_tempban_perm = "moverseer.tempban";
	public String commands_spawn_perm = "moverseer.spawn";
	public String commands_setspawn_perm = "moverseer.setspawn";

	public Boolean commands_teleport_enabled = true;
	public Boolean commands_teleporthere_enabled = true;
	public Boolean commands_teleportask_enabled = true;
	public Boolean commands_teleportaskhere_enabled = true;
	public Boolean commands_home_enabled = true;
	public Boolean commands_sethome_enabled = true;
	public Boolean commands_kick_enabled = true;
	public Boolean commands_ban_enabled = true;
	public Boolean commands_unban_enabled = true;
	;
	public Boolean commands_tempban_enabled = true;
	public Boolean commands_spawn_enabled = true;
	public Boolean commands_setspawn_enabled = true;

	public List<String> commands_teleport_aliases = null;
	public List<String> commands_teleporthere_aliases = null;
	public List<String> commands_teleportask_aliases = null;
	public List<String> commands_teleportaskhere_aliases = null;
	public List<String> commands_home_aliases = null;
	public List<String> commands_sethome_aliases = null;
	public List<String> commands_kick_aliases = null;
	public List<String> commands_ban_aliases = null;
	public List<String> commands_unban_aliases = null;
	;
	public List<String> commands_tempban_aliases = null;
	public List<String> commands_spawn_aliases = null;
	public List<String> commands_setspawn_aliases = null;
}
