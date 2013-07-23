package com.mordrum.mdota.util;

import com.mordrum.mdota.exceptions.PlayerAlreadyInGameException;
import com.mordrum.mdota.exceptions.PlayerNotInGameException;
import com.mordrum.mdota.mDota;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 6/25/13
 * Time: 9:29 PM
 */
public class DotaGame {

    //Data for each team including the team count
    private final Team team1;
    private final Team team2;

    //Data needed to run the game
    private final mDota plugin;
    private final World gameWorld;
    private final Integer gameID;
    public final Map<Location, String> turretLocations = new HashMap<Location, String>();
    public Location team1Point = null;
    public Location team2Point = null;
    public Location team1Bed = null;
    public Location team2Bed = null;
    public Location farAwayLocation = null;

    //Variables for storing info
    private List<String> playersInGame;
    private Map<String, Integer> playerTeam;
    private Map<String, Integer> playerKills;
    private Map<String, Integer> playerDeaths;
    private Map<String, Integer> playerCreepScore;
    private Map<String, Integer> playerTowerScore;

    private Map<String, Boolean> turretstates;

    //Special runtime variables
    private boolean gamePaused;
    private boolean gameRefreshing;
    private boolean gameInProgress = false;

    public DotaGame(Team team1, Team team2, mDota instance, World gameWorld, Integer gameID) {
        this.team1 = team1;
        this.team2 = team2;
        this.plugin = instance;
        this.gameWorld = gameWorld;
        this.gameID = gameID;
    }

    /*
    Starts the game
     */
    public void StartGame() {
        InitializeLocations();
        InitializeWorld();
        InitializeGame();
        String newGameMessage = mDota.tag + "A new DOTA game has started! Type /dota to play!";
        mDota.getmCommon().BroadcastGlobalNetworkMessage(newGameMessage);
        gameInProgress = true;
    }

    /*
    Ends the game
     */
    public void EndGame() {
        gameInProgress = false;
        for (String playerName : playersInGame) {
            Player player = mDota.server.getPlayerExact(playerName);
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);

            try {
                out.writeUTF("Connect");
                out.writeUTF("survival"); // Target Server
            } catch (IOException ex) {
                // Can never happen
            }
            player.sendPluginMessage(this.plugin, "BungeeCord", b.toByteArray()); //Send the player on their way
        }
    }

    /*
    Pauses the game
     */
    public void PauseGame() {
        if (gamePaused) return;
        gamePaused = true;
    }

    /*
    Resumes the game from a paused state
     */
    public void ResumeGame() {

    }

    /*
    Removes a player from the game
     */
    public void RemovePlayer(Player player) throws PlayerNotInGameException {
        RemovePlayer(player.getName());
    }

    /*
    Removes a player from the game
    */
    public void RemovePlayer(String playerName) throws PlayerNotInGameException {
        if (!playersInGame.contains(playerName)) {
            throw new PlayerNotInGameException();
        }
        playersInGame.remove(playerName);
        int teamID = playerTeam.get(playerName);
        if (teamID == 1) team1.decrementCount();
        else team2.decrementCount();
        playerTeam.remove(playerName);
        playerKills.remove(playerName);
        playerDeaths.remove(playerName);
        playerCreepScore.remove(playerName);
        playerTowerScore.remove(playerName);

        Player player = mDota.server.getPlayerExact(playerName);
        if (player != null) {
            player.getInventory().clear();
            player.getInventory().setArmorContents(null);
            player.setHealth(20);
            player.setFoodLevel(20);
        } else mDota.log.warning("Attempted to sanitize player '" + playerName + "' but they were not on the server");
        mDota.log.info("Removed " + playerName + " from game " + gameID);
    }

    /*
    Add a player from the game
     */
    public void AddPlayer(Player player) throws PlayerAlreadyInGameException {
        AddPlayer(player.getName());
    }

    /*
    Add a player from the game
     */
    public void AddPlayer(String playerName) throws PlayerAlreadyInGameException {
        if (playersInGame.contains(playerName)) {
            throw new PlayerAlreadyInGameException();
        }
        playersInGame.add(playerName);

        Player player = mDota.server.getPlayerExact(playerName);
        if (player != null) {
            player.getInventory().clear();
            player.getInventory().setArmorContents(null);
            player.setHealth(20);
            player.setFoodLevel(20);
        } else mDota.log.warning("Attempted to add player '" + playerName + "' but they were not on the server");

        int desiredTeamID;
        if (team1.getCount() > team2.getCount()) desiredTeamID = 2;
        else if (team2.getCount() > team1.getCount()) desiredTeamID = 1;
        else desiredTeamID = new Random().nextInt(3);

        String modifiedPlayerName = playerName;
        if (desiredTeamID == 1) {
            player.setBedSpawnLocation(team1Bed, true);
            modifiedPlayerName = team1.getChatColor() + modifiedPlayerName;
            if (mDota.chatColors) {
                player.setDisplayName(modifiedPlayerName);
            }
            if (mDota.tabListColors) {
                if (playerName.length() > 14) modifiedPlayerName = modifiedPlayerName.substring(0, 14);
                player.setPlayerListName(modifiedPlayerName);
            }

        } else {
            player.setBedSpawnLocation(team2Bed, true);
            modifiedPlayerName = team2.getChatColor() + modifiedPlayerName;
            if (mDota.chatColors) {
                player.setDisplayName(modifiedPlayerName);
            }
            if (mDota.tabListColors) {
                if (playerName.length() > 14) modifiedPlayerName = modifiedPlayerName.substring(0, 14);
                player.setPlayerListName(modifiedPlayerName);
            }
        }

        playerTeam.put(playerName, desiredTeamID);
        playerKills.put(playerName, 0);
        playerDeaths.put(playerName, 0);
        playerCreepScore.put(playerName, 0);
        playerTowerScore.put(playerName, 0);

        player.getInventory().addItem(new ItemStack(Material.STONE_SWORD, 1),
                new ItemStack(Material.COOKED_BEEF, 3), new ItemStack(Material.MAP, 1)); //Give them some starting goods
        player.teleport(player.getBedSpawnLocation()); //Teleport them into the game!

        mDota.log.info("Added " + playerName + " to game " + gameID);
    }

    /*
    Broadcasts a message to all players in this game
     */
    public void BroadcastMessage(String messageToBroadcast) {
        for (Player p : gameWorld.getPlayers()) {
            p.sendMessage(messageToBroadcast);
        }
    }

    /*
    Broadcasts a message to all players on a particular team
    Team 1 = 1, Team 2 = 2
     */
    public void BroadCastMessage(String messageToBroadcast, int team) {
        for (Player p : gameWorld.getPlayers()) {
            int id = playerTeam.get(p.getName());
            if (id == team) p.sendMessage(messageToBroadcast);
        }
    }

    private void InitializeLocations() {
        turretLocations.clear();
        turretLocations.put(new Location(gameWorld, -1158., 55., 411.), "Red Nexus");
        turretLocations.put(new Location(gameWorld, -975., 55., 224.), "Blue Nexus");
        turretLocations.put(new Location(gameWorld, -1093., 54., 343.), "Red Mid Tower");
        turretLocations.put(new Location(gameWorld, -1040., 54., 292.), "Blue Mid Tower");
        turretLocations.put(new Location(gameWorld, -1042., 53., 180.), "Blue Top Inner Tower");
        turretLocations.put(new Location(gameWorld, -1130., 53., 190.), "Blue Top Outer Tower");
        turretLocations.put(new Location(gameWorld, -1193., 53., 260.), "Red Top Outer Tower");
        turretLocations.put(new Location(gameWorld, -1192., 53., 350.), "Red Top Inner Tower");
        turretLocations.put(new Location(gameWorld, -1091., 53., 455.), "Red Bottom Inner Tower");
        turretLocations.put(new Location(gameWorld, -1003., 53., 455.), "Red Bottom Outer Tower");
        turretLocations.put(new Location(gameWorld, -940., 53., 375.), "Blue Bottom Outer Tower");
        turretLocations.put(new Location(gameWorld, -941., 53., 285.), "Blue Bottom Inner Tower");

        team1Point = new Location(gameWorld, -1188., 49., 440.);
        team2Point = new Location(gameWorld, -945., 49., 195.);
        team1Bed = new Location(gameWorld, -1194., 47., 444.);
        team2Bed = new Location(gameWorld, -941., 47., 191.);

        farAwayLocation = new Location(gameWorld, -1448., 4., -78.);
    }

    private void InitializeWorld() {
        //Load red and blue spawnpoints
        team1Point.getChunk().load();
        team2Point.getChunk().load();

        //Turn PVP on and AutoSave off (otherwise the map reset won't work properly)
        gameWorld.setPVP(true);
        gameWorld.setAutoSave(false);
    }

    private void InitializeGame() {
        turretstates.clear();
        for (String value : turretLocations.values()) {
            turretstates.put(value, false);
        }

        playersInGame = new ArrayList<>();
        playerTeam = new HashMap<>();
        playerKills = new HashMap<>();
        playerDeaths = new HashMap<>();
        playerCreepScore = new HashMap<>();
        playerTowerScore = new HashMap<>();
    }

    public Team getTeam1() {
        return team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public World getGameWorld() {
        return gameWorld;
    }

    public List<String> getPlayersInGame() {
        return playersInGame;
    }

    public void setPlayersInGame(List<String> playersInGame) {
        this.playersInGame = playersInGame;
    }

    public Map<String, Boolean> getTurretstates() {
        return turretstates;
    }

    public void setTurretstates(Map<String, Boolean> turretstates) {
        this.turretstates = turretstates;
    }

    public boolean isGamePaused() {
        return gamePaused;
    }

    public void setGamePaused(boolean gamePaused) {
        this.gamePaused = gamePaused;
    }

    public boolean isGameRefreshing() {
        return gameRefreshing;
    }

    public void setGameRefreshing(boolean gameRefreshing) {
        this.gameRefreshing = gameRefreshing;
    }

    public Map<String, Integer> getPlayerTeam() {
        return playerTeam;
    }

    public Map<String, Integer> getPlayerKills() {
        return playerKills;
    }

    public Map<String, Integer> getPlayerDeaths() {
        return playerDeaths;
    }

    public Map<String, Integer> getPlayerCreepScore() {
        return playerCreepScore;
    }

    public Map<String, Integer> getPlayerTowerScore() {
        return playerTowerScore;
    }

    public Integer getID() {
        return gameID;
    }

    public void RecallPlayer(Player player, boolean showMessage) {
        if (playerTeam.get(player.getName()) == 1) player.teleport(team1Point);
        else player.teleport(team2Point);
        if (showMessage) player.sendMessage(mDota.tag + "You have been recalled");
    }

    public boolean IsPlayerInEnemyNexus(Player player) {
        int teamID = playerTeam.get(player.getName());
        if (teamID == 1 && (player.getLocation().distance(team2Point) < 70.0)) return true;
        else if (teamID == 2 && (player.getLocation().distance(team1Point) < 70.0)) return true;
        return false;
    }

    public void setTurretstate(String turretName, boolean state) {
        turretstates.put(turretName, state);
    }

    public boolean getTurretState(String turretName) {
        return turretstates.get(turretName);
    }

    public boolean isRunning() {
        return gameInProgress;
    }
}