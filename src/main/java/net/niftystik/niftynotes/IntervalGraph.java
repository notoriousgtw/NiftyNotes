package net.niftystik.niftynotes;

import java.util.*;

public abstract class IntervalGraph<T extends Interval, S extends Node> {
    protected List<S> nodes;

    IntervalGraph() {
        nodes = new ArrayList<>();
    }

    public abstract void add(T interval);

    public S get(int i) {
        return nodes.get(i);
    }
}
