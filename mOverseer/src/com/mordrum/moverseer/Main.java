package com.mordrum.moverseer;

import com.mordrum.moverseer.commands.BanCommand;
import com.mordrum.moverseer.commands.HomeCommand;
import com.mordrum.moverseer.commands.TeleportCommand;
import com.mordrum.moverseer.listeners.BanListener;
import net.craftminecraft.bungee.bungeeyaml.pluginapi.ConfigurablePlugin;
import net.md_5.bungee.api.plugin.PluginManager;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 5/30/13
 * Time: 10:25 PM
 */
public class Main extends ConfigurablePlugin {

	PluginConfig config;
	IOHandler IO;

	/*
	Called when mOverseer is enabled. Takes care of registering listeners and commands.
	 */
	public void onEnable() {
		config = new PluginConfig(this); //Create the config
		RegisterCommands();
		RegisterListeners();

		IO = new IOHandler(this);
	}

	/*
	Called when mOverseer is disabled.
	 */
	public void onDisable() {
		config = null;
		IO.SaveRecords();
	}

	/*
	Method to fetch mOverseer's config
	 */
	public PluginConfig getConfig() {
		return config;
	}

	public IOHandler getIO() {
		return IO;
	}

	private void RegisterListeners() {
		PluginManager pm = this.getProxy().getPluginManager();
		pm.registerListener(this, new BanListener(this));
	}

	private void RegisterCommands() {
		PluginManager pm = this.getProxy().getPluginManager();
		//Below all commands are registered, really should find a better way to do it
		//TODO see if passing config to classes is faster
		if (config.commands_ban_enabled)
			pm.registerCommand(this, new BanCommand(this));
		if (config.commands_home_enabled)
			pm.registerCommand(this, new HomeCommand(this));
		if (config.commands_kick_enabled)
			pm.registerCommand(this, new KickCommand(this));
		if (config.commands_sethome_enabled)
			pm.registerCommand(this, new SetHomeCommand(this));
		if (config.commands_setspawn_enabled)
			pm.registerCommand(this, new SetSpawnCommand(this));
		if (config.commands_spawn_enabled)
			pm.registerCommand(this, new SpawnCommand(this));
		if (config.commands_teleportask_enabled)
			pm.registerCommand(this, new TeleportAskCommand(this));
		if (config.commands_teleportaskhere_enabled)
			pm.registerCommand(this, new TeleportAskHereCommand(this));
		if (config.commands_teleport_enabled)
			pm.registerCommand(this, new TeleportCommand(this));
		if (config.commands_teleporthere_enabled)
			pm.registerCommand(this, new TeleportHereCommand(this));
		if (config.commands_tempban_enabled)
			pm.registerCommand(this, new TempbanCommand(this));
		if (config.commands_unban_enabled)
			pm.registerCommand(this, new UnbanCommand(this));
	}

	private void UnregisterCommands() {
		PluginManager pm = this.getProxy().getPluginManager();
		//Below all commands are unregistered, useful for some sort of plugin disable situation
		pm.unregisterCommand(new BanCommand(this));
		pm.unregisterCommand(new HomeCommand(this));
		pm.unregisterCommand(new KickCommand(this));
		pm.unregisterCommand(new SetHomeCommand(this));
		pm.unregisterCommand(new SetSpawnCommand(this));
		pm.unregisterCommand(new SpawnCommand(this));
		pm.unregisterCommand(new TeleportAskCommand(this));
		pm.unregisterCommand(new TeleportAskHereCommand(this));
		pm.unregisterCommand(new TeleportCommand(this));
		pm.unregisterCommand(new TeleportHereCommand(this));
		pm.unregisterCommand(new TempbanCommand(this));
		pm.unregisterCommand(new UnbanCommand(this));
	}
}
