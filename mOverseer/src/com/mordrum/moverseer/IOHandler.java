package com.mordrum.moverseer;

import java.sql.*;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 5/31/13
 * Time: 12:42 AM
 * <p/>
 * IO handling class that is in charge of managing mOverseer's SQLite database. Everything is cached in the mOverseer
 * class and is only saved on shutdown, or when the appropriate command is executed.
 */
public class IOHandler {

	Main plugin;
	Connection connection = null;

	public IOHandler(Main instance) {
		plugin = instance;
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
							"homeZ INTEGER, homeWorld INTEGER, ");


			statement.executeUpdate("drop table if exists person");
			statement.executeUpdate("create table person (id integer, name string)");
			statement.executeUpdate("insert into person values(1, 'leo')");
			statement.executeUpdate("insert into person values(2, 'yui')");
			ResultSet rs = statement.executeQuery("select * from person");
			while (rs.next()) {
				// read the result set
				System.out.println("name = " + rs.getString("name"));
				System.out.println("id = " + rs.getInt("id"));
			}
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

	}
}
