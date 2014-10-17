package net.karolek.revoguild.store;

public interface Entry {

	public void insert();
	
	public void update(boolean now);
	
	public void delete();
	
}
