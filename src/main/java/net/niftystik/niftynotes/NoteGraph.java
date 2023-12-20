package net.niftystik.niftynotes;

import java.util.*;

public abstract class NoteGraph<INTERVAL extends Interval, NOTE extends Note, NODE extends NoteGraph.NoteNode> {
    protected ArrayList<NODE> nodes;

    NoteGraph() {
        nodes = new ArrayList<>();
    }

    abstract void add(NOTE note);

    public NODE get(int i) {
        return nodes.get(i);
    }

    public abstract class NoteNode {
        NOTE note;
        Map<INTERVAL, NODE> intervals;
        List<NODE> enharmonics;

        NoteNode(NOTE note) {
            this.note = note;
            this.intervals = new LinkedHashMap<>();
        }

        public void addInterval(INTERVAL interval, NODE node) {
            this.intervals.put(interval, node);
        }
//        public abstract Map<INTERVAL, NOTE> getIntervals();
    }
}
