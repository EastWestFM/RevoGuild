package net.karolek.revoguild.store.modes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import net.karolek.revoguild.GuildPlugin;
import net.karolek.revoguild.store.Store;
import net.karolek.revoguild.store.StoreMode;
import net.karolek.revoguild.utils.Logger;
import net.karolek.revoguild.utils.TimeUtil;

import org.bukkit.scheduler.BukkitRunnable;

public class StoreMySQL implements Store {

	private final String	host, user, pass, name, prefix;
	private final int		port;
	private Connection	conn;
	private long			time;
	private Executor		executor;

	public StoreMySQL(String host, int port, String user, String pass, String name, String prefix) {
		this.host = host;
		this.port = port;
		this.user = user;
		this.pass = pass;
		this.name = name;
		this.prefix = prefix;

		this.executor = Executors.newSingleThreadExecutor();

		this.time = System.currentTimeMillis();

		new BukkitRunnable() {

			@Override
			public void run() {
				if (System.currentTimeMillis() - time > TimeUtil.SECOND.getTime(30))
					update(false, "DO 1");
			}
		}.runTaskTimer(GuildPlugin.getPlugin(), 20 * 30, 20 * 30);

	}

	@Override
	public boolean connect() {
		long start = System.currentTimeMillis();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Logger.info("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.name, this.user, this.pass);
			this.conn = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.name, this.user, this.pass);
			Logger.info("Connected to the MySQL server!", "Connection ping " + (System.currentTimeMillis() - start) + "ms!");
			return true;
		} catch (ClassNotFoundException e) {
			Logger.warning("JDBC driver not found!", "Error: " + e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			Logger.warning("Can not connect to a MySQL server!", "Error: " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	public void update(boolean now, final String update) {
		time = System.currentTimeMillis();
		Runnable r = new Runnable() {
			@Override
			public void run() {
				try {
					conn.createStatement().executeUpdate(update.replace("{P}", prefix));
				} catch (SQLException e) {
					Logger.warning("An error occurred with given query '" + update.replace("{P}", prefix) + "'!", "Error: " + e.getMessage());
					e.printStackTrace();
				}
			}
		};

		if (now) {
			r.run();
		} else {
			executor.execute(r);
		}
	}

	@Override
	public void disconnect() {
		if (this.conn != null)
			try {
				this.conn.close();
			} catch (SQLException e) {
				Logger.warning("Can not close the connection to the MySQL server!", "Error: " + e.getMessage());
				e.printStackTrace();
			}
	}

	@Override
	public void reconnect() {
		this.connect();
	}

	@Override
	public boolean isConnected() {
		try {
			return !(this.conn.isClosed()) || (this.conn == null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public ResultSet query(String query) {
		try {
			return conn.createStatement().executeQuery(query.replace("{P}", this.prefix));

		} catch (SQLException e) {
			Logger.warning("An error occurred with given query '" + query.replace("{P}", this.prefix) + "'!", "Error: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Connection getConnection() {
		return this.conn;
	}

	@Override
	public StoreMode getStoreMode() {
		return StoreMode.MYSQL;
	}

}
