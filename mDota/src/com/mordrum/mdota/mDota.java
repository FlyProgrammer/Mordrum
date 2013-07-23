package com.mordrum.mdota;

import com.mordrum.mcommon.api.util.Copy;
import com.mordrum.mcommon.mCommon;
import com.mordrum.mdota.commands.TeamChatCommand;
import com.mordrum.mdota.listeners.*;
import com.mordrum.mdota.util.DotaGame;
import com.mordrum.mdota.util.Team;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.ScoreboardManager;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

public class mDota extends JavaPlugin {

    private static int lastGameID = 0;

    public final static String tag = ChatColor.RED + "[DOTA]" + ChatColor.GOLD;
    private static mCommon mCommonInstance;
    private final static int maximumCreationDepth = 3;

    //Locations of objects on the DOTA map
    public final Rectangle worldSpawn = new Rectangle(-1278, 100, 40, 41);
    public static final Rectangle redSpawn = new Rectangle(-1197, 438, 11, 22);
    public static final Rectangle blueSpawn = new Rectangle(-948, 176, 11, 22);

    public Location FarAwayLocation = null;

    public static Logger log;
    public static Server server;
    public static PluginManager pluginManager;
    public static ScoreboardManager scoreboardManager;

    public static Map<String, Integer> playerList;
    public static Map<Integer, DotaGame> activeGames;
    public static Map<World, Integer> loadedWorlds;

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
    public static String cleanMapName; //Name of the blank DOTA map we will be cloning

    public void onEnable() {
        log = this.getLogger();
        server = this.getServer();
        pluginManager = server.getPluginManager();
        scoreboardManager = server.getScoreboardManager();
        mCommonInstance = (mCommon) pluginManager.getPlugin("mCommon");

        playerList = new HashMap<>();
        activeGames = new HashMap<>();
        loadedWorlds = new HashMap<>();

        LoadConfigValues(true);
        RegisterCommands();
        RegisterListeners();
    }

    public void onDisable() {
    }

    private void RegisterListeners() {
        if (getConfig().getBoolean("PreventBlockModification", true))
            pluginManager.registerEvents(new BlockListener(this), this);
        if (getConfig().getBoolean("NameplateColors", true) && pluginManager.isPluginEnabled("TagAPI"))
            pluginManager.registerEvents(new NameplateListener(this), this);

        pluginManager.registerEvents(new PlayerConnectionListener(this), this);
        pluginManager.registerEvents(new DispenserListener(), this);
        pluginManager.registerEvents(new EntityListener(),this);
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
        cleanMapName = config.getString("CleanMapName", "dotaMap");
    }

    private void RegisterCommands() {
        getCommand("t").setExecutor(new TeamChatCommand());
    }

    public static void unloadMap(String mapName) {
        if (server.unloadWorld(server.getWorld(mapName), false)) {
            log.info("Successfully unloaded " + mapName);
        } else {
            log.severe("COULD NOT UNLOAD " + mapName);
            log.severe(server.getWorld(mapName).getPlayers().toString());
        }
    }

    //Loading maps (MUST BE CALLED AFTER UNLOAD MAPS TO FINISH THE ROLLBACK PROCESS)
    public static World loadMap(String mapName) {
        return server.createWorld(new WorldCreator(mapName));
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

    public static DotaGame CreateNewGame() {
        return CreateNewGameWithRetryOnFail(0);
    }

    private static DotaGame CreateNewGameWithRetryOnFail(int depth) {
        lastGameID++; //Making a new game, so increase the ID

        int ran = new Random().nextInt(8); //Logic to randomly pick two team colors (Red and blue is boring!)
        Team team1 = Team.values()[ran];
        if (ran == 7) ran = 0;
        Team team2 = Team.values()[ran+1];

        String pluginDataFolder = pluginManager.getPlugin("mDota").getDataFolder() + File.separator; //Static reference to mDota's folder
        String nameOfClonedFolder = "dotaMapClone_" + lastGameID; //The name we will be using for the cloned world folder
        try {
            Files.copy(Paths.get(pluginDataFolder + cleanMapName), Paths.get(Bukkit.getWorldContainer().toPath() + File.separator + nameOfClonedFolder)); //Clone it!
            World newGameWorld = loadMap(nameOfClonedFolder); //Load it onto the server

            DotaGame dg = new DotaGame(team1, team2, (mDota) pluginManager.getPlugin("mDota"), newGameWorld, lastGameID); //Make a new game

            activeGames.put(lastGameID, dg); //Put it into our list of active games
            dg.StartGame(); //Start it up!!
            return dg;
        } catch (IOException e) {
            if (depth > maximumCreationDepth) {
                log.severe(tag + "Failed to create a new game because of IO error");
                log.severe(tag + "Creation was attempted " + depth + " times");
                e.printStackTrace();
                return null;
            }
            else {
                lastGameID--; //Process failed, roll back the ID
                return CreateNewGameWithRetryOnFail(depth + 1);
            }
        }
    }

    public static void StopGame(DotaGame gameToStop) {
        if (gameToStop.isRunning()) gameToStop.EndGame();
        World world = gameToStop.getGameWorld();
        if (world.getPlayers().size() > 0) {
            for (Player p : world.getPlayers()) {
                p.kickPlayer(ChatColor.RED + "You have been kicked because the game you were playing is being shutdown!");
            }
        }
        unloadMap(world.getName());
    }
}
