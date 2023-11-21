package org.axerold;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Graph {
    private final int[][] initMatrix;
    private boolean isValid = true;

    private final List<Edge> edges = new ArrayList<>();
    private final List<Vertex> vertices = new ArrayList<>();

    private void initVertices() throws IllegalArgumentException {
        if (initMatrix.length != initMatrix[0].length) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < initMatrix.length; i++) {
            vertices.add(new Vertex(i + 1));
        }
    }

    private void initEdges() {
        Edge edge;
        for (int i = 0; i < initMatrix.length; i++) {
            for (int j = 0; j < i; j++) {
                if (initMatrix[i][j] != 0) {
                    edge = new Edge(vertices.get(i), vertices.get(j));
                    vertices.get(i).addEdge(edge);
                    vertices.get(j).addEdge(edge);
                }
            }
        }

    }

    public Graph(int[][] initMatrix) {
        this.initMatrix = initMatrix.clone();
        try {
            initVertices();
        } catch (IllegalArgumentException exception) {
            isValid = false;
            log.error("Incorrect size of weighted matrix");
        }
        if (isValid) {
            initEdges();
        }
    }


    public boolean isValid() {
        return isValid;
    }
}
