package net.niftystik.niftynotes;

import java.util.ArrayList;
import java.util.List;

public class ImmutableTwelveTETIntervalGraph extends TwelveTETIntervalGraph {
    @Override
    public void add(TwelveTET.Interval interval) {
        throw new UnsupportedOperationException("Graph is immutable");
    }

    ImmutableTwelveTETIntervalGraph(TwelveTETIntervalGraph graph) {
        List<IntervalNode> nodes = new ArrayList<>();
        for (IntervalNode node : graph.nodes) {
            IntervalNode copy = new IntervalNode(node.value);
            copy.diatonics = node.diatonics;
            copy.quantatics = node.quantatics;
            copy.chromatics = node.chromatics;
            copy.enharmonics = node.enharmonics;
            copy.neighbors = List.of(node.neighbors.toArray(new IntervalEdge[]{}));
            nodes.add(copy);
        }
        this.nodes = List.of(nodes.toArray(new IntervalNode[]{}));
    }
}
