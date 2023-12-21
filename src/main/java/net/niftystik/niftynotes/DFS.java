package net.niftystik.niftynotes;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public abstract class DFS<T extends Node, S extends Edge> {
    protected HashMap<T, Boolean> marked;
    protected Stack<T> nodeStack;
    VisitFunction<T> visitFunction;
    MarkFunction<T> markFunction;
    NeighborsFunction<T, S> neighborsFunction;

    DFS() {
        marked = new HashMap<>();
        nodeStack = new Stack<>();
        visitFunction = this::visit;
        markFunction = this::mark;
        neighborsFunction = this::neighbors;
    }

    public void dfs(T node) {
        nodeStack.add(node);
        while (nodeStack.size() > 0) {
            node = nodeStack.pop();
            if (!marked.get(node)) {
                visitFunction.execute(node);
                markFunction.execute(node);
                for (S edge : neighborsFunction.execute(node)) {
                    if (!(Boolean) marked.get(edge.end)) {
                        nodeStack.add((T) edge.end);
                    }
                }
            }
        }
    }

    protected abstract void visit(T node);
    protected abstract void mark(T node);
    protected abstract List<S> neighbors(T node);

    @FunctionalInterface
    public interface VisitFunction<T extends Node> {
         void execute(T node);
    }

    @FunctionalInterface
    public interface MarkFunction<T extends Node> {
        void execute(T node);
    }

    @FunctionalInterface
    public interface NeighborsFunction<T extends Node, S extends Edge> {
        List<S> execute(T node);
    }
}


