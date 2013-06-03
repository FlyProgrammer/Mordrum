package com.mordrum.moverseer;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 5/31/13
 * Time: 1:00 AM
 * This class is the "API" for mOverseer.
 */
public class mOverseer {

	public List<String> bannedPlayers;

	private Main plugin;
	private IOHandler IO;

	protected mOverseer(Main instance) {
		plugin = instance;
		IO = plugin.getIO();
	}

	protected void Initialize() {

	}

	protected void ShutDown() {

	}

	public Boolean isPlayerBanned(ProxiedPlayer PlayerToCheck) {
		return isPlayerBanned(PlayerToCheck.getName());
	}

	public Boolean isPlayerBanned(String PlayerToCheck) {
        if (IO.playerRecordMap.containsKey(PlayerToCheck)) {
            PlayerRecord pr = IO.playerRecordMap.get(PlayerToCheck);
            return pr.getBanState();
        }
        return false;
	}

	public void setBanStateForPlayer(ProxiedPlayer PlayerToSet, Boolean BanState) {
		setBanStateForPlayer(PlayerToSet.getName(), BanState);
	}

	public void setBanStateForPlayer(String PlayerToSet, Boolean BanState) {
        PlayerRecord pr = null;
        if (IO.playerRecordMap.containsKey(PlayerToSet)) {
            pr = IO.playerRecordMap.get(PlayerToSet);
            pr.setBanState(BanState);
        }
        else {
            pr = new PlayerRecord();
            pr.setBanState(BanState);
            IO.playerRecordMap.put(PlayerToSet, pr);
        }
	}

    public void setBanReasonForPlayer(ProxiedPlayer PlayerToSet, String BanReason) {
        setBanReasonForPlayer(PlayerToSet.getName(), BanReason);
    }

    public void setBanReasonForPlayer(String PlayerToSet, String BanReason) {
        PlayerRecord pr = null;
        if (IO.playerRecordMap.containsKey(PlayerToSet)) {
            pr = IO.playerRecordMap.get(PlayerToSet);
            pr.setBanReason(BanReason);
        }
        else {
            pr = new PlayerRecord();
            pr.setBanReason(BanReason);
            IO.playerRecordMap.put(PlayerToSet, pr);
        }
    }

    public void BanPlayer(ProxiedPlayer PlayerToSet, Boolean BanState, String BanReason) {
        BanPlayer(PlayerToSet.getName(), BanState, BanReason);
    }

    public static void BanPlayer(String PlayerToSet, Boolean BanState, String BanReason) {
        PlayerRecord pr = null;
        if (IO.playerRecordMap.containsKey(PlayerToSet)) {
            pr = IO.playerRecordMap.get(PlayerToSet);
            pr.setBanState(BanState);
            pr.setBanReason(BanReason);
        }
        else {
            pr = new PlayerRecord();
            pr.setBanState(BanState);
            pr.setBanReason(BanReason);
            IO.playerRecordMap.put(PlayerToSet, pr);
        }
    }
}
