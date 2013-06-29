package com.mordrum.mcommon.microfeatures;

import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Costco
 * Date: 03/06/13
 * Time: 5:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class CreativeOverkillListener implements Listener {

	@EventHandler
	public void onPlayerDamage(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			if (!(e.getEntity() instanceof Player) && (e.getEntity() instanceof LivingEntity)) {
				if (((Player) e.getDamager()).getGameMode() == GameMode.CREATIVE) {
					LivingEntity le = (LivingEntity) e.getEntity();
					le.setHealth(0);
				}
			}
		}
	}
}
