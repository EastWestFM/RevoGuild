package net.karolek.revoguild.store.values;

import net.karolek.revoguild.store.Entry;

public class LongValue {

    private long value;
    private String name;
    private Entry entry;
    private boolean update;

    public LongValue(String name, Entry entry, long v, boolean update) {
        this.value = v;
        this.name = name;
        this.entry = entry;
        this.update = update;
    }

    public LongValue(String name, Entry entry, long v) {
        this(name, entry, v, true);
    }

    public LongValue(Entry entry, long v) {
        this("", entry, v, false);
    }

    public void add(long v) {
        value += v;
        update();
    }

    public void set(long v) {
        value = v;
        update();
    }

    public long get() {
        return value;
    }

    public void remove(long v) {
        value -= v;
        update();
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
                return "" + value;
            }
        });
    }

}
