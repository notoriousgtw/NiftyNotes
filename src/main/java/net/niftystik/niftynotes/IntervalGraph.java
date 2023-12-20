package net.niftystik.niftynotes;

import java.util.*;

public abstract class IntervalGraph<INTERVAL extends Interval, NODE extends Node> {
    protected List<NODE> nodes;

    IntervalGraph() {
        nodes = new ArrayList<>();
    }

    public abstract void add(INTERVAL interval);

    public NODE get(int i) {
        return nodes.get(i);
    }
}
