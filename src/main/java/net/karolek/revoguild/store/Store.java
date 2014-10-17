package net.karolek.revoguild.store;

import java.sql.Connection;
import java.sql.ResultSet;

public interface Store extends Runnable {

	public Connection getConnection();
	
	public boolean connect();
	
	public void disconnect();
	
	public void reconnect();
	
	public boolean isConnected();
	
	public ResultSet query(String query);
	
	public void update(String update);
		
	public ResultSet updateNow(String update);
	
	public StoreMode getStoreMode();
	
}
