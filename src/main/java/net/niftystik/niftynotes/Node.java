package net.niftystik.niftynotes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public abstract class Node<T, NODE extends Node, EDGE extends Edge> {
    T value;
    List<EDGE> neighbors;

    Node(T value) {
        this.value = value;
        neighbors = new ArrayList<>();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
