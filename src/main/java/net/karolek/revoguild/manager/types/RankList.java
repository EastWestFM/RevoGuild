package net.karolek.revoguild.manager.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
public class RankList {

    private List<Data> topPlayers = Collections.synchronizedList(new ArrayList<Data>());
    private List<Data> topGuilds = Collections.synchronizedList(new ArrayList<Data>());

    public void setTopPlayers(Collection<Data> data){
        topPlayers.clear();
        topPlayers.addAll(data);
    }

    public void setTopGuilds(Collection<Data> data){
        topGuilds.clear();
        topGuilds.addAll(data);
    }

    @Getter
    @AllArgsConstructor
    public static class Data {

        private String name;
        private int points;

    }
}
