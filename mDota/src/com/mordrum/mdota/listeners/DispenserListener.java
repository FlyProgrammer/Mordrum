package com.mordrum.mdota.listeners;

import com.mordrum.mdota.mDota;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dispenser;
import org.bukkit.material.SpawnEgg;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 6/25/13
 * Time: 9:24 PM
 */
public class DispenserListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockDispenseEvent(BlockDispenseEvent e) {
        if (e.getItem().getType() == Material.MONSTER_EGG) {
            Dispenser disp = (Dispenser) e.getBlock().getState().getData(); //Get the dispenser
            SpawnEgg egg = (SpawnEgg) e.getItem().getData(); //Get the egg being spawned
            //Do some fancy stuff involving block faces (Investigate all of this)
            BlockFace face = disp.getFacing();
            if (face == BlockFace.EAST) {
                face = BlockFace.NORTH;
            } else if (face == BlockFace.SOUTH) {
                face = BlockFace.EAST;
            } else if (face == BlockFace.WEST) {
                face = BlockFace.SOUTH;
            } else if (face == BlockFace.NORTH) {
                face = BlockFace.WEST;
            }
            //Spawn the mob based on the egg and the position of the dispenser
            LivingEntity newEntity = (LivingEntity) e.getBlock().getWorld().spawnEntity(
                    e.getBlock().getRelative(face).getLocation().add(.5, -3.0, .5),
                    egg.getSpawnedType());
            if (mDota.stripMobArmor) { //Remove all of the mob's armor
                newEntity.getEquipment().setHelmet(null);
                newEntity.getEquipment().setChestplate(null);
                newEntity.getEquipment().setLeggings(null);
                newEntity.getEquipment().setBoots(null);
            }
            if (mDota.mobHeadgear) { //Give the mob a helemt so it doesn't burn in sunlight
                if (newEntity instanceof Monster && (newEntity.getEquipment().getHelmet() == null || newEntity.getEquipment().getHelmet().getType() == Material.AIR)) {
                    newEntity.getEquipment().setHelmet(new ItemStack(Material.LEATHER_HELMET));
                }
            }
            e.setCancelled(true); //Make sure the dispenser stays full
        }
    }
}
