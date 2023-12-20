package net.niftystik.niftynotes;

import net.niftystik.niftynotes.TwelveTET.Interval;

import java.util.*;

import static java.lang.Math.abs;

public class TwelveTETIntervalGraph extends IntervalGraph<TwelveTET.Interval, TwelveTETIntervalGraph.IntervalNode> {
    public void populateIntervals() {
        Populator populator = new Populator();
        populator.populate();
    }

    TwelveTETIntervalGraph() {
    }

    public void add(Interval interval) {
       nodes.add(new IntervalNode(interval));
    }

    public class Populator extends DFS<IntervalNode, IntervalEdge> {
        protected HashMap<IntervalNode, Boolean> marked;
        protected Stack<IntervalNode> nodeStack;
        protected int quantity;
        protected boolean finished;

        Populator() {
            quantity = 1;
            marked = new HashMap<>();

            nodeStack = new Stack<>();
        }

        public void populate() {
            IntervalNode node = new IntervalNode(new Interval(Quality.PERFECT, Quantity.FIRST, 0));
            node.value.abbr = "P1";
            nodes.add(node);
            marked.put(node, false);

            nodeStack.add(node);
            while (nodeStack.size() > 0) {
                node = nodeStack.pop();
                if (!marked.get(node)) {
                    visit(node);
                    mark(node);
                    for (IntervalEdge edge : neighbors(node)) {
                        if (!(Boolean) marked.get(edge.end)) {
                            nodeStack.add(edge.end);
                        }
                    }
                }
            }

            node = nodes.get(0);
            marked.replaceAll((key, oldValue) -> false);

            nodeStack.add(node);
            while (nodeStack.size() > 0) {
                node = nodeStack.pop();
                if (!marked.get(node)) {
                    secondVisit(node);
                    mark(node);
                    for (IntervalEdge edge : neighbors(node)) {
                        if (!(Boolean) marked.get(edge.end)) {
                            nodeStack.add(edge.end);
                        }
                    }
                }
            }
        }

        @Override
        protected void visit(IntervalNode node) {
            quantity = node.value.quantity.id;

            if ((node.value.quality == Quality.PERFECT || node.value.quality == Quality.MAJOR) && quantity != 15) {
                IntervalNode diatonic = getNextDiatonic(node);
                if (quantity == 3 || quantity == 7 || quantity == 10 || quantity == 14)
                    node.addDiatonic(diatonic, 1);
                else
                    node.addDiatonic(diatonic, 2);
                marked.put(diatonic, false);
                nodes.add(diatonic);
            }

            if (node.value.quality == Quality.PERFECT) {
                if (quantity == 1) {
                    Interval aug = new Interval(Quality.AUGMENTED, node.value.quantity, node.value.semitones + 1);
                    aug.abbr = "A" + quantity;

                    IntervalNode augNode = new IntervalNode(aug);

                    node.addQuantatic(augNode, 1);
                } else if (quantity == 4 || quantity == 5 || quantity == 8 || quantity == 11 || quantity == 12) {
                    Interval dim = new Interval(Quality.DIMINISHED, node.value.quantity, node.value.semitones - 1);
                    dim.abbr = "d" + quantity;
                    Interval aug = new Interval(Quality.AUGMENTED, node.value.quantity, node.value.semitones + 1);
                    aug.abbr = "A" + quantity;

                    IntervalNode dimNode = new IntervalNode(dim);
                    IntervalNode augNode = new IntervalNode(aug);

                    dimNode.addQuantatic(node, 1);
                    node.addQuantatic(augNode, 1);

                } else if (quantity == 15) {
                    Interval dim = new Interval(Quality.DIMINISHED, node.value.quantity, node.value.semitones - 1);
                    dim.abbr = "d" + quantity;

                    IntervalNode dimNode = new IntervalNode(dim);

                    node.addQuantatic(dimNode, -1);
                }

            } else if (node.value.quality == Quality.MAJOR) {
                Interval dim = new Interval(Quality.DIMINISHED, node.value.quantity, node.value.semitones - 2);
                dim.abbr = "d" + quantity;
                Interval min = new Interval(Quality.MINOR, node.value.quantity, node.value.semitones - 1);
                min.abbr = "m" + quantity;
                Interval aug = new Interval(Quality.AUGMENTED, node.value.quantity, node.value.semitones + 1);
                aug.abbr = "A" + quantity;

                IntervalNode dimNode = new IntervalNode(dim);
                IntervalNode minNode = new IntervalNode(min);
                IntervalNode augNode = new IntervalNode(aug);

                dimNode.addQuantatic(minNode, 1);
                minNode.addQuantatic(node, 1);
                node.addQuantatic(augNode, 1);

            }

            for (IntervalEdge e : node.neighbors) {
                if (!marked.containsKey(e.end) && e.type == IntervalEdge.Type.QUANTATIC) {
                    marked.put((IntervalNode) e.end, false);
                    nodes.add(nodes.indexOf(node) + (e.distance > 0 ? e.distance : 0) ,(IntervalNode) e.end);
                }
            }
        }

        protected void secondVisit(IntervalNode node) {
            for (IntervalNode n : nodes) {
                if (!node.equals(n) && !n.isChromaticNeighbor(node) &&
                        node.chromatics < 2 && (node.value.semitones == n.value.semitones + 1 || node.value.semitones == n.value.semitones - 1)) {
                    if ((node.value.quality != Quality.DIMINISHED && node.value.quality != Quality.AUGMENTED) &&
                            (n.value.quality != Quality.DIMINISHED && n.value.quality != Quality.AUGMENTED))
                        node.addChromatic(n);
                    if ((node.value.quality == Quality.DIMINISHED || node.value.quality == Quality.AUGMENTED) &&
                            (n.value.quality == Quality.DIMINISHED || n.value.quality == Quality.AUGMENTED))
                        node.addChromatic(n);
                }
            }

            for (IntervalNode n : nodes) {
                if (!node.equals(n) && !n.isEnharmonicNeighbor(node) &&
                        node.enharmonics == 0 && node.value.semitones == n.value.semitones)
                    node.addEnharmonic(n);
            }
            node.sortNeighbors();
        }

        private IntervalNode getNextDiatonic(IntervalNode node) {
            IntervalNode neighbor;
            if (node.value.quantity.degree == 1 || node.value.quantity.degree == 2 || node.value.quantity.degree == 5 || node.value.quantity.degree == 6)
                neighbor = new IntervalNode(new Interval(Quality.MAJOR, node.value.quantity.next(), node.value.semitones + 2));
            else if (node.value.quantity.degree == 3 || node.value.quantity.degree == 7)
                neighbor = new IntervalNode(new Interval(Quality.PERFECT, node.value.quantity.next(), node.value.semitones + 1));
            else
                neighbor = new IntervalNode(new Interval(Quality.PERFECT, node.value.quantity.next(), node.value.semitones + 2));
            return neighbor;
        }

        @Override
        protected void mark(IntervalNode node) {
            marked.replace(node, true);
        }

        @Override
        protected List<IntervalEdge> neighbors(IntervalNode node) {
            return node.neighbors;
        }
    }

    public class IntervalNode extends Node<Interval, IntervalNode, IntervalEdge> {
        protected int diatonics;
        protected int quantatics;
        protected int chromatics;
        protected int enharmonics;

        IntervalNode(Interval interval) {
            super(interval);
            diatonics = 0;
            quantatics = 0;
            chromatics = 0;
            enharmonics = 0;
        }

        public void addDiatonic(IntervalNode diatonic, int distance) {
            this.neighbors.add(new IntervalEdge(this, diatonic, distance, IntervalEdge.Type.DIATONIC));
            diatonic.neighbors.add(new IntervalEdge(diatonic, this, -distance, IntervalEdge.Type.DIATONIC));
            diatonics += 1;
            diatonic.diatonics += 1;
        }

        public void addQuantatic(IntervalNode quantatic, int distance) {
            this.neighbors.add(new IntervalEdge(this, quantatic, distance, IntervalEdge.Type.QUANTATIC));
            quantatic.neighbors.add(new IntervalEdge(quantatic, this, -distance, IntervalEdge.Type.QUANTATIC));
            quantatics += 1;
            quantatic.quantatics += 1;
        }

        public void addChromatic(IntervalNode chromatic) {
            this.neighbors.add(new IntervalEdge(this, chromatic, 1, IntervalEdge.Type.CHROMATIC));
            chromatic.neighbors.add(new IntervalEdge(chromatic, this, -1, IntervalEdge.Type.CHROMATIC));
            chromatics += 1;
            chromatic.chromatics += 1;
        }

        public void addEnharmonic(IntervalNode enharmonic) {
            this.neighbors.add(new IntervalEdge(this, enharmonic, 0, IntervalEdge.Type.ENHARMONIC));
            enharmonic.neighbors.add(new IntervalEdge(enharmonic, this, 0, IntervalEdge.Type.ENHARMONIC));
            enharmonics += 1;
            enharmonic.enharmonics += 1;
        }

        public boolean isDiatonicNeighbor(IntervalNode node) {
            for (IntervalEdge edge : neighbors)
                if (edge.end == node && edge.type == IntervalEdge.Type.DIATONIC)
                    return true;
            return false;
        }
        public boolean isQuantaticNeighbor(IntervalNode node) {
            for (IntervalEdge edge : neighbors)
                if (edge.end == node && edge.type == IntervalEdge.Type.QUANTATIC)
                    return true;
            return false;
        }
        public boolean isChromaticNeighbor(IntervalNode node) {
            for (IntervalEdge edge : neighbors)
                if (edge.end == node && edge.type == IntervalEdge.Type.CHROMATIC)
                    return true;
            return false;
        }
        public boolean isEnharmonicNeighbor(IntervalNode node) {
            for (IntervalEdge edge : neighbors)
                if (edge.end == node && edge.type == IntervalEdge.Type.ENHARMONIC)
                    return true;
            return false;
        }

        public boolean isNeighbor(IntervalNode node) {
            for (IntervalEdge edge : neighbors)
                if (edge.end == node)
                    return true;
            return false;
        }

        public void sortNeighbors() {
            for (int i = 0; i < neighbors.size(); i++) {
                neighbors.sort(new SortNeighbors());
            }
        }

        protected class SortNeighbors implements Comparator<IntervalEdge> {

            @Override
            public int compare(IntervalEdge e1, IntervalEdge e2) {
                if (e1.equals(e2))
                    return 0;
                if (e1.type.ordinal() < e2.type.ordinal())
                    return -1;
                else if (e1.type.ordinal() == e2.type.ordinal()) {
                    if (e1.end.value.semitones < e2.end.value.semitones)
                        return -1;
                    else if (e1.end.value.semitones > e2.end.value.semitones)
                        return 1;
                    else
                        return 0;
                }
                return 1;
            }
        }
    }

    public class IntervalEdge extends Edge<IntervalNode> {
        int distance;
        Type type;

        IntervalEdge(IntervalNode start, IntervalNode end, int distance, Type type) {
            super(start, end);
            this.distance = distance;
            this.type = type;
        }

        @Override
        public String toString() {
            return "Type: " + type + " Interval: " + end;
        }

        public enum Type {
            DIATONIC,
            QUANTATIC,
            CHROMATIC,
            ENHARMONIC
        }
    }
}
