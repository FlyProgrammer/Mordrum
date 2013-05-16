package com.mordrum.mdrug;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 5/12/13
 * Time: 4:43 PM
 */
public class DrugHandler {

	static HashMap<Integer, DrugTemplate> drugMap = new HashMap<>();
	static HashMap<Location, Drug> drugLocations = new HashMap<>();
	static HashMap<String, Map<Integer, Integer>> dosageMap = new HashMap<String, Map<Integer, Integer>>();

	public static Boolean HandleFertilization(Block BlockBeingFertilized, Player PlayerFertilizing) {
		ItemStack itemInHand = PlayerFertilizing.getItemInHand(); //The item in the players hand
		Material fertilizer = itemInHand.getType(); //The material type of the item
		switch(BlockBeingFertilized.getType()) { //switch-case statement to make sure only organic blocks are being interacted with
			case BROWN_MUSHROOM: case RED_MUSHROOM: case LONG_GRASS: case YELLOW_FLOWER: case RED_ROSE: case CACTUS:
			case PUMPKIN: case MELON_BLOCK: case VINE: case SAPLING:
				for (DrugTemplate d : drugMap.values()) { //Loop through all of the available drugs
					if (d.getSteps()[0].getAction() == ProductionAction.Fertilize) { //Sort out the ones that are initiated via fertilization
						if (d.getIngredients()[0] == BlockBeingFertilized.getType() && d.getIngredients()[1] == fertilizer) {
							if (!drugLocations.containsKey(BlockBeingFertilized.getLocation())) {
								Drug newDrug = new Drug(d);
								newDrug.NextStep(); //First step was fertilize, which has been done already
								newDrug.setProducerName(PlayerFertilizing.getName()); //Stamp the player's name onto the drug
								drugLocations.put(BlockBeingFertilized.getLocation(), newDrug); //Add the newly created drug to the list of active drugs
								itemInHand.setAmount(itemInHand.getAmount() - 1); //Update the item in the player's hand
								PlayerFertilizing.setItemInHand(itemInHand);
								BlockBeingFertilized.getWorld().playEffect(BlockBeingFertilized.getLocation(), Effect.STEP_SOUND, BlockBeingFertilized.getTypeId()); //Play a visual effect
								return true; //Fertilization was successful, allow the event to be cancelled properly
							}
						}
					}
				}
		}
		return false; //No fertilization occurred
	}

	/*
	This method will handle the harvesting of a planted drug, such as magic mushrooms. Method does not take a player as
	an parameter to allow the automation of harvesting.
	 */
	public static Boolean HandleHarvesting(Block BlockBeingHarvested) {
		if (drugLocations.containsKey(BlockBeingHarvested.getLocation())) { //If the block being harvested is a drug
			Location l = BlockBeingHarvested.getLocation();
			Drug d = drugLocations.get(l); //Get the drug being harvested
			if (d.getCurrentStep() == d.getSteps().length - 1) { //If drug is on last step
				if (d.getSteps()[d.getCurrentStep()].getAction() == ProductionAction.Harvest) { //and is harvestable
					BlockBeingHarvested.setType(Material.AIR); //Delete the block
					ItemStack drugToSpawn = new ItemStack(d.getIconMaterial(), 1, d.getIconData()); //Make a new itemstack with the drug's information
					ItemMeta i = drugToSpawn.getItemMeta(); //Setup the drugs itemmeta (Custom name + lore)
					i.setDisplayName(d.getName()); //The custom name
					ArrayList<String> loreList = new ArrayList<>(); //Stamp the producer's name on it
					loreList.add(d.getProducerName());
					i.setLore(loreList);
					drugToSpawn.setItemMeta(i); //Finally, set the custom itemmeta to the itemstack
					drugLocations.remove(l); //Remove the drug from the hashmap
					BlockBeingHarvested.getWorld().dropItemNaturally(l, drugToSpawn); //Spawn the drug into the world
					return true; //The block is a drug and is harvestable, make sure the event is cancelled
				}
			}
		}
		return false; //The block was not a drug, was not harvestable, or was not ready to be harvested
	}

	public static Boolean HandleConsumption(ItemStack ItemBeingConsumed, Player PlayerConsuming) {
		ItemStack itemInHand = PlayerConsuming.getItemInHand();
		for (DrugTemplate dt : drugMap.values()) {
			if (itemInHand.getItemMeta().getDisplayName().equalsIgnoreCase(dt.getName())) {
				itemInHand.setAmount(itemInHand.getAmount() - 1);
				PlayerConsuming.setItemInHand(itemInHand);
				PotionEffect effect = dt.getPositiveEffect1().createEffect(2400, 0); //TODO make effect based on drug, not arbitrary numbers
				PlayerConsuming.addPotionEffect(effect, true);
				UpdatePlayerDosage(PlayerConsuming, dt);
				return true; //Consumption occurred, allow event to be cancelled properly
			}
		}
		return false; //No consumption occurred
	}

	//TODO add code to method to handle the updating of player dosages
	private static void UpdatePlayerDosage(Player player, DrugTemplate drugTemplate) {

	}

	public static void RegisterNewDrugTemplate(DrugTemplate dt) {
		drugMap.put(drugMap.size() + 1, dt);
	}
}
