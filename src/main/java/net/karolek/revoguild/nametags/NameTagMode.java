package net.karolek.revoguild.nametags;

import lombok.Getter;

public enum NameTagMode {
    TAG_API("tagapi"), SCOREBOARD("scoreboard");

    @Getter
    private String name;

    private NameTagMode(String name) {
        this.name = name;
    }

    public static NameTagMode getByName(String name) {
        for (NameTagMode sm : values())
            if (sm.getName().equalsIgnoreCase(name))
                return sm;
        return null;
    }
}
