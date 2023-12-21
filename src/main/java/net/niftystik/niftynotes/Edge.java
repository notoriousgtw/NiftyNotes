package net.niftystik.niftynotes;

public abstract class Edge<T, S extends Node> {
    T data;
    S start;
    S end;

    Edge(S start, S end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return end.toString();
    }
}
