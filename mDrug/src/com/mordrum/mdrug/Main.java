package com.mordrum.mdrug;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
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

	public void AddHardCodedDrugs() {

	}

	public void AddBantam() { //Bantam, green dye and sugar
		ProductionStep steps[] = new ProductionStep[1];
		steps[0] = new ProductionStep(ProductionAction.Combine, 0);
		ItemStack ingredients[] = new ItemStack[2];
		ingredients[0] = new ItemStack(Material.SUGAR, 1);
		ingredients[1] = new ItemStack(Material.INK_SACK, 1, (short) 2);

		DrugTemplate dt = new DrugTemplate("Bantam", Material.INK_SACK, PotionEffectType.DAMAGE_RESISTANCE, ingredients, steps);
		dt.setPositiveEffect2(PotionEffectType.INCREASE_DAMAGE);
		dt.setNegativeEffect1(PotionEffectType.SLOW_DIGGING);
		dt.setMinimumDose1(3);
		dt.setMinimumDose2(5);
		dt.setOverDose1(3);
		dt.setOverDose4(7);
		dt.setEffectDuration(300);
		DrugHandler.RegisterNewDrugTemplate(dt);
	}

	public void AddShrooms() {
		ProductionStep steps[] = new ProductionStep[2];
		steps[0] = new ProductionStep(ProductionAction.Fertilize, 0);
		steps[1] = new ProductionStep(ProductionAction.Harvest, 0);
		ItemStack ingredients[] = new ItemStack[2];
		ingredients[0] = new ItemStack(Material.RED_MUSHROOM, 1);
		ingredients[1] = new ItemStack(Material.SUGAR);

		DrugTemplate dt = new DrugTemplate("Magic Mushroom", Material.RED_MUSHROOM, PotionEffectType.CONFUSION, ingredients, steps);
		dt.setMinimumDose1(1);
		dt.setEffectDuration(30);
		DrugHandler.RegisterNewDrugTemplate(dt);
	}
}

