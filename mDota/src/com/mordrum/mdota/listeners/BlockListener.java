package com.mordrum.mdota.listeners;

import com.mordrum.mdota.mDota;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {

	mDota plugin;

	public BlockListener(mDota plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockBreak(BlockBreakEvent event) {
		if (event.getBlock().getWorld().getName().equals(plugin.WorldName)) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockPlace(BlockPlaceEvent event) {
		if (event.getBlock().getWorld().getName().equals(plugin.WorldName)) {
			event.setCancelled(true);
		}
	}
}
