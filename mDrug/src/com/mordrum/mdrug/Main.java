package com.mordrum.mdrug;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 5/11/13
 * Time: 4:31 PM
 */
public class Main extends JavaPlugin {

	static Logger log;

	public void onEnable() {
		log = this.getLogger();
	}
}
