package net.karolek.revoguild.tablist.update;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import net.karolek.revoguild.GuildPlugin;
import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.base.User;
import net.karolek.revoguild.managers.GuildManager;
import net.karolek.revoguild.managers.UserManager;
import net.karolek.revoguild.tablist.update.RankList.Data;
import net.karolek.revoguild.utils.Logger;
import lombok.Getter;

public class TabThread extends Thread {

	@Getter
	private static TabThread			instance;

	private Object							lock;

	@Getter
	private boolean						run;
	
	private AtomicInteger ai = new AtomicInteger();


	public TabThread() {

		instance = this;
		lock = new Object();
		run = true;

		this.start();
	}

	@Override
	public void run() {
		try {
			while (run) {

				System.out.println("Petla #" + ai.getAndIncrement());
				
				List<User> stats = new ArrayList<>();
				stats.addAll(UserManager.getUsers().values());
				Collections.sort(stats, getUsersComparator());
				List<Data<User>> toAddPlayers = new LinkedList<>();
				
				for (int i = 0; i < Math.min(20, stats.size()); i++) {
					User u = stats.get(i);
					toAddPlayers.add(new Data<User>(u, u.getPoints().get()));
				}

				RankList.setTopPlayers(toAddPlayers);

				List<Guild> guilds = new ArrayList<>();
				guilds.addAll(GuildManager.getGuilds().values());
				Collections.sort(guilds, getGuildsComparator());
				List<Data<Guild>> toAddGuilds = new LinkedList<>();
				
				for (int i = 0; i < Math.min(20, guilds.size()); i++) {
					Guild g = guilds.get(i);
					toAddGuilds.add(new Data<Guild>(g, g.getPoints()));
				}
				
				RankList.setTopGuilds(toAddGuilds);
				new TabHighUpdateTask().runTaskLaterAsynchronously(GuildPlugin.getPlugin(), 1L);
				
				synchronized (lock) {
					lock.wait();
				}
			}
		} catch (Exception e) {
			Logger.exception(e);
		}
	}
	
	public static void restart() {
		synchronized(instance.lock) {
			instance.lock.notify();
		}
	}
	
	@Getter
	private static Comparator<User>	usersComparator	= new Comparator<User>() {
																			@Override
																			public int compare(User o1, User o2) {
																				return o2.getPoints().get() - o1.getPoints().get();
																			}
																		};

	@Getter
	private static Comparator<Guild>	guildsComparator	= new Comparator<Guild>() {
																			@Override
																			public int compare(Guild o1, Guild o2) {
																				return o2.getPoints() - o1.getPoints();
																			}
																		};

}
