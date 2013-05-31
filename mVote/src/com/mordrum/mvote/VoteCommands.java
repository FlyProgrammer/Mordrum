package com.mordrum.mvote;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import org.bukkit.entity.Player;
import se.ranzdo.bukkit.methodcommand.Command;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 3/9/13
 * Time: 1:50 PM
 */
public class VoteCommands {

	//TODO Top voters, edit a voter, edit all voters, view a voter
	@Command(identifier = "vote", description = "Sample vote command", onlyPlayers = true)
	public void FakeVote(Player sender) {
		Vote v = new Vote();
		v.setAddress(sender.getAddress().getHostName());
		v.setUsername(sender.getName());
		v.setServiceName("debug.vote.address");
		VotifierEvent ve = new VotifierEvent(v);
		Main.server.getPluginManager().callEvent(ve);
	}
}
