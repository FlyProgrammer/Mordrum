package com.mordrum.mcommon;

import com.mordrum.mcommon.api.util.ServerMonitor;
import org.bukkit.command.CommandSender;
import se.ranzdo.bukkit.methodcommand.Arg;
import se.ranzdo.bukkit.methodcommand.Command;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 6/24/13
 * Time: 9:56 PM
 */
public class Commands {

    @Command(identifier = "ekg", description = "Server Monitoring Command", onlyPlayers = false)
    public void ekgCommand(CommandSender sender, @Arg(name = "world") String world) {
        if (world.equalsIgnoreCase("all")) ServerMonitor.PrintAllWorldInfo(sender);
        else ServerMonitor.PrintDetailedWorldInfo(sender, world);
    }
}
