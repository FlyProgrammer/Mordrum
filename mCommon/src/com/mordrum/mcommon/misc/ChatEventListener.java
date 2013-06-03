package com.mordrum.mcommon.misc;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Costco
 * Date: 03/06/13
 * Time: 5:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class ChatEventListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        e.setCancelled(true);
    }
}
