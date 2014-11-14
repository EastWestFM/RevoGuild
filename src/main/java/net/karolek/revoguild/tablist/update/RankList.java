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
    private List<Data<User>> topPlayers = Collections.synchronizedList(new ArrayList<Data<User>>());
    @Getter
    private List<Data<Guild>> topGuilds = Collections.synchronizedList(new ArrayList<Data<Guild>>());

    public void setTopPlayers(Collection<Data<User>> data) {
        topPlayers.clear();
        topPlayers.addAll(data); for(Data<User> guildData: data)System.out.println("User: " + guildData.getKey().getName());
    }

    public void setTopGuilds(Collection<Data<Guild>> data) {
        topGuilds.clear();
        topGuilds.addAll(data);
        for(Data<Guild> guildData: data)System.out.println("Gildia: " + guildData.getKey().getTag());
    }

    @Getter
    @AllArgsConstructor
    public static class Data<T> {

        private T key;
        private int points;

    }
}
