package org.axerold;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class Prim {
    private final Graph graph;

    private final PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>();

    private final Set<Edge> minimumSpanningTreeEdges = new HashSet<>();

    public Prim(Graph graph) {
        this.graph = graph;
    }

}
