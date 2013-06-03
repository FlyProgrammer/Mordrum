package com.mordrum.moverseer.commands;

import com.mordrum.moverseer.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 5/31/13
 * Time: 12:04 AM
 */
public class TeleportCommand extends Command {

	private Main plugin;

	public TeleportCommand(Main instance) {
		super(
				instance.getConfig().commands_teleport_name,
				instance.getConfig().commands_teleport_perm
		);
		plugin = instance;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) { //Player is calling the command
            if (args.length <= 0 || args.length > 2) {
                sender.sendMessage("Correct usage is /" + plugin.getConfig().commands_teleport_name + " Player1 (Player2)");
            }
            else if (args.length == 1) {
                ProxiedPlayer player1 = (ProxiedPlayer) sender;
                ProxiedPlayer player2 = plugin.getProxy().getPlayer(args[0]);
                if (player1 == null) sender.sendMessage("Player not found");
                else {
                    player1.connect(player2.getServer().getInfo());
                }
            }
            else if (args.length == 2) {
                ProxiedPlayer player1 = plugin.getProxy().getPlayer(args[0]);
                ProxiedPlayer player2 = plugin.getProxy().getPlayer(args[1]);
                if (player1 == null) sender.sendMessage("Player1 not found");
                if (player2 == null) sender.sendMessage("Player2 not found");
                else {
                    player1.connect(player2.getServer().getInfo());
                }
            }
        }
        else { //Player is console
            if (args.length != 2) {
                sender.sendMessage("Correct usage is /" + plugin.getConfig().commands_teleport_name + " Player1 Player2");
            }
            else {
                ProxiedPlayer player1 = plugin.getProxy().getPlayer(args[0]);
                ProxiedPlayer player2 = plugin.getProxy().getPlayer(args[1]);
                if (player1 == null) sender.sendMessage("Player1 not found");
                if (player2 == null) sender.sendMessage("Player2 not found");
                else {
                    player1.connect(player2.getServer().getInfo());
                }
            }
        }
	}
}
