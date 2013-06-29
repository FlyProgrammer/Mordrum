package com.mordrum.mcommon.api.reward;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import static org.bukkit.Material.*;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 3/2/13
 * Time: 7:06 PM
 */
public class PrizeWheel {

	static int randomRange = 100; //Range of numbers to use
    static ItemStack[] prizes;

    public static void SeedPrizes() {
        for (int i = 0; 1 < randomRange; i++) {
            //Common prizes
            if (i < 10) prizes[i] = new ItemStack(COAL, 8);
            else if(i < 20) prizes[i] = new ItemStack(IRON_INGOT, 4);
            else if(i < 30) prizes[i] = new ItemStack(GOLD_INGOT, 2);
            else if(i < 40) prizes[i] = new ItemStack(APPLE, 6);
            else if(i < 50) prizes[i] = new ItemStack(DIAMOND, 1);
            else if(i < 60) prizes[i] = new ItemStack(ENDER_PEARL, 3);
            else if(i < 70) prizes[i] = new ItemStack(WATER_LILY, 5);

            //Uncommon prizes - 5%
            else if(i < 75) prizes[i] = new ItemStack(SULPHUR, 6);
            else if(i < 80) prizes[i] = new ItemStack(ARROW, 16);
            else if(i < 85) prizes[i] = new ItemStack(SLIME_BALL, 3);
            else if(i < 90) prizes[i] = new ItemStack(FIREBALL, 5);

            //Rare prizes - 2% chance
            else if(i < 92) prizes[i] = new ItemStack(DIAMOND_BLOCK, 1);
            else if(i < 94) prizes[i] = new ItemStack(GOLD_BLOCK, 2);
            else if(i < 96) prizes[i] = new ItemStack(LAPIS_BLOCK, 2);

            //Very rare prizes - 1% chance
            else if(i == 96) prizes[i] = new ItemStack(MONSTER_EGG, 1, (short) 91); //Sheep spawner
            else if(i == 97) prizes[i] = new ItemStack(MONSTER_EGG, 1, (short) 93); //Chicken spawner
            else if(i == 98) prizes[i] = new ItemStack(MONSTER_EGG, 1, (short) 90); //Pig spawner
            else if(i == 99) prizes[i] = new ItemStack(MONSTER_EGG, 1, (short) 92); //Cow spawner
        }
    }

	public static void Spin(Player p, int x) {
		Random generator = new Random();
		List<ItemStack> itemsWon = new ArrayList<>();
		for (int i = 0; i < x; i++) {
			int r = generator.nextInt(randomRange);
			ItemStack itemToAdd = prizes[r]; //Get the item the player won for this spin
			if (!itemsWon.isEmpty()) {
				Boolean wasFound = false;
				for (ItemStack itemInLoop : itemsWon) {
					if (itemInLoop.getType() == itemToAdd.getType()) {
						itemInLoop.setAmount(itemInLoop.getAmount() + itemToAdd.getAmount());
						wasFound = true;
					}
				}
				if (!wasFound) {
					itemsWon.add(itemToAdd);
				}
			} else itemsWon.add(itemToAdd);
		}
        StringBuilder sb = new StringBuilder();
        sb.append(ChatColor.YELLOW);
        sb.append("You won: ");
		for (ItemStack item : itemsWon) {
			p.getInventory().addItem(item);
			String messageToAdd = "";
			//If there is more than one item make it plural
			if (item.getAmount() > 1) messageToAdd = item.getAmount() + " " + item.getType().name().toLowerCase() + "s";
			//If this is the last item, make it grammatically correct
			if (itemsWon.indexOf(item) == itemsWon.size() - 1) {
                sb.append("and ");
                sb.append(messageToAdd);
            }
			else {
                sb.append(messageToAdd);
                sb.append(", ");
            }
		}
		p.sendMessage(sb.toString().replaceAll(Pattern.quote("_"), " "));
	}
}
