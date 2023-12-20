package net.niftystik.niftynotes;

public abstract class Edge<NODE extends Node> {
    NODE start;
    NODE end;

    Edge(NODE start, NODE end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return end.toString();
    }
}
