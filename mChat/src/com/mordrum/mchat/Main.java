package com.mordrum.mchat;

//import lib.PatPeter.SQLibrary.SQLite;

import net.craftminecraft.bungee.bungeeyaml.pluginapi.ConfigurablePlugin;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.PluginManager;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 3/9/13
 * Time: 1:33 PM
 */
public class Main extends ConfigurablePlugin {

	public String APIfull, APIkey, APIuid, APIadvertType, APIdomain;

	@Override
	public void onEnable() {
		//TODO commands + listeners + logger + API

		APIkey = "http://api.adf.ly/api.php?key=" +
				"8f2016913e96f23d15530fbae94ce14f&" +
				"uid=805961&" +
				"advert_type=banner&" +
				"domain=adf.ly";
	}

	private void DoConfigStuff() {
		this.saveDefaultConfig(); //Save the config if one does not exist

		APIkey = getConfig().getString("API.key");
		APIuid = getConfig().getString("API.uid");
		APIadvertType = getConfig().getString("API.adverttype");
		APIdomain = getConfig().getString("API.domain");
		APIfull = "http://api.adf.ly/api.php?key=" + APIkey + "&uid=" + APIuid +
				"&advert_type=" + APIadvertType + "&domain=" + APIdomain;


	}

	/*
	Registers plugin channels
	 */
	private void registerChannels() {
		getProxyServer().registerChannel("BungeeChatPlus");
	}

	/*
	Returns an instance of the ProxyServer
	 */
	public ProxyServer getProxyServer() {
		return ProxyServer.getInstance();
	}

	/*
	Returns the PluginManager
	 */
	public PluginManager getPluginManager() {
		return ProxyServer.getInstance().getPluginManager();
	}
}
