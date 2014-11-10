package net.karolek.revoguild.tablist.update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.base.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class RankList {

    @Getter
    private static List<Data<User>> topPlayers = Collections.synchronizedList(new ArrayList<Data<User>>());
    @Getter
    private static List<Data<Guild>> topGuilds = Collections.synchronizedList(new ArrayList<Data<Guild>>());

    public static void setTopPlayers(Collection<Data<User>> data) {
        topPlayers.clear();
        topPlayers.addAll(data);
    }

    public static void setTopGuilds(Collection<Data<Guild>> data) {
        topGuilds.clear();
        topGuilds.addAll(data);
    }

    @Getter
    @AllArgsConstructor
    public static class Data<T> {

        private T key;
        private int points;

    }
}
