package com.mordrum.moverseer.commands;

import com.mordrum.moverseer.Main;
import com.mordrum.moverseer.PlayerRecord;
import com.mordrum.moverseer.mOverseer;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 5/31/13
 * Time: 12:04 AM
 */
public class BanCommand extends Command {

	private Main plugin;

	public BanCommand(Main instance) {
		super(
				instance.getConfig().commands_ban_name,
				instance.getConfig().commands_ban_perm
		);
		plugin = instance;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("Correct usage is /" + plugin.getConfig().commands_ban_name + " Player Reason");
        }
        else {
            String moderatorName = sender.getName();
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                sb.append(args[i]);
            }
            mOverseer.BanPlayer(args[0], true, sb.toString());

        }
	}
}
