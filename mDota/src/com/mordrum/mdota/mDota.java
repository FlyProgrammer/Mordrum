package com.mordrum.mdota;

import com.mordrum.mcommon.mCommon;
import com.mordrum.mdota.commands.TeamChatCommand;
import com.mordrum.mdota.listeners.BlockListener;
import com.mordrum.mdota.listeners.DispenserListener;
import com.mordrum.mdota.listeners.NameplateListener;
import com.mordrum.mdota.listeners.PlayerConnectionListener;
import com.mordrum.mdota.util.DotaGame;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.ScoreboardManager;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class mDota extends JavaPlugin {

	public final static String tag = ChatColor.RED + "[DOTA]" + ChatColor.GOLD;
	private static mCommon mCommonInstance;

	//Locations of objects on the DOTA map
	public final Rectangle WorldSpawn = new Rectangle(-1278, 100, 40, 41);
	public final Rectangle RedSpawn = new Rectangle(-1197, 438, 11, 22);
	public final Rectangle BlueSpawn = new Rectangle(-948, 176, 11, 22);

	public Location FarAwayLocation = null;

	public static Logger log;
	public static Server server;
	public static PluginManager pluginManager;
	public static ScoreboardManager scoreboardManager;

	public static Map<String, Integer> playerList;
	public static Map<Integer, DotaGame> activeGames;

	public static Boolean tabListColors; //Should players be colored in the tablist?
	public static boolean chatColors;
	public static Boolean nameplateColors; //Should players have colored nameplates?
	public static Boolean stripMobArmor; //Should mobs be stripped of armor?
	public static Boolean mobHeadgear; //Should mobs have helmets?
	public static Boolean useScoreboard; //Should the scoreboard be used?
	public static Boolean retainItems; //Should players keep their items on death?
	public static Boolean retainArmor; //Should players keep their armor on death?
	public static Boolean retainLevels; //Should players keep their level on death?
	public static Boolean recallEnabled; //Is recall enabled?
	public static Integer recallTime; //Time until recall
	public static Integer maximumPlayersPerGame; //Maximum number of players in one game

	public void onEnable() {
		log = this.getLogger();
		server = this.getServer();
		pluginManager = server.getPluginManager();
		scoreboardManager = server.getScoreboardManager();
		mCommonInstance = (mCommon) pluginManager.getPlugin("mCommon");

		playerList = new HashMap<>();
		activeGames = new HashMap<>();

		LoadConfigValues(true);
		RegisterCommands();
		RegisterListeners();
	}

	public void onDisable() {
		/*for (int a = -55; a <= -79; a++) {
			for (int b = 7; b <= 31; b++) {
				this.world.unloadChunk(a, b, false, false);
			}
		}
		this.getServer().unloadWorld(this.world, false);*/
	}

	private void RegisterListeners() {
		if (getConfig().getBoolean("PreventBlockModification", true))
			pluginManager.registerEvents(new BlockListener(this), this);
		if (getConfig().getBoolean("NameplateColors", true) && pluginManager.isPluginEnabled("TagAPI"))
			pluginManager.registerEvents(new NameplateListener(this), this);

		pluginManager.registerEvents(new PlayerConnectionListener(this), this);
		pluginManager.registerEvents(new DispenserListener(), this);
	}

	private void LoadConfigValues(Boolean firstRun) {
		FileConfiguration config = this.getConfig();
		if (firstRun) {
			//Make sure the config exists, and update if it's old
			this.saveDefaultConfig();
			config.options().copyDefaults(true);
			this.saveConfig();
		}

		tabListColors = config.getBoolean("TabListColors", true);
		tabListColors = config.getBoolean("ChatColors", true);
		nameplateColors = config.getBoolean("NameplateColors", true);
		stripMobArmor = config.getBoolean("StripMobArmor", true);
		mobHeadgear = config.getBoolean("HelmetOnMobs", true);
		useScoreboard = config.getBoolean("UseScoreboard", true);
		retainItems = config.getBoolean("RetainItems", false);
		retainArmor = config.getBoolean("RetainArmor", true);
		retainLevels = config.getBoolean("RetainLevels", false);
		recallEnabled = config.getBoolean("EnableRecall", false);
		recallTime = config.getInt("RecallTime", 7);
		maximumPlayersPerGame = config.getInt("MaxPlayersPerGame", 12);
	}

	private void RegisterCommands() {
		getCommand("t").setExecutor(new TeamChatCommand());
	}

	public static void unloadMap(String mapname) {
		if (server.unloadWorld(server.getWorld(mapname), false)) {
			log.info("Successfully unloaded " + mapname);
		} else {
			log.severe("COULD NOT UNLOAD " + mapname);
			log.severe(server.getWorld(mapname).getPlayers().toString());
		}
	}

	//Loading maps (MUST BE CALLED AFTER UNLOAD MAPS TO FINISH THE ROLLBACK PROCESS)
	public static void loadMap(String mapname) {
		server.createWorld(new WorldCreator(mapname));
	}

	public void SendPlayerToLobby(Player player) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("Connect");
			out.writeUTF("survival"); // Target Server
		} catch (IOException ex) {
			// Can never happen
		}
		player.sendPluginMessage(this, "BungeeCord", b.toByteArray()); //Send the player on their way
	}

	public static mCommon getmCommon() {
		return mCommonInstance;
	}
}
