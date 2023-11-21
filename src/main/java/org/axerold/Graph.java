package org.axerold;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SuppressWarnings("unused")
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
                    edge = new Edge(vertices.get(i), vertices.get(j), initMatrix[i][j]);
                    edges.add(edge);
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

    public int[][] retrieveModifiedWeightMatrix() {
        int[][] matrix = new int[vertices.size()][vertices.size()];
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = 0; j < i; j++) {
                if (initMatrix[i][j] != 0) {
                    matrix[i][j] = (vertices.get(i).getEdgeByAdjacentVertex(vertices.get(j)).doesBelongToTree()
                            ? initMatrix[i][j]
                            : 0);
                    matrix[j][i] = matrix[i][j];
                }
            }
        }
        return matrix;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }


    public boolean isValid() {
        return isValid;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public int[][] getInitMatrix() {
        return initMatrix;
    }
}
