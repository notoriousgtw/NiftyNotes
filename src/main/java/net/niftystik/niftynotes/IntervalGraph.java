package net.niftystik.niftynotes;

import java.util.*;

public abstract class IntervalGraph implements Graph<Interval, > {
    protected List<NodeT> nodes;

    IntervalGraph() {
        nodes = new ArrayList<>();
    }

    public abstract void add(IntervalT interval);

    public NodeT get(int i) {
        return nodes.get(i);
    }
}
