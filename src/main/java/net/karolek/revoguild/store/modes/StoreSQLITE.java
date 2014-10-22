package net.karolek.revoguild.store.modes;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import lombok.Data;

import org.bukkit.scheduler.BukkitRunnable;

import net.karolek.revoguild.GuildPlugin;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.store.Store;
import net.karolek.revoguild.store.StoreMode;
import net.karolek.revoguild.utils.Logger;
import net.karolek.revoguild.utils.TimeUtil;

@Data
public class StoreSQLITE implements Store {

	private final String	name, prefix;
	private Connection	conn;
	private long			time;
	private Executor		executor;

	public StoreSQLITE(String name, String prefix) {

		this.name = name;
		this.prefix = prefix;

		this.executor = Executors.newSingleThreadExecutor();

		this.time = System.currentTimeMillis();

		new BukkitRunnable() {

			@Override
			public void run() {
				if (System.currentTimeMillis() - time > TimeUtil.SECOND.getTime(30))
					update(false, "UPDATE `{P}guilds` SET `tag`='KEEP' WHERE `id`='-1'");
			}
		}.runTaskTimer(GuildPlugin.getPlugin(), 20 * 30, 20 * 30);

	}
	
	@Override
	public boolean connect() {
		long start = System.currentTimeMillis();
		try {
			Class.forName("org.sqlite.JDBC");
			this.conn = DriverManager.getConnection("jdbc:sqlite:" + GuildPlugin.getPlugin().getDataFolder() + File.separator + Config.DATABASE_SQLITE_NAME);
			Logger.info("Connected to the SQLite server!", "Connection ping " + (System.currentTimeMillis() - start) + "ms!");
			return true;
		} catch (ClassNotFoundException e) {
			Logger.warning("JDBC driver not found!", "Error: " + e.getMessage());
			Logger.exception(e);
		} catch (SQLException e) {
			Logger.warning("Can not connect to a SQLite server!", "Error: " + e.getMessage());
			Logger.exception(e);
		}
		return false;	}

	public void update(boolean now, final String update) {
		time = System.currentTimeMillis();
		Runnable r = new Runnable() {
			@Override
			public void run() {
				try {
					conn.createStatement().executeUpdate(update.replace("{P}", prefix));
				} catch (SQLException e) {
					Logger.warning("An error occurred with given query '" + update.replace("{P}", prefix) + "'!", "Error: " + e.getMessage());
					Logger.exception(e);
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
				Logger.exception(e);
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
			Logger.exception(e);
		}
		return false;
	}

	@Override
	public ResultSet query(String query) {
		try {
			return conn.createStatement().executeQuery(query.replace("{P}", this.prefix));

		} catch (SQLException e) {
			Logger.warning("An error occurred with given query '" + query.replace("{P}", this.prefix) + "'!", "Error: " + e.getMessage());
			Logger.exception(e);
		}
		return null;
	}

	@Override
	public Connection getConnection() {
		return this.conn;
	}

	@Override
	public StoreMode getStoreMode() {
		return StoreMode.SQLITE;
	}

}
