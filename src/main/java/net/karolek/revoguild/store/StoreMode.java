package net.karolek.revoguild.store;

import lombok.Getter;

public enum StoreMode {

	MYSQL("mysql"), SQLITE("sqlite");

	@Getter
	private String	name;

	private StoreMode(String name) {
		this.name = name;
	}

	public static StoreMode getByName(String name) {
		for (StoreMode sm : values())
			if (sm.getName().equalsIgnoreCase(name))
				return sm;
		return null;
	}

}
