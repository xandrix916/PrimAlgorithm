package org.axerold;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SuppressWarnings("unused")
public class Vertex {

    private static final int INFINITY = Integer.MAX_VALUE;
    private final int number;

    private int distance;

    private Vertex ancestor;

    private final List<Edge> adjacentEdges = new ArrayList<>();

    public Vertex(int number) {
        this.number = number;
        this.distance = INFINITY;
        this.ancestor = null;
    }

    public void addEdge(Edge edge) {
        adjacentEdges.add(edge);
    }

    public List<Edge> getAdjacentEdges() {
        return adjacentEdges;
    }

    public Edge getEdgeByAdjacentVertex(Vertex vertex) {
        for (var e: adjacentEdges) {
            if (e.getAnotherVertex(vertex) == this) {
                return e;
            }
        }
        log.warn("No such edge found");
        return null;
    }

    public int getNumber() {
        return number;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Vertex getAncestor() {
        return ancestor;
    }

    public void setAncestor(Vertex ancestor) {
        this.ancestor = ancestor;
    }
}
