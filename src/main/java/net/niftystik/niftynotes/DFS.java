package net.niftystik.niftynotes;

import java.util.HashMap;
import java.util.List;

public abstract class DFS<NODE extends Node, EDGE extends Edge> {
    protected abstract void visit(NODE node);
    protected abstract void mark(NODE node);
    protected abstract List<EDGE> neighbors(NODE node);
}
