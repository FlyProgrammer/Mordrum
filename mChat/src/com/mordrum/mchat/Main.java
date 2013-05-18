package com.mordrum.mchat;

//import lib.PatPeter.SQLibrary.SQLite;

import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;
import se.ranzdo.bukkit.methodcommand.CommandHandler;

import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 3/9/13
 * Time: 1:33 PM
 */
public class Main extends JavaPlugin {

	protected static Server server;
	//protected static SQLite sql;
	protected static Logger log;
	protected static CommandHandler cmdHandler;

	@Override
	public void onEnable() {
		server = this.getServer();
		log = Logger.getLogger("Minecraft");
		mChat.Initialize();
		cmdHandler = new CommandHandler(this);
		cmdHandler.registerCommands(new CommandSet());

		server.getPluginManager().registerEvents(new ChannelHandler(this), this);
		/*sql = new SQLite(log,
				"[mChat] ",
				this.getDataFolder().getAbsolutePath(),
				"mChat");
		if (sql.open()) {
			log.info("SQL Open");
			PreparedStatement ps = null;
			try {
				ps = sql.prepare("CREATE TABLE IF NOT EXISTS Votes(Username varchar(255), TotalVotes int);");
				sql.query(ps);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}*/
	}

	@Override
	public void onDisable() {
		///	sql.close();
	}
}
