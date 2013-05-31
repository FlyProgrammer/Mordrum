package com.mordrum.moverseer.commands;

import com.mordrum.moverseer.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 5/31/13
 * Time: 12:04 AM
 */
public class TeleportHereCommand extends Command {

	private Main plugin;

	public TeleportHereCommand(Main instance) {
		super(
				instance.getConfig().commands_teleporthere_name,
				instance.getConfig().commands_teleporthere_perm
		);
		plugin = instance;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {

	}
}
