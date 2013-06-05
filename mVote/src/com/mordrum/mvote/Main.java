package com.mordrum.mvote;

import com.mordrum.mchat.Replacement;
import com.mordrum.mchat.mChat;
import lib.PatPeter.SQLibrary.SQLite;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import se.ranzdo.bukkit.methodcommand.CommandHandler;

import java.sql.*;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 3/9/13
 * Time: 1:33 PM
 */
public class Main extends JavaPlugin {

	protected static Server server;
	//protected static Connection connection;
    protected static SQLite sql;
	public static boolean debug = true;
	protected static CommandHandler cmdHandler;
	static Logger log;

	@Override
	public void onEnable() {
		server = this.getServer();
		log = Logger.getLogger("Minecraft");
		cmdHandler = new CommandHandler(this);
		cmdHandler.registerCommands(new VoteCommands());
		server.getPluginManager().registerEvents(new VoteListener(this), this);
		InitializeSQL();
		doStuffWithMordrumChat();
	}

	@Override
	public void onDisable() {
		sql.close();
	}

	private void doStuffWithMordrumChat() {
		mChat.RegisterNewReplacement(new Replacement("vote") {
			@Override
			public String call(Player chatter, AsyncPlayerChatEvent event) {
				int voteCount;
				try {
					PreparedStatement ps = Main.sql.prepare("SELECT TotalVotes " +
							"FROM Votes " +
							"WHERE Username='" + chatter.getName() + "';");
					ResultSet rs = Main.sql.query(ps);
					voteCount = rs.getInt("TotalVotes");
				} catch (SQLException e1) {
					voteCount = 0;
				}
				return ChatColor.DARK_GRAY + "[" + voteCount + "]" + ChatColor.RESET;
			}
		});
	}

	private void InitializeSQL() {
        /*try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.getDataFolder().getAbsolutePath() + "storage.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        sql = new SQLite(log,
				"[mVote] ",
				this.getDataFolder().getAbsolutePath(),
				"mVote");
		if (sql.open()) {
			log.info("(TV)SQL Open");
			PreparedStatement ps;
			try {
				ps = sql.prepare("CREATE TABLE IF NOT EXISTS Votes(id INTEGER PRIMARY KEY, username varchar(50), totalvotes INTEGER);");
				sql.query(ps);
				log.info("(TV)Created table Votes if one did not already exist");
			} catch (SQLException e) {
				e.printStackTrace();
				log.info("(TV)Error while creating Votes table");
			}
		}
	}
}
