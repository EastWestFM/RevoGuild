package net.karolek.revoguild.store.values;

import net.karolek.revoguild.base.User;
import net.karolek.revoguild.store.Entry;

public class UserValue {

    private User value;
    private String name;
    private Entry entry;
    private boolean update;

    public UserValue(String name, Entry entry, User v, boolean update) {
        this.value = v;
        this.name = name;
        this.entry = entry;
        this.update = update;
    }

    public UserValue(String name, Entry entry, User v) {
        this(name, entry, v, true);
    }

    public UserValue(Entry entry, User v) {
        this("", entry, v, false);
    }

    public void set(User v) {
        value = v;
        update();
    }

    public User get() {
        return value;
    }

    private void update() {
        if (!update)
            return;
        entry.update(new Valueable() {

            @Override
            public String getFieldName() {
                return name;
            }

            @Override
            public String getStringValue() {
                return "" + value.toString();
            }
        });
    }

}
