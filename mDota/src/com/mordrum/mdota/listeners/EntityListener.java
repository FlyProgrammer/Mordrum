package com.mordrum.mdota.listeners;

import com.mordrum.mdota.mDota;
import com.mordrum.mdota.util.DotaGame;
import org.bukkit.ChatColor;
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
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class EntityListener implements Listener {
	mDota plugin;

	//TODO this entire class D:::::
	public EntityListener(mDota plugin) {
		this.plugin = plugin;
	}

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
        }
        else if (teamID == 2 && mDota.redSpawn.contains(x, z)) {
            player.setHealth(0);
            player.sendMessage(mDota.tag + "You have been killed for attempting to enter the enemy's spawn!");
        }
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player) {
			if (e.getDamager() instanceof Player) {

			}
		}


		if (event.getDamager().getWorld().getName().equals(plugin.WorldName) && event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
			String p1 = ((Player) event.getEntity()).getName();
			String p2 = ((Player) event.getDamager()).getName();
			if (plugin.playerlist.containsKey(p1) && plugin.playerlist.containsKey(p2)) {
				if (plugin.playerlist.get(p1) == plugin.playerlist.get(p2)) {
					event.setCancelled(true);
				}
			} else {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		if (!event.getEntity().getWorld().getName().equals(plugin.WorldName)) {
			return;
		}
		if (plugin.PlayersKeepItems) {
			DamageCause cause = event.getEntity().getLastDamageCause().getCause();
			if (cause == DamageCause.DROWNING) {
				event.getEntity().sendMessage("You died in the pirate cave, so you lost your items!");
			} else if (cause == DamageCause.LAVA) {
				event.getEntity().sendMessage("You died in the lava cave, so you lost your items!");
			} else {
				plugin.playerdeathitems.put(event.getEntity().getName(), event.getEntity().getInventory().getContents());
				plugin.playerdeatharmor.put(event.getEntity().getName(), event.getEntity().getInventory().getArmorContents());
//				event.getEntity().getInventory().clear();
//				for (ItemStack e : event.getDrops()) {
//					System.out.println(e.getType().name());
//				}
//				System.out.println(event.getDrops().size());
				event.getDrops().clear();
			}
		}
		event.setKeepLevel(plugin.PlayersKeepLevel);
		String name = event.getEntity().getName();
		if (plugin.playerdeaths.containsKey(name)) {
			plugin.playerdeaths.put(name, plugin.playerdeaths.get(name) + 1);
		}
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		if (!event.getPlayer().getWorld().getName().equals(plugin.WorldName)) {
			return;
		}
		String name = event.getPlayer().getName();
		if (plugin.playerdeathitems.containsKey(name)) {
			PlayerInventory inven = event.getPlayer().getInventory();
			Integer count = 0;
			for (ItemStack istack : plugin.playerdeathitems.get(name)) {
				if (istack instanceof ItemStack) {
					inven.setItem(count, istack);
				}
				count++;
			}
			//inven.setContents(plugin.playerdeathitems.get(name));
			inven.setArmorContents(plugin.playerdeatharmor.get(name));
			plugin.playerdeathitems.remove(name);
			plugin.playerdeatharmor.remove(name);
		}
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		if (!event.getEntity().getWorld().getName().equals(plugin.WorldName)) {
			return;
		}
		if (event.getEntityType() != EntityType.PLAYER) {
			event.getEntity().getEquipment().setHelmet(null);
			event.getEntity().getEquipment().setChestplate(null);
			event.getEntity().getEquipment().setLeggings(null);
			event.getEntity().getEquipment().setBoots(null);
			if (event.getEntity().getKiller() instanceof Player) {
				Player player = event.getEntity().getKiller();
				for (ItemStack istack : event.getDrops()) {
					player.getInventory().addItem(istack);
				}
				if (plugin.playercs.containsKey(player.getName())) {
					plugin.playercs.put(player.getName(), plugin.playercs.get(player.getName()) + 1);
				}
			}
			event.getDrops().clear();
		} else {
			if (event.getEntity().getKiller() instanceof Player) {
				String name = event.getEntity().getKiller().getName();
				if (plugin.playerkills.containsKey(name)) {
					plugin.playerkills.put(name, plugin.playerkills.get(name) + 1);
				}
			}

		}
	}

	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		if (!event.getPlayer().getWorld().getName().equals(plugin.WorldName)) {
			return;
		}
		String name = event.getPlayer().getName();
		if (plugin.playerlist.containsKey(name)) {
			if (plugin.playerlist.get(name) == 1) {
				if (event.getItem().getLocation().distance(plugin.BluePoint) < 70.0) {
					event.setCancelled(true);
				}
			} else if (plugin.playerlist.get(name) == 2) {
				if (event.getItem().getLocation().distance(plugin.RedPoint) < 70.0) {
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (!event.getPlayer().getWorld().getName().equals(plugin.WorldName)) {
			return;
		}
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.hasBlock()) {
			if (event.getClickedBlock().getType() == Material.DISPENSER) {
				event.setCancelled(true);
			} else if (event.getClickedBlock().getType() == Material.LEVER) {
				if (plugin.RedCount == 0 || plugin.BlueCount == 0) {
					event.setCancelled(true);
					event.getPlayer().sendMessage("Waiting for opposing players to join.");
				}
			} else if (event.getClickedBlock().getType() == Material.CHEST) {
				String name = event.getPlayer().getName();
				if (plugin.playerlist.containsKey(name)) {
					if (plugin.playerlist.get(name) == 1) {
						if (plugin.playerlist.containsKey(name)) {
							if (plugin.playerlist.get(name) == 1) {
								if (event.getClickedBlock().getLocation().distance(plugin.BluePoint) < 70.0) {
									event.setCancelled(true);
								}
							} else if (plugin.playerlist.get(name) == 2) {
								if (event.getClickedBlock().getLocation().distance(plugin.BluePoint) < 70.0) {
									event.setCancelled(true);
								}
							}
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		if (plugin.GameInProgress && event.getLocation().getWorld().getName().equals(plugin.WorldName)) {
			Location exloc = event.getLocation();
			for (Location tuloc : plugin.turretlocations.keySet()) {
				if (tuloc.distance(exloc) < 10) {
					String turretname = plugin.turretlocations.get(tuloc);
					if (plugin.turretstates.get(turretname) == false) {
						plugin.broadcastMessage(ChatColor.RED + "[DOTA]" + turretname + " has been destroyed.");
						plugin.turretstates.put(turretname, true);
						if (turretname.equals("Red Nexus")) {
							plugin.broadcastMessage(ChatColor.RED + "[DOTA]" + ChatColor.BLUE + "Blue Team has won!");
							plugin.GameInProgress = false;
							plugin.resetMap();
						} else if (turretname.equals("Blue Nexus")) {
							plugin.broadcastMessage(ChatColor.RED + "[DOTA]" + ChatColor.RED + "Red Team has won!");
							plugin.GameInProgress = false;
							plugin.resetMap();
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		if (!event.getTo().getWorld().getName().equals(plugin.WorldName)) {
			String name = event.getPlayer().getName();
			if (plugin.playerlist.containsKey(name) && event.getFrom().getWorld().getName().equals(plugin.WorldName)) {
				plugin.playerlist.remove(name);
				event.getPlayer().setPlayerListName(event.getPlayer().getName());
				plugin.playerdeaths.remove(name);
				plugin.playerkills.remove(name);
				plugin.playerdeathitems.remove(name);
				plugin.playerdeatharmor.remove(name);
				event.getPlayer().getInventory().clear();
				event.getPlayer().getInventory().setArmorContents(null);
				event.getPlayer().setBedSpawnLocation(event.getTo().getWorld().getSpawnLocation(), true);
				event.getPlayer().sendMessage(ChatColor.DARK_RED + "You have left the game.");
			}
		}
	}
}
