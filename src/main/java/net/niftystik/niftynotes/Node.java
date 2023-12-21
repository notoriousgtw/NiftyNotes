package net.niftystik.niftynotes;

import java.util.ArrayList;
import java.util.List;

public abstract class Node<T, S extends Edge> {
    T value;
    List<S> neighbors;

    Node(T value) {
        this.value = value;
        neighbors = new ArrayList<>();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
