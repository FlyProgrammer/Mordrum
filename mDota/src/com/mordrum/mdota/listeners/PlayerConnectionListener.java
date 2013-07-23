package com.mordrum.mdota.listeners;

import com.mordrum.mdota.exceptions.PlayerAlreadyInGameException;
import com.mordrum.mdota.exceptions.PlayerNotInGameException;
import com.mordrum.mdota.mDota;
import com.mordrum.mdota.util.DotaGame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 6/26/13
 * Time: 12:48 PM
 */
public class PlayerConnectionListener implements Listener {

    private final mDota plugin;
    private List<String> playersToSendBackToLobby = new ArrayList<>();

    public PlayerConnectionListener(mDota instance) {
        plugin = instance;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        String playerName = e.getPlayer().getName();

        //If they player previously quit (cleanly), remove them from the server
        if (playersToSendBackToLobby.contains(playerName)) {
            playersToSendBackToLobby.remove(playerName);

            plugin.SendPlayerToLobby(e.getPlayer());
        } else {
            if (mDota.playerList.containsKey(playerName)) { //Player lost connection, put them back in the game (in a safe spot)
                DotaGame dg = mDota.activeGames.get(mDota.playerList.get(playerName));
                if (dg == null) {
                    e.getPlayer().sendMessage(mDota.tag + "Unfortunately the game has ended and you have been returned to the Survival server");
                    plugin.SendPlayerToLobby(e.getPlayer());
                }
                else {
                    dg.RecallPlayer(e.getPlayer(), false);
                    e.getPlayer().sendMessage(mDota.tag + "Welcome back! You have been returned to a safe location!");
                }
            } else {
                for (DotaGame dg : mDota.activeGames.values()) { //Get all available games
                    if (dg.getPlayersInGame().size() < mDota.maximumPlayersPerGame) { //If the player will fit in the game
                        try {
                            dg.AddPlayer(e.getPlayer()); //Attempt to add them
                            return;
                        } catch (PlayerAlreadyInGameException e1) {
                            mDota.log.severe("Could not add player '" + playerName + "' to game " + dg.getID() + " because they were already in it");
                            e1.printStackTrace();
                        }
                    }
                }
                //This code will only execute if we couldn't find a game for the player
                DotaGame dg = mDota.CreateNewGame();
                try {
                    dg.AddPlayer(e.getPlayer());
                } catch (PlayerAlreadyInGameException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent e) {
        if (isCleanQuit(e.getQuitMessage())) { //If the hit the quit button themselves
            String playerName = e.getPlayer().getName();
            try {
                mDota.activeGames.get(mDota.playerList.get(e.getPlayer().getName())).RemovePlayer(playerName);
            } catch (PlayerNotInGameException e1) {
                mDota.log.severe("Attempted to remove a player that was not in a game");
                e1.printStackTrace();
            }
            playersToSendBackToLobby.add(e.getPlayer().getName());
            mDota.playerList.remove(playerName);
        }
    }

    public boolean isCleanQuit(String reason) {
        return !(reason.equalsIgnoreCase("disconnect.timeout") || reason.equalsIgnoreCase("disconnect.overflow") || reason.equalsIgnoreCase("disconnect.genericreason"));
    }
}
