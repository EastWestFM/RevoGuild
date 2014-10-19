package net.karolek.revoguild.store.modes;

import java.sql.Connection;
import java.sql.ResultSet;

import net.karolek.revoguild.store.Store;
import net.karolek.revoguild.store.StoreMode;

public class StoreSQLITE implements Store {

	@Override
	public Connection getConnection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean connect() {
		return false;
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub

	}

	@Override
	public void reconnect() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isConnected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ResultSet query(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(boolean now, String update) {
		// TODO Auto-generated method stub

	}

	@Override
	public StoreMode getStoreMode() {
		// TODO Auto-generated method stub
		return null;
	}

}
