package org.axerold;

public class Main {
    public static int[][] modifyWeightMatrix(int[][] initMatrix, int startVertex) {
        Graph graph = new Graph(initMatrix);
        Prim primAlgo = new Prim(graph);
        if (startVertex < 0 || startVertex > initMatrix.length) {
            primAlgo.run();
        } else {
            primAlgo.run(startVertex);
        }
        return graph.retrieveModifiedWeightMatrix();
    }
    public static void main(String[] args) {

    }
}