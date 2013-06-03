package com.mordrum.moverseer;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 5/31/13
 * Time: 12:42 AM
 * IO handling class that is in charge of managing mOverseer's SQLite database. Everything is cached in the mOverseer
 * class and is only saved on shutdown, or when the appropriate command is executed.
 */
public class IOHandler {

	Main plugin;
	Connection connection = null;
    public Map<String, PlayerRecord> playerRecordMap;

	public IOHandler(Main instance) {
		plugin = instance;
        playerRecordMap = new HashMap<>();
		EstablishConnection();
		FetchRecords();
	}

	private void EstablishConnection() {
		try {
			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:" + plugin.getDataFolder().getAbsolutePath() + "storage.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.

			statement.executeUpdate(
					"create table if not exists players" +
					"(id INTEGER, username VARCHAR(20), banState BOOLEAN, banReason TEXT, homeX INTEGER, homeY INTEGER, " +
					"homeZ INTEGER, homeWorld INTEGER, "
            );
		} catch (SQLException e) {
			// if the error message is "out of memory",
			// it probably means no database file is found
			System.err.println(e.getMessage());
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				// connection close failed.
				System.err.println(e);
			}
		}
	}

	private void FetchRecords() {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from players");
            while (rs.next()) {
                // read the result set
                String username = rs.getString("username");
                Boolean banState = rs.getBoolean("banState");
                String banReason = rs.getString("banReason");
                int homeX = rs.getInt("homeX");
                int homeY = rs.getInt("homeY");
                int homeZ = rs.getInt("homeZ");
                int homeWorld = rs.getInt("homeWorld");

                PlayerRecord pr = new PlayerRecord(banState, banReason, homeX, homeY, homeZ, homeWorld);
                playerRecordMap.put(username, pr);
            }
            System.out.print("[mOverseer]Added " + playerRecordMap.size() + " records to cache");
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

    public void SaveRecords() {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.setQueryTimeout(30); //set timeout to 30 seconds
            statement.executeUpdate("drop table if exists players");
            for (String s : playerRecordMap.keySet()) {
                statement.executeUpdate(
                        "INSERT INTO players values('" + s + "'," + playerRecordMap.get(s).getSQLReadyValues() + ")"
                );
            }
            System.out.print("Instered");
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
