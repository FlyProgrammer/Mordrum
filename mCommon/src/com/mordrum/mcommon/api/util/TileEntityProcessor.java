package com.mordrum.mcommon.api.util;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.block.BlockState;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 6/20/13
 * Time: 10:16 AM
 */

public abstract class TileEntityProcessor {
	private static final int TICK_REPEAT_DELAY = 1;
	private static final int TICK_DELAY = 1;

	private Queue<Chunk> chunks;
	private Queue<BlockState> tileEntities;

	private final long tickProcessingTimeNano;
	private BukkitTask task;

	public TileEntityProcessor(long tickProcessingTime, TimeUnit tickProcessingUnit) {
		Preconditions.checkNotNull(tickProcessingUnit, "Tick processing unit cannot be NULL.");

		this.chunks = new ArrayDeque<Chunk>();
		this.tickProcessingTimeNano = tickProcessingUnit.toNanos(tickProcessingTime);
	}

	/**
	 * Process a given tile entity.
	 *
	 * @param state - the entity to process.
	 */
	protected abstract void processTileEntity(BlockState state);

	/**
	 * Determine if the entity processor is running.
	 *
	 * @return TRUE if it is, FALSE otherwise.
	 */
	public final boolean isRunning() {
		return task != null;
	}

	/**
	 * Begin processing tile entities.
	 *
	 * @param plugin - the current plugin.
	 */
	public final void start(Plugin plugin) {
		start(Bukkit.getScheduler(), plugin);
	}

	/**
	 * Begin processing tile entities.
	 *
	 * @param scheduler - the current Bukkit scehduler.
	 * @param plugin    - the current plugin.
	 */
	public final void start(BukkitScheduler scheduler, Plugin plugin) {
		Preconditions.checkState(!isRunning(), "Cannot start a processor twice.");
		Preconditions.checkNotNull(scheduler, "Scheduler cannot be NULL.");
		Preconditions.checkNotNull(plugin, "Plugin cannot be NULL.");

		scheduler.runTaskTimer(plugin, getRunnable(), TICK_DELAY, TICK_REPEAT_DELAY);
	}

	/**
	 * Stop processing tile entities.
	 */
	public final void stop() {
		Preconditions.checkState(isRunning(), "Cannot stop a stopped processor.");

		task.cancel();
		task = null;
	}

	private Runnable getRunnable() {
		return new Runnable() {
			@Override
			public void run() {
				long start = System.nanoTime();

				while (true) {
					if (increment()) {
						// Handle timeout
						if (!processTileEntities(start))
							break;

					} else {
						// Don't repeat
						stop();
						break;
					}
				}
			}
		};
	}

	private boolean processTileEntities(long start) {
		while (!tileEntities.isEmpty()) {
			// Timeout?
			if (System.nanoTime() > start + tickProcessingTimeNano)
				return false;

			BlockState state = tileEntities.poll();
			processTileEntity(state);
		}

		// Process more!
		return true;
	}

	private boolean increment() {
		if (isEmpty(tileEntities)) {
			// Process next chunk
			if (!isEmpty(chunks)) {
				tileEntities = new ArrayDeque<BlockState>(
						Arrays.asList(chunks.poll().getTileEntities())
				);
				return true;
			}
		}
		return false;
	}

	private boolean isEmpty(Queue<?> queue) {
		return queue == null || queue.isEmpty();
	}
}