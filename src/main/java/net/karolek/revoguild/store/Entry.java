package net.karolek.revoguild.store;

import net.karolek.revoguild.store.values.Valueable;

public interface Entry {

	public void insert();
	
	public void update(boolean now);
	
	public void update(Valueable value);
	
	public void delete();
	
}
