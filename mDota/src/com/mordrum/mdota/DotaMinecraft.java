package com.mordrum.mdota;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class DotaMinecraft extends JavaPlugin {
	public World world;
	public final Map<String, Boolean> playerhasjoined = new HashMap<String, Boolean>();
	public final Map<String, Integer> playerlist = new HashMap<String, Integer>();
	public final Map<String, Integer> playerkills = new HashMap<String, Integer>();
	public final Map<String, Integer> playercs = new HashMap<String, Integer>();
	public final Map<String, Integer> playerdeaths = new HashMap<String, Integer>();
	public final Map<String, Integer> playerRecallID = new HashMap<String, Integer>();
	public final Map<String, ItemStack[]> playerdeathitems = new HashMap<String, ItemStack[]>();
	public final Map<String, ItemStack[]> playerdeatharmor = new HashMap<String, ItemStack[]>();
	public final Map<Location, String> turretlocations = new HashMap<Location, String>();
	public final Map<String, Boolean> turretstates = new HashMap<String, Boolean>();
	public int RedCount = 0;
	public int BlueCount = 0;
	public final Rectangle WorldSpawn = new Rectangle(-1278, 100, 40, 41);
	public final Rectangle RedSpawn = new Rectangle(-1197, 438, 11, 22);
	public final Rectangle BlueSpawn = new Rectangle(-948, 176, 11, 22);
	public Location RedPoint = null;
	public Location BluePoint = null;
	public Location RedBed = null;
	public Location BlueBed = null;
	public boolean GameInProgress = true;
	public Location FarAwayLocation = null;
	public boolean PlayersKeepItems = true;
	public boolean Enabled = false;
	public boolean PlayersKeepLevel = true;
	public boolean colorNameTag = true;
	public String WorldName = "Minecraft_dota";
	public PluginManager pm = null;
	public boolean RecallEnabled = false;
	public int RecallDelay = 6;

	public boolean isResetting = false;

	public boolean removeMobArmor = false;
	public boolean giveMobsHelmet = false;

	public static Logger log;

	//Red is 1
	//Blue is 2

	public void onEnable() {
		pm = getServer().getPluginManager();
		log = this.getLogger();
		this.saveDefaultConfig();
		FileConfiguration config = this.getConfig();
		config.options().copyDefaults(true);
		this.saveConfig();
		this.Enabled = true;//config.getBoolean("Enabled");
		this.WorldName = config.getString("WorldName");
		this.PlayersKeepItems = config.getBoolean("PlayersKeepItems");
		this.PlayersKeepLevel = config.getBoolean("PlayersKeepLevel");
		this.removeMobArmor = config.getBoolean("removeMobArmor");
		this.giveMobsHelmet = config.getBoolean("giveMobsHelmet");
		this.RecallEnabled = config.getBoolean("RecallEnabled");
		this.RecallDelay = config.getInt("RecallDelay");
		this.colorNameTag = config.getBoolean("colorNameTag");


		loadMap(WorldName);

		PluginListener.LoadPlugin(this);
	}

	public void onDisable() {
		for (int a = -55; a <= -79; a++) {
			for (int b = 7; b <= 31; b++) {
				this.world.unloadChunk(a, b, false, false);
			}
		}
		this.getServer().unloadWorld(this.world, false);
	}

	public void broadcastMessage(String message) {
		World world = this.getServer().getWorld(WorldName);
		for (Player p : world.getPlayers()) {
			p.sendMessage(message);
		}
	}

	public void resetMap() {
		if (!isResetting) {
			isResetting = true;
			for (Player player : getServer().getOnlinePlayers()) {
				log.info(player.getWorld().getName());
				player.teleport(getServer().getWorld("safe_map").getSpawnLocation());
				log.info(player.getWorld().getName());
				player.setDisplayName(player.getName());
				playerlist.remove(player.getName());
				playerkills.remove(player.getName());
				playerdeaths.remove(player.getName());
				//player.teleport(getServer().getWorld(WorldName).getSpawnLocation());
				//player.setBedSpawnLocation(getServer().getWorld(WorldName).getSpawnLocation());
				player.getInventory().clear();
				player.getInventory().setArmorContents(null);
				player.setHealth(20);
				player.setFoodLevel(20);
				if (playerlist.containsKey(player.getName())) {
					if (playerlist.get(player.getName()) == 1) {
						RedCount--;
					} else if (playerlist.get(player.getName()) == 2) {
						BlueCount--;
					}
				}
				playerlist.remove(player.getName());
			}

			playerlist.clear();
			playerdeaths.clear();
			playerkills.clear();

			getServer().broadcastMessage(ChatColor.RED + "[DOTA]" + ChatColor.GOLD + "Map is resetting!");
			unloadMap(WorldName);
			loadMap(WorldName);
			RedCount = 0;
			BlueCount = 0;
			isResetting = false;
			getServer().broadcastMessage(ChatColor.RED + "[DOTA]" + ChatColor.GOLD + "Map reset complete!");
		}
	}

	public void unloadMap(String mapname) {
		if (getServer().unloadWorld(getServer().getWorld(mapname), false)) {
			log.info("Successfully unloaded " + mapname);
		} else {
			log.severe("COULD NOT UNLOAD " + mapname);
			log.severe(getServer().getWorld(mapname).getPlayers().toString());
		}
	}

	//Loading maps (MUST BE CALLED AFTER UNLOAD MAPS TO FINISH THE ROLLBACK PROCESS)
	public void loadMap(String mapname) {
		getServer().createWorld(new WorldCreator(mapname));
	}
}
