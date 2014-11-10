package net.karolek.revoguild.store.values;

import net.karolek.revoguild.store.Entry;

public class IntegerValue {

    private int value;
    private String name;
    private Entry entry;
    private boolean update;

    public IntegerValue(String name, Entry entry, int v, boolean update) {
        this.value = v;
        this.name = name;
        this.entry = entry;
        this.update = update;
    }

    public IntegerValue(String name, Entry entry, int v) {
        this(name, entry, v, true);
    }

    public IntegerValue(Entry entry, int v) {
        this("", entry, v, false);
    }

    public void add(int v) {
        value += v;
        update();
    }

    public void set(int v) {
        value = v;
        update();
    }

    public int get() {
        return value;
    }

    public void remove(int v) {
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
