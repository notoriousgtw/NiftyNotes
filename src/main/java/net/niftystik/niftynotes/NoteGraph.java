package net.niftystik.niftynotes;

import java.util.*;

public abstract class NoteGraph<T extends Note, S extends Node> {
    protected ArrayList<S> nodes;

    NoteGraph() {
        nodes = new ArrayList<>();
    }

    abstract void add(T note);

    public S get(int i) {
        return nodes.get(i);
    }

}
