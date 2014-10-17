package net.karolek.revoguild.manager.types;

import net.karolek.revoguild.manager.IManager;

public class GuildManager implements IManager {

	@Override
	public void enable() throws Exception {
		System.out.println("create gm");
	}

	@Override
	public void disable() {
		System.out.println("destroy gm");
	}

}
