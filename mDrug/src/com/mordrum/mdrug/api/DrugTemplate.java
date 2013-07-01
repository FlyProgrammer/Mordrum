package com.mordrum.mdrug.api;

import com.mordrum.mdrug.util.ProductionStep;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 5/11/13
 * Time: 5:27 PM
 */
public class DrugTemplate {
	//The name of the drug
	private String name;
	//The data needed to give the drug an item/icon
	private Material iconMaterial;
	private Short iconData = 0;
	//The three positive effects that this drug is capable of providing
	private PotionEffectType positiveEffect1;
	private PotionEffectType positiveEffect2;
	private PotionEffectType positiveEffect3;
	//The three negative effects that this drug is capable of providing
	private PotionEffectType negativeEffect1;
	private PotionEffectType negativeEffect2;
	private PotionEffectType negativeEffect3;
	//The dosages required to gain each positive effect
	private int minimumDose1;
	private int minimumDose2;
	private int minimumDose3;
	//The dosages required to gain each negative effect
	private int overDose1;
	private int overDose2;
	private int overDose3;
	//The dose that will kill the player
	private int overDose4;
	//The ingredients for the drug (if an ingredient is undefined, then it and the ones above it are not needed)
	private ItemStack ingredients[];
	//The steps needed to make the drug
	private ProductionStep steps[];
	private int effectDuration;

    public Map<String, Integer> playerDoses;

	public DrugTemplate(String name, Material iconMaterial, PotionEffectType positiveEffect1, ItemStack[] ingredients, ProductionStep[] steps) {
		this.name = name;
		this.iconMaterial = iconMaterial;
		this.positiveEffect1 = positiveEffect1;
		this.ingredients = ingredients;
		this.steps = steps;

        this.playerDoses = new HashMap<>();
	}

	public DrugTemplate(DrugTemplate dt) {
		this.name = dt.name;
		this.iconMaterial = dt.iconMaterial;
		this.iconData = dt.iconData;
		this.positiveEffect1 = dt.positiveEffect1;
		this.positiveEffect2 = dt.positiveEffect2;
		this.positiveEffect3 = dt.positiveEffect3;
		this.negativeEffect1 = dt.negativeEffect1;
		this.negativeEffect2 = dt.negativeEffect2;
		this.negativeEffect3 = dt.negativeEffect3;
		this.minimumDose1 = dt.minimumDose1;
		this.minimumDose2 = dt.minimumDose2;
		this.minimumDose3 = dt.minimumDose3;
		this.overDose1 = dt.overDose1;
		this.overDose2 = dt.overDose2;
		this.overDose3 = dt.overDose3;
		this.overDose4 = dt.overDose4;
		this.ingredients = dt.ingredients;
		this.steps = dt.steps;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Material getIconMaterial() {
		return iconMaterial;
	}

	public void setIconMaterial(Material iconMaterial) {
		this.iconMaterial = iconMaterial;
	}

	public Short getIconData() {
		return iconData;
	}

	public void setIconData(Short iconData) {
		this.iconData = iconData;
	}

	public PotionEffectType getPositiveEffect1() {
		return positiveEffect1;
	}

	public void setPositiveEffect1(PotionEffectType positiveEffect1) {
		this.positiveEffect1 = positiveEffect1;
	}

	public PotionEffectType getPositiveEffect2() {
		return positiveEffect2;
	}

	public void setPositiveEffect2(PotionEffectType positiveEffect2) {
		this.positiveEffect2 = positiveEffect2;
	}

	public PotionEffectType getPositiveEffect3() {
		return positiveEffect3;
	}

	public void setPositiveEffect3(PotionEffectType positiveEffect3) {
		this.positiveEffect3 = positiveEffect3;
	}

	public PotionEffectType getNegativeEffect1() {
		return negativeEffect1;
	}

	public void setNegativeEffect1(PotionEffectType negativeEffect1) {
		this.negativeEffect1 = negativeEffect1;
	}

	public PotionEffectType getNegativeEffect2() {
		return negativeEffect2;
	}

	public void setNegativeEffect2(PotionEffectType negativeEffect2) {
		this.negativeEffect2 = negativeEffect2;
	}

	public PotionEffectType getNegativeEffect3() {
		return negativeEffect3;
	}

	public void setNegativeEffect3(PotionEffectType negativeEffect3) {
		this.negativeEffect3 = negativeEffect3;
	}

	public int getMinimumDose1() {
		return minimumDose1;
	}

	public void setMinimumDose1(int minimumDose1) {
		this.minimumDose1 = minimumDose1;
	}

	public int getMinimumDose2() {
		return minimumDose2;
	}

	public void setMinimumDose2(int minimumDose2) {
		this.minimumDose2 = minimumDose2;
	}

	public int getMinimumDose3() {
		return minimumDose3;
	}

	public void setMinimumDose3(int minimumDose3) {
		this.minimumDose3 = minimumDose3;
	}

	public int getOverDose1() {
		return overDose1;
	}

	public void setOverDose1(int overDose1) {
		this.overDose1 = overDose1;
	}

	public int getOverDose2() {
		return overDose2;
	}

	public void setOverDose2(int overDose2) {
		this.overDose2 = overDose2;
	}

	public int getOverDose3() {
		return overDose3;
	}

	public void setOverDose3(int overDose3) {
		this.overDose3 = overDose3;
	}

	public int getOverDose4() {
		return overDose4;
	}

	public void setOverDose4(int overDose4) {
		this.overDose4 = overDose4;
	}

	public ItemStack[] getIngredients() {
		return ingredients;
	}

	public void setIngredients(ItemStack[] ingredients) {
		this.ingredients = ingredients;
	}

	public ProductionStep[] getSteps() {
		return steps;
	}

	public void setSteps(ProductionStep[] steps) {
		this.steps = steps;
	}

	public void setEffectDuration(int effectDuration) {
		this.effectDuration = effectDuration;
	}

	public int getEffectDuration() {
		return effectDuration;
	}
}
