package net.niftystik.niftynotes;

public interface Graph<E, NodeT extends Node, EdgeT extends Edge> {
    void add(E element);
    E get(int i);
}
