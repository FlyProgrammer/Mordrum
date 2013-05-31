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
	public static String adflyURL;
	BungeeCommunicator bungeeComunicator;

	@Override
	public void onEnable() {
		server = this.getServer();
		log = Logger.getLogger("Minecraft");
		mChat.Initialize();
		cmdHandler = new CommandHandler(this);
		cmdHandler.registerCommands(new CommandSet());
		adflyURL = "http://api.adf.ly/api.php?key=8f2016913e96f23d15530fbae94ce14f&uid=805961&advert_type=int&domain=adf.ly";
		bungeeComunicator = new BungeeCommunicator(this);
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
