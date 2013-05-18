package com.mordrum.mvote;

import com.mordrum.mchat.Replacement;
import com.mordrum.mchat.mChat;
import lib.PatPeter.SQLibrary.SQLite;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 3/9/13
 * Time: 1:33 PM
 */
public class Main extends JavaPlugin {

	protected static Server server;
	protected static SQLite sql;
	public static boolean debug = false;

	@Override
	public void onEnable() {
		server = this.getServer();
		Logger log = Logger.getLogger("Minecraft");

		server.getPluginManager().registerEvents(new VoteListener(this), this);
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
		doStuffWithMordrumChat();
	}

	@Override
	public void onDisable() {
		sql.close();
	}

	private void doStuffWithMordrumChat() {
		mChat.RegisterNewReplacement(new Replacement("vote") {
			@Override
			public String call(Player chatter, PlayerChatEvent event) {
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
}
