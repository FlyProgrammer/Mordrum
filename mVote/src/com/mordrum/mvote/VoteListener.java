package com.mordrum.mvote;

import com.mordrum.mcommon.mCommon;
import com.mordrum.mcommon.question.Questioner;
import com.mordrum.mcommon.reward.PrizeWheel;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 3/9/13
 * Time: 1:34 PM
 */
public class VoteListener implements Listener {

	List<String> activeVoters = new ArrayList<>();
	HashMap<String, Integer> voteCountMap = new HashMap<>();
	Main plugin;
	Questioner q;
	PrizeWheel wheel;

	public VoteListener(Main instance) {
		this.plugin = instance;
		q = mCommon.getQuestioner();
		wheel = new PrizeWheel();
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onVote(VotifierEvent e) { //Called when Votifier receives a vote
		final Vote v = e.getVote();
		final Player p = Main.server.getPlayer(v.getUsername());
		if (p == null) return; //Player was not found
		if (!activeVoters.contains(p.getName())) {
			activeVoters.add(p.getName());
			voteCountMap.put(p.getName(), 0);
			Main.server.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
				@Override
				public void run() {
					if (!Questioner.askOpenQuestion(p, ChatColor.YELLOW + "Type anything when you are done voting").equalsIgnoreCase("")) {
						int totalVotes = voteCountMap.get(p.getName());
						voteCountMap.remove(p.getName());
						activeVoters.remove(p.getName());
						String formatted = String.format("&5%s &evoted &5%d &etimes!", p.getDisplayName(), totalVotes);
						Main.server.broadcastMessage(ChatColor.translateAlternateColorCodes('&', formatted));
						try {
							String statement = "INSERT OR REPLACE INTO Votes (id, username,totalvotes)" +
									"VALUES ((select id from Votes where username = '%s')," +
									"'%s'," +
									"coalesce((select totalvotes from Votes where username = '%s') + %s, %s))";
							statement = String.format(statement, p.getName(), p.getName(), p.getName(), totalVotes, totalVotes);
							PreparedStatement ps = Main.sql.prepare(statement);
							Main.sql.query(ps);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						p.sendMessage(ChatColor.YELLOW + "Spinning wheel...");
						wheel.Spin(p, totalVotes);
					}
				}
			});
		}
		voteCountMap.put(p.getName(), voteCountMap.get(p.getName()) + 1);
		p.sendMessage(ChatColor.YELLOW + "Vote received from " + ChatColor.DARK_PURPLE + v.getServiceName());
	}
}