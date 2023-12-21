package net.niftystik.niftynotes;

import net.niftystik.niftynotes.TwelveTET.Interval;

import java.util.*;
import java.util.function.Function;

import static java.lang.Math.abs;

public class TwelveTETIntervalGraph extends IntervalGraph<TwelveTET.Interval, TwelveTETIntervalGraph.Node> {
    public void populateIntervals() {
        Populator populator = new Populator();
        populator.populate();
    }

    TwelveTETIntervalGraph() {
    }

    public void add(Interval interval) {
       nodes.add(new Node(interval));
    }

    public class Populator extends DFS<Node, Edge> {
        protected int quantity;
        protected boolean finished;

        Populator() {
            super();
            quantity = 1;
        }


        public void populate() {
            Node node = new Node(new Interval(Quality.PERFECT, Quantity.FIRST, 0));
            node.value.abbr = "P1";
            nodes.add(node);
            marked.put(node, false);
            dfs(node);

            node = nodes.get(0);
            marked.replaceAll((key, oldValue) -> false);
            visitFunction = this::secondVisit;
            dfs(node);
        }

        @Override
        protected void visit(Node node) {
            quantity = node.value.quantity.id;

            if ((node.value.quality == Quality.PERFECT || node.value.quality == Quality.MAJOR) && quantity != 15) {
                Node diatonic = getNextDiatonic(node);
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

                    Node augNode = new Node(aug);

                    node.addQuantatic(augNode, 1);
                } else if (quantity == 4 || quantity == 5 || quantity == 8 || quantity == 11 || quantity == 12) {
                    Interval dim = new Interval(Quality.DIMINISHED, node.value.quantity, node.value.semitones - 1);
                    dim.abbr = "d" + quantity;
                    Interval aug = new Interval(Quality.AUGMENTED, node.value.quantity, node.value.semitones + 1);
                    aug.abbr = "A" + quantity;

                    Node dimNode = new Node(dim);
                    Node augNode = new Node(aug);

                    dimNode.addQuantatic(node, 1);
                    node.addQuantatic(augNode, 1);

                } else if (quantity == 15) {
                    Interval dim = new Interval(Quality.DIMINISHED, node.value.quantity, node.value.semitones - 1);
                    dim.abbr = "d" + quantity;

                    Node dimNode = new Node(dim);

                    node.addQuantatic(dimNode, -1);
                }

            } else if (node.value.quality == Quality.MAJOR) {
                Interval dim = new Interval(Quality.DIMINISHED, node.value.quantity, node.value.semitones - 2);
                dim.abbr = "d" + quantity;
                Interval min = new Interval(Quality.MINOR, node.value.quantity, node.value.semitones - 1);
                min.abbr = "m" + quantity;
                Interval aug = new Interval(Quality.AUGMENTED, node.value.quantity, node.value.semitones + 1);
                aug.abbr = "A" + quantity;

                Node dimNode = new Node(dim);
                Node minNode = new Node(min);
                Node augNode = new Node(aug);

                dimNode.addQuantatic(minNode, 1);
                minNode.addQuantatic(node, 1);
                node.addQuantatic(augNode, 1);

            }

            for (Edge e : node.neighbors) {
                if (!marked.containsKey(e.end) && e.type == Edge.Type.QUANTATIC) {
                    marked.put((Node) e.end, false);
                    nodes.add(nodes.indexOf(node) + (e.data > 0 ? e.data : 0) ,(Node) e.end);
                }
            }
        }

        protected void secondVisit(Node node) {
            for (Node n : nodes) {
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

            for (Node n : nodes) {
                if (!node.equals(n) && !n.isEnharmonicNeighbor(node) &&
                        node.enharmonics == 0 && node.value.semitones == n.value.semitones)
                    node.addEnharmonic(n);
            }
            node.sortNeighbors();
        }

        private Node getNextDiatonic(Node node) {
            Node neighbor;
            if (node.value.quantity.degree == 1 || node.value.quantity.degree == 2 || node.value.quantity.degree == 5 || node.value.quantity.degree == 6)
                neighbor = new Node(new Interval(Quality.MAJOR, node.value.quantity.next(), node.value.semitones + 2));
            else if (node.value.quantity.degree == 3 || node.value.quantity.degree == 7)
                neighbor = new Node(new Interval(Quality.PERFECT, node.value.quantity.next(), node.value.semitones + 1));
            else
                neighbor = new Node(new Interval(Quality.PERFECT, node.value.quantity.next(), node.value.semitones + 2));
            return neighbor;
        }

        @Override
        protected void mark(Node node) {
            marked.replace(node, true);
        }

        @Override
        protected List<Edge> neighbors(Node node) {
            return node.neighbors;
        }
    }

    public class Node extends net.niftystik.niftynotes.Node<Interval, Edge> {
        protected int diatonics;
        protected int quantatics;
        protected int chromatics;
        protected int enharmonics;

        Node(Interval interval) {
            super(interval);
            diatonics = 0;
            quantatics = 0;
            chromatics = 0;
            enharmonics = 0;
        }

        public void addDiatonic(Node diatonic, int distance) {
            this.neighbors.add(new Edge(this, diatonic, distance, Edge.Type.DIATONIC));
            diatonic.neighbors.add(new Edge(diatonic, this, -distance, Edge.Type.DIATONIC));
            diatonics += 1;
            diatonic.diatonics += 1;
        }

        public void addQuantatic(Node quantatic, int distance) {
            this.neighbors.add(new Edge(this, quantatic, distance, Edge.Type.QUANTATIC));
            quantatic.neighbors.add(new Edge(quantatic, this, -distance, Edge.Type.QUANTATIC));
            quantatics += 1;
            quantatic.quantatics += 1;
        }

        public void addChromatic(Node chromatic) {
            this.neighbors.add(new Edge(this, chromatic, 1, Edge.Type.CHROMATIC));
            chromatic.neighbors.add(new Edge(chromatic, this, -1, Edge.Type.CHROMATIC));
            chromatics += 1;
            chromatic.chromatics += 1;
        }

        public void addEnharmonic(Node enharmonic) {
            this.neighbors.add(new Edge(this, enharmonic, 0, Edge.Type.ENHARMONIC));
            enharmonic.neighbors.add(new Edge(enharmonic, this, 0, Edge.Type.ENHARMONIC));
            enharmonics += 1;
            enharmonic.enharmonics += 1;
        }

        public boolean isDiatonicNeighbor(Node node) {
            for (Edge edge : neighbors)
                if (edge.end == node && edge.type == Edge.Type.DIATONIC)
                    return true;
            return false;
        }
        public boolean isQuantaticNeighbor(Node node) {
            for (Edge edge : neighbors)
                if (edge.end == node && edge.type == Edge.Type.QUANTATIC)
                    return true;
            return false;
        }
        public boolean isChromaticNeighbor(Node node) {
            for (Edge edge : neighbors)
                if (edge.end == node && edge.type == Edge.Type.CHROMATIC)
                    return true;
            return false;
        }
        public boolean isEnharmonicNeighbor(Node node) {
            for (Edge edge : neighbors)
                if (edge.end == node && edge.type == Edge.Type.ENHARMONIC)
                    return true;
            return false;
        }

        public boolean isNeighbor(Node node) {
            for (Edge edge : neighbors)
                if (edge.end == node)
                    return true;
            return false;
        }

        public void sortNeighbors() {
            for (int i = 0; i < neighbors.size(); i++) {
                neighbors.sort(new SortNeighbors());
            }
        }

        protected class SortNeighbors implements Comparator<Edge> {

            @Override
            public int compare(Edge e1, Edge e2) {
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

    public class Edge extends net.niftystik.niftynotes.Edge<Integer, Node> {
        Type type;

        Edge(Node start, Node end, int distance, Type type) {
            super(start, end);
            this.data = distance;
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
