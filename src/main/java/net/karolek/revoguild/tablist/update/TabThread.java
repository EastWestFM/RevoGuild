package net.karolek.revoguild.tablist.update;

import lombok.Getter;
import net.karolek.revoguild.GuildPlugin;
import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.base.User;
import net.karolek.revoguild.managers.GuildManager;
import net.karolek.revoguild.managers.UserManager;
import net.karolek.revoguild.tablist.update.RankList.Data;
import net.karolek.revoguild.utils.Logger;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class TabThread extends Thread {

    @Getter
    private static TabThread instance;

    @Getter
    private static Comparator<User> usersComparator = new Comparator<User>() {
        @Override
        public int compare(User o1, User o2) {
            return o2.getPoints().get() - o1.getPoints().get();
        }
    };

    @Getter
    private static Comparator<Guild> guildsComparator = new Comparator<Guild>() {
        @Override
        public int compare(Guild o1, Guild o2) {
            return o2.getPoints() - o1.getPoints();
        }
    };
    private static AtomicInteger ai = new AtomicInteger();
    private RankList rankList;
    private Executor executor;
    private Object locker;

    public TabThread() {

        instance = this;
        rankList = new RankList();
        locker = new Object();

        this.setName("TabThread");
        this.start();

    }

    public static void restart() {
        System.out.println("Metoda restart");
        if (instance == null) {
            Logger.warning("TabThread instance cannot be null!");
            return;
        }

        synchronized (instance.locker) {
            instance.locker.notify();
        }

    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println("Petla #" + ai.getAndIncrement());
                List<User> stats = new ArrayList<>();
                stats.addAll(UserManager.getUsers().values());
                Collections.sort(stats, getUsersComparator());
                List<Data<User>> toAddPlayers = new LinkedList<>();

                for (int i = 0; i < stats.size(); i++) {
                    User u = stats.get(i);
                    toAddPlayers.add(new Data<User>(u, u.getPoints().get()));
                }

                rankList.setTopPlayers(toAddPlayers);

                List<Guild> guilds = new ArrayList<>();
                guilds.addAll(GuildManager.getGuilds().values());
                Collections.sort(guilds, getGuildsComparator());
                List<Data<Guild>> toAddGuilds = new LinkedList<>();

                for (int i = 0; i < guilds.size(); i++) {
                    Guild g = guilds.get(i);
                    toAddGuilds.add(new Data<Guild>(g, g.getPoints()));
                }

                rankList.setTopGuilds(toAddGuilds);
                new TabHighUpdateTask().runTaskLaterAsynchronously(GuildPlugin.getPlugin(), 1L);
                synchronized (locker) {
                    locker.wait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
