package org.axerold;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SuppressWarnings("unused")
public class Graph {
    private final int[][] initMatrix;
    private int[][] mstMatrix;

    private int mstWeight;
    private boolean isValid = true;
    private boolean isConnected = true;

    private final List<Edge> edges = new ArrayList<>();
    private final List<Vertex> vertices = new ArrayList<>();

    private boolean isOriented() {
        for (int i = 0; i < initMatrix.length; i++) {
            for (int j = 0; j < i; j++) {
                if (initMatrix[i][j] != initMatrix[j][i]) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean containsLoops() {
        for (int i = 0; i < initMatrix.length; i++) {
            if (initMatrix[i][i] != 0) {
                return true;
            }
        }
        return false;
    }

    private void DFS(List<Vertex> visited, List<Vertex> notVisited, int index) {
        visited.add(notVisited.get(index));
        for (var v: notVisited.get(index).getAdjacentVertices()) {
            if (!visited.contains(v)) {
                DFS(visited, notVisited, v.getNumber() - 1);
            }
        }
    }

    private boolean isConnected() {
        List<Vertex> visited = new ArrayList<>();
        List<Vertex> notVisited = new ArrayList<>(vertices);
        DFS(visited, notVisited, 0);
        isConnected = visited.size() == notVisited.size();
        return isConnected;
    }

    private void initVertices() throws IllegalArgumentException {
        if (initMatrix.length != initMatrix[0].length || isOriented() || containsLoops()) {
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
            if (!isConnected()) {
                log.warn("Graph is not connected. In the end of algorithm you'll retrieve a forest" +
                        " of minimum spanning trees");
            }
        }
    }

    public int[][] retrieveModifiedWeightMatrix() {
        if (!isValid) {
            return null;
        }
        int[][] matrix = new int[vertices.size()][vertices.size()];
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = 0; j < i; j++) {
                if (initMatrix[i][j] != 0) {
                    matrix[i][j] = (vertices.get(i).getEdgeByAdjacentVertex(vertices.get(j)).doesBelongToTree()
                            ? initMatrix[i][j]
                            : 0);
                    mstWeight += matrix[i][j];
                    matrix[j][i] = matrix[i][j];
                }
            }
        }
        mstMatrix = matrix;
        return matrix;
    }

    public int getMinimalSpanningTreeSummaryWeight() {
        return mstWeight;
    }

    private void matrixToString(StringBuilder stringBuilder) {
        for (var matrix : mstMatrix) {
            StringBuilder rowBuilder = new StringBuilder();
            for (int j = 0; j < mstMatrix.length; j++) {
                if (j == mstMatrix.length - 1) {
                    rowBuilder.append("%d\n".formatted(matrix[j]));
                } else {
                    rowBuilder.append("%d ".formatted(matrix[j]));
                }
            }
            stringBuilder.append(rowBuilder);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (isValid) {
            if (isConnected) {
                stringBuilder.append("Summary weight of minimum spanning tree - %d\nMinimum Spanning Tree:\n"
                        .formatted(mstWeight));
            } else {
                stringBuilder.append("Summary weight of forest - %d\nForest of MST:\n"
                        .formatted(mstWeight));
            }
            matrixToString(stringBuilder);
        } else {
            stringBuilder.append("Current graph doesn't satisfy Prim algorithm's requirements, " +
                    "therefore it cannot find MST");
        }
        return stringBuilder.toString();
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

    public int[][] getMstMatrix() {
        return mstMatrix;
    }
}
