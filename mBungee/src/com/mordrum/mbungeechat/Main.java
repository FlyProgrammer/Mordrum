package com.mordrum.mbungeechat;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 5/19/13
 * Time: 8:49 PM
 */
public class Main extends Plugin implements Listener {

	private Long startTime;

	ProxyServer proxy;
	public void onEnable(){
		Log("Plugin loaded, initializing!");
		startTime = System.currentTimeMillis();
		proxy = this.getProxy();
	}

	public void onDisable(){
		Log("Plugin is now disabled");
	}

	public void Log(String msg) {
		System.out.println("[mChat]" + msg);
	}
}
