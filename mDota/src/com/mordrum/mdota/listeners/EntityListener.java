package com.mordrum.mdota.listeners;

import com.mordrum.mdota.mDota;
import com.mordrum.mdota.util.DotaGame;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class EntityListener implements Listener {

    /*
    If the player attempts to enter the enemy's spawn, kill them
     */
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Location loc = event.getPlayer().getLocation();
        Integer x = loc.getBlockX();
        Integer z = loc.getBlockZ();
        Player player = event.getPlayer();
        String playerName = player.getName();

        DotaGame dg = mDota.activeGames.get(mDota.playerList.get(playerName));
        int teamID = dg.getPlayerTeam().get(playerName);

        if (teamID == 1 && mDota.blueSpawn.contains(x, z)) {
            player.setHealth(0);
            player.sendMessage(mDota.tag + "You have been killed for attempting to enter the enemy's spawn!");
        } else if (teamID == 2 && mDota.redSpawn.contains(x, z)) {
            player.setHealth(0);
            player.sendMessage(mDota.tag + "You have been killed for attempting to enter the enemy's spawn!");
        }
    }

    /*
    Makes sure that no friendly fire occurs
     */
    //TODO make sure this works for arrows
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            if (e.getDamager() instanceof Player) {
                //Get player names and the game they are playing in
                String attackerName = ((Player) e.getDamager()).getName();
                String defenderName = ((Player) e.getEntity()).getName();
                DotaGame dg = mDota.activeGames.get(attackerName);

                //Check for friendly fire
                int player1TeamID = dg.getPlayerTeam().get(attackerName);
                int player2TeamID = dg.getPlayerTeam().get(defenderName);
                if (player1TeamID == player2TeamID) e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        //Keep items on death if configured
        if (mDota.retainItems) {
            DamageCause cause = event.getEntity().getLastDamageCause().getCause();
            if (cause == DamageCause.DROWNING) {
                event.getEntity().sendMessage(mDota.tag + "You died in the pirate cave, so you lost your items!");
            } else if (cause == DamageCause.LAVA) {
                event.getEntity().sendMessage(mDota.tag + "You died in the lava cave, so you lost your items!");
            } else {
                //TODO code to make players keep items (Store the items until they respawn)
            }
            //Let players keep their levels on death if configured
            event.setKeepLevel(mDota.retainLevels);
            //Add a death to the player's score
            String playerName = event.getEntity().getName();
            DotaGame dg = mDota.activeGames.get(mDota.playerList.get(playerName));
            dg.getPlayerDeaths().put(playerName, dg.getPlayerDeaths().get(playerName) + 1);
        }
    }

    //TODO keep items on death code
//	@EventHandler
//	public void onPlayerRespawn(PlayerRespawnEvent event) {
//
//	}

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        //If a player kills something
        if (event.getEntity().getKiller() != null) {
            Player killer = event.getEntity().getKiller();
            DotaGame dg = mDota.activeGames.get(mDota.playerList.get(killer.getName()));
            //If a player kills a mob, put the items directly into the player's inventory
            if (event.getEntityType() != EntityType.PLAYER) {
                for (ItemStack item : event.getDrops()) {
                    killer.getInventory().addItem(item);
                }
                dg.getPlayerCreepScore().put(killer.getName(), dg.getPlayerCreepScore().get(killer.getName()));
                //Make sure the mob doesn't DROP anything onto the ground
                event.getDrops().clear();
            }
            //Give the player a point for killing another player
            else {
                dg.getPlayerKills().put(killer.getName(), dg.getPlayerKills().get(killer.getName()) + 1);
            }
        }
    }

    /*
    Prevent players from picking up items in the enemy team's nexus
     */
    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        String playerName = event.getPlayer().getName();
        DotaGame dg = mDota.activeGames.get(mDota.playerList.get(playerName));
        if (dg.IsPlayerInEnemyNexus(event.getPlayer())) event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        //If the player is right clicking a block
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.hasBlock()) {
            Material blockType = event.getClickedBlock().getType();
            //If player is interacting with a dispenser, block it
            if (blockType == Material.DISPENSER) {
                event.setCancelled(true);
            }
            //If they're interacting with a lever, make sure the other team has enough players
            else if (blockType == Material.LEVER) {
                Player player = event.getPlayer();
                DotaGame dg = mDota.activeGames.get(mDota.playerList.get(player.getName()));
                if (dg.getTeam1().getCount() < 3 || dg.getTeam2().getCount() < 3) {
                    event.setCancelled(true);
                    player.sendMessage(mDota.tag + "Waiting for opposing players to join.");
                }
            }
            //If they're interacting with a chest in the enemy's nexus, block it
            else if (blockType == Material.CHEST) {
                Player player = event.getPlayer();
                DotaGame dg = mDota.activeGames.get(mDota.playerList.get(player.getName()));
                if (dg.IsPlayerInEnemyNexus(player)) event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        DotaGame dg = mDota.activeGames.get(mDota.playerList.get(event.getLocation().getWorld().getPlayers().get(0).getName()));

        Location exLoc = event.getLocation();
        for (Location tuLoc : dg.turretLocations.keySet()) {
            if (exLoc.distance(tuLoc) < 10) {
                String turretName = dg.turretLocations.get(tuLoc);
                if (!dg.getTurretState(turretName)) { //If turret is not already destroyed
                    dg.BroadcastMessage(mDota.tag + turretName + " has been destroyed!");
                    dg.setTurretstate(turretName, true);

                    if (turretName.endsWith("Nexus")) { //If the nexus was destroyed
                        if (tuLoc.distance(dg.team1Point) < 70.0) {
                            dg.BroadcastMessage(mDota.tag + dg.getTeam2().getName() + " has won!");
                        } else if (tuLoc.distance(dg.team2Point) < 70.0) {
                            dg.BroadcastMessage(mDota.tag + dg.getTeam1().getName() + " has won!");
                        }
                        dg.EndGame(); //Game has finished, end it
                    }
                }
            }
        }
    }
}
