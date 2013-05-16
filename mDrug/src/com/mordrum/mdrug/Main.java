package com.mordrum.mdrug;

import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 5/11/13
 * Time: 4:31 PM
 */
public class Main extends JavaPlugin {

	static Logger log;
	static DrugListener dh;

	public void onEnable() {
		log = this.getLogger();
		AddShrooms();
		dh = new DrugListener();
		this.getServer().getPluginManager().registerEvents(dh, this);
	}

	public void AddShrooms() {
		ProductionStep steps[] = new ProductionStep[2];
		steps[0] = new ProductionStep(ProductionAction.Fertilize, 0);
		steps[1] = new ProductionStep(ProductionAction.Harvest, 0);
		Material ingredients[] = new Material[2];
		ingredients[0] = Material.RED_MUSHROOM;
		ingredients[1] = Material.SUGAR;

		DrugTemplate dt = new DrugTemplate("Magic Mushroom", Material.RED_MUSHROOM, PotionEffectType.CONFUSION, ingredients, steps);
		DrugHandler.RegisterNewDrugTemplate(dt);
	}
}

