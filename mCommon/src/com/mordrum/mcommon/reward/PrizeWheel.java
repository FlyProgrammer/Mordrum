package com.mordrum.mcommon.reward;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Pattern;

import static org.bukkit.Material.*;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 3/2/13
 * Time: 7:06 PM
 */
public class PrizeWheel { //TODO finish prizewheel API

	int randomRange = 100; //Range of numbers to use
	ArrayList<ItemStack> prizeArray;

	public PrizeWheel() {
		prizeArray = new ArrayList<ItemStack>();

		for (int a = 0; a < 100; a++) {
			if (a < 101) {
				if ((a % 10 == 0) || (a % 9 == 0)) prizeArray.add(new ItemStack(COAL, 8));
				else if (a % 8 == 0) prizeArray.add(new ItemStack(IRON_INGOT, 4));
				else if (a % 7 == 0) prizeArray.add(new ItemStack(GOLD_INGOT, 2));
				else if (a % 6 == 0) prizeArray.add(new ItemStack(APPLE, 6));
				else if (a % 5 == 0) prizeArray.add(new ItemStack(DIAMOND, 1));
				else if (a % 4 == 0) prizeArray.add(new ItemStack(ENDER_PEARL, 3));
				else if (a % 3 == 0) prizeArray.add(new ItemStack(WATER_LILY, 4));
				else if (a % 2 == 0) prizeArray.add(new ItemStack(ARROW, 10));
				else if (a % 1 == 0) prizeArray.add(new ItemStack(SLIME_BALL, 2));
			} else {

			}
		}
	}

	public void Spin(Player p, int x) {
		String preMessage = ChatColor.YELLOW + "Spinning the wheel...";

		Random generator = new Random();
		ArrayList<ItemStack> itemsWon = new ArrayList<ItemStack>();
		int r = 0;
		for (int i = 0; i < x; i++) {
			r = generator.nextInt(100);
			ItemStack itemToAdd = prizeArray.get(r); //Get the item the player won for this spin
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
		String winMessage = ChatColor.YELLOW + "You won: ";
		for (ItemStack item : itemsWon) {
			p.getInventory().addItem(item);
			String messageToAdd = "";
			//If there is more than one item make it plural
			if (item.getAmount() > 1) messageToAdd = item.getAmount() + " " + item.getType().name().toLowerCase() + "s";
			//If this is the last item, make it grammatically correct
			if (itemsWon.indexOf(item) == itemsWon.size() - 1) winMessage = winMessage + "and " + messageToAdd;
			else winMessage = winMessage + messageToAdd + ", ";
		}
		winMessage.replaceAll(Pattern.quote("_"), " ");
		p.sendMessage(winMessage);
	}
}
