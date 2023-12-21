package net.niftystik.niftynotes;

import java.util.ArrayList;
import java.util.List;

public class ImmutableTwelveTETIntervalGraph extends TwelveTETIntervalGraph {
    @Override
    public void add(TwelveTET.Interval interval) {
        throw new UnsupportedOperationException("Graph is immutable");
    }

    ImmutableTwelveTETIntervalGraph(TwelveTETIntervalGraph graph) {
        List<Node> nodes = new ArrayList<>();
        for (Node node : graph.nodes) {
            Node copy = new Node(node.value);
            copy.diatonics = node.diatonics;
            copy.quantatics = node.quantatics;
            copy.chromatics = node.chromatics;
            copy.enharmonics = node.enharmonics;
            copy.neighbors = List.of(node.neighbors.toArray(new Edge[]{}));
            nodes.add(copy);
        }
        this.nodes = List.of(nodes.toArray(new Node[]{}));
    }

}
