package org.axerold;

import java.util.*;


public class Prim {
    private final Graph graph;

    private final CustomPriorityQueue priorityQueue = new CustomPriorityQueue();

    private final Set<Edge> minimumSpanningTreeEdges = new HashSet<>();

    public Prim(Graph graph) {
        this.graph = graph;
    }

    public void run() {
        int vertexIndex = (int) (Math.random() * graph.getVertices().size());
        run(vertexIndex);
    }

    public void run(int startVertex) {
        var vertices = graph.getVertices();

        vertices.get(startVertex).setDistance(0);
        priorityQueue.addAll(vertices);
        var vertex = priorityQueue.remove();

        while (!priorityQueue.isEmpty()) {
            for (var e : vertex.getAdjacentEdges()) {
                Vertex adjacentVertex = e.getAnotherVertex(vertex);

                if (priorityQueue.contains(adjacentVertex) && e.getWeight() < adjacentVertex.getDistance()) {
                    adjacentVertex.setDistance(e.getWeight());
                    adjacentVertex.setAncestor(vertex);
                }
            }
            priorityQueue.updateOrder();

            vertex = priorityQueue.remove();
            minimumSpanningTreeEdges.add(vertex.getAncestor().getEdgeByAdjacentVertex(vertex));
        }
        makeResultTree();
    }

    public void makeResultTree() {
        for (var e: minimumSpanningTreeEdges) {
            e.setBelongsToTree(true);
        }
    }



}
