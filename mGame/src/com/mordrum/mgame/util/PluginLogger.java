package com.mordrum.mgame.util;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 6/8/13
 * Time: 1:23 PM
 */
public class PluginLogger extends Logger {
	private String pluginName;

	/**
	 * Creates a new PluginLogger that extracts the name from a plugin.
	 *
	 * @param context A reference to the plugin
	 */
	public PluginLogger(Plugin context) {
		super(context.getClass().getCanonicalName(), null);
		String prefix = context.getDescription().getName();
		pluginName = new StringBuilder().append("[").append(prefix).append("] ").toString();
		setParent(ProxyServer.getInstance().getLogger());
		setLevel(Level.ALL);
	}

	@Override
	public void log(LogRecord logRecord) {
		logRecord.setMessage(pluginName + logRecord.getMessage());
		super.log(logRecord);
	}
}
