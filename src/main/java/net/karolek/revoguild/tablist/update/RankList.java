package net.karolek.revoguild.tablist.update;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class RankList {

	@Getter
	private static List<Data>	topPlayers	= Collections.synchronizedList(new ArrayList<Data>());
	@Getter
	private static List<Data>	topGuilds	= Collections.synchronizedList(new ArrayList<Data>());

	public static void setTopPlayers(Collection<Data> data) {
		topPlayers.clear();
		topPlayers.addAll(data);
	}

	public static void setTopGuilds(Collection<Data> data) {
		topGuilds.clear();
		topGuilds.addAll(data);
	}

	@Getter
	@AllArgsConstructor
	public static class Data {

		private String	name;
		private int		points;

	}
}
