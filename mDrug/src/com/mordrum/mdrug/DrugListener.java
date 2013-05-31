package com.mordrum.mdrug;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 5/11/13
 * Time: 5:54 PM
 */
public class DrugListener implements Listener {

	/*
	Checks to see if a player is interacting with a block or the air. Will then check for consumption or fertilization
	and will cancel the event if either is occuring.
	 */
	@EventHandler
	public void OnInteract(PlayerInteractEvent e) {
		ItemStack itemInHand = e.getPlayer().getItemInHand();
		if (itemInHand.getType() != Material.AIR) { //If the player is not holding anything, exit the method
			Action action = e.getAction();
			if (action == Action.RIGHT_CLICK_BLOCK) { //Player may be attempting to fertilize a drug
				if (!DrugHandler.HandleFertilization(e.getClickedBlock(), e.getPlayer())) { //No fertilization occurred
					e.setCancelled(DrugHandler.HandleConsumption(itemInHand, e.getPlayer())); //Check for consumption
				} else {
					e.setCancelled(true); //Fertilization occurred
				}
			} else if (action == Action.RIGHT_CLICK_AIR) { //Player may be attempting to consume a drug
				if (!DrugHandler.HandleConsumption(itemInHand, e.getPlayer())) { //Check for consumption
					e.setCancelled(DrugHandler.HandleMixing(itemInHand, e.getPlayer()));
				} else e.setCancelled(true);
			}
		}
	}

	/*
	Checks to see if a drug is being harvested. If this is the case, the event is cancelled as to avoid a dupe glitch
	 */
	@EventHandler(priority = EventPriority.HIGH)
	public void OnBlockBreak(BlockBreakEvent e) {
		e.setCancelled(DrugHandler.HandleHarvesting(e.getBlock()));
	}

	/*
	Checks to see if a drug is being harvested via water (Semi auto farming). If this is the case, the drug will be
	set to water to avoid visual glitching.
	 */
	@EventHandler(priority = EventPriority.HIGH)
	public void OnWaterBreak(BlockFromToEvent e) {
		if (e.getBlock().getType() == Material.WATER) {
			if (DrugHandler.HandleHarvesting(e.getToBlock())) {
				e.getToBlock().setType(Material.WATER);
			}
		}
	}

}
