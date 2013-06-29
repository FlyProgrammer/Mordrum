package com.mordrum.mcommon.microfeatures;

import com.mordrum.mcommon.mCommon;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 6/20/13
 * Time: 9:45 AM
 */
public class LongGrassRemoveListener implements Listener {

	mCommon plugin;
	List<Chunk> chunkCleanseQueue;
	List<Chunk> cleansedChunks;

	public LongGrassRemoveListener(mCommon instance) {
		plugin = instance;
		chunkCleanseQueue = new ArrayList<>();
		cleansedChunks = new ArrayList<>();
		StartChunkCleanseThread();
	}

	private void StartChunkCleanseThread() {
		mCommon.server.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			public void run() {
				if (chunkCleanseQueue.isEmpty()) return;
				List<Chunk> chunksToCleanse = new ArrayList<>();
				int portionOfQueue = 0;
				if (chunkCleanseQueue.size() > 1000) portionOfQueue = (chunkCleanseQueue.size() / 10);
				else if (chunkCleanseQueue.size() < 25) portionOfQueue = chunkCleanseQueue.size();
				else portionOfQueue = 25;
				chunksToCleanse.addAll(chunkCleanseQueue.subList(0, portionOfQueue));
				for (Chunk c : chunksToCleanse) {
					int bx = c.getX() << 4;
					int bz = c.getZ() << 4;
					int i = 0;
					World world = c.getWorld();

					for (int xx = bx; xx < bx + 16; xx++) {
						for (int zz = bz; zz < bz + 16; zz++) {
							for (int yy = 40; yy < 128; yy++) {
								int typeId = world.getBlockTypeIdAt(xx, yy, zz);
								if (typeId == 31) {
									world.getBlockAt(xx, yy, zz).setType(Material.AIR);
									i++;
								}
							}
						}
					}
				}
				chunkCleanseQueue.removeAll(chunksToCleanse);
				cleansedChunks.addAll(chunksToCleanse);
				if (chunkCleanseQueue.size() > 0)
					mCommon.server.getLogger().info("Size of long grass cleanse queue: " + chunkCleanseQueue.size());
			}
		}, 20L, 20L);
	}


	@EventHandler
	public void onChunkLoad(ChunkLoadEvent e) {
		if (!cleansedChunks.contains(e.getChunk())) chunkCleanseQueue.add(e.getChunk());
	}

	@EventHandler
	public void onGrassTill(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getClickedBlock().getType() == Material.GRASS) {
				Material itemMat = e.getPlayer().getItemInHand().getType();
				if (itemMat == Material.WOOD_HOE || itemMat == Material.IRON_HOE || itemMat == Material.GOLD_HOE || itemMat == Material.DIAMOND_HOE) {
					e.getPlayer().getWorld().dropItemNaturally(e.getClickedBlock().getLocation(), new ItemStack(Material.SEEDS));
				}
			}
		}
	}
}
