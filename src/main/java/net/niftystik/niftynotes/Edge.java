package net.niftystik.niftynotes;

public abstract class Edge<T, NodeT extends Node> {
    T data;
    NodeT start;
    NodeT end;

    Edge(NodeT start, NodeT end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return end.toString();
    }
}
