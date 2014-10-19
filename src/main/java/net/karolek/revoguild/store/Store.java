package net.karolek.revoguild.store;

import java.sql.Connection;
import java.sql.ResultSet;

public interface Store {

	public Connection getConnection();
	
	public boolean connect();
	
	public void disconnect();
	
	public void reconnect();
	
	public boolean isConnected();
	
	public ResultSet query(String query);
	
	public void update(boolean now, String update);
		
	public StoreMode getStoreMode();
	
}
