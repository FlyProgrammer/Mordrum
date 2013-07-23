package com.mordrum.mcommon.misc;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: Costco
 * Date: 17/06/13
 * Time: 6:20 PM
 */
public class BackstabCritical implements Listener {

    public void onPlayerDamagePlayer(EntityDamageByEntityEvent e) {
        //if (e.getDamager() instanceof Player || e.getEntity() instanceof Player) return; //Exit if not PVP

        Player attacker = (Player) e.getDamager();
        Player defender = (Player) e.getEntity();

        double angleOfAttack = Math.toDegrees(attacker.getLocation().getYaw());
        double angleOfDefense = Math.toDegrees(defender.getLocation().getYaw());
        if (angleOfDefense == 0) angleOfDefense = 360;
        if (angleOfAttack == 0) angleOfAttack = 360;

        double finalAngle = angleOfAttack + (-1*angleOfDefense);
        if ((finalAngle <= 10) || ((angleOfAttack - finalAngle) <= 10)) {
            e.setDamage(e.getDamage() * 3);
            if (defender.getHealth() <= e.getDamage() *3) {
                defender.setHealth(0);
                StringBuilder sb = new StringBuilder();
                sb.append(defender.getName());
                sb.append(" was backstabbed by ");
                sb.append(attacker.getName());
                if (attacker.getItemInHand().hasItemMeta()) {
                    sb.append(" using ");
                    sb.append(attacker.getItemInHand().getItemMeta().getDisplayName());
                }
                defender.getServer().broadcastMessage(sb.toString());
            }
        }
    }
}
