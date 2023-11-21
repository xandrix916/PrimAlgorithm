package org.axerold;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Edge {
    private final Vertex first;
    private final Vertex second;

    private final int weight;

    private boolean belongsToTree;

    public Edge(Vertex first, Vertex second, int weight) {
        this.first = first;
        this.second = second;
        this.belongsToTree = false;
        this.weight = weight;
    }

    public Vertex getAnotherVertex(Vertex vertex) {
        if (first != vertex && second != vertex) {
            return null;
        }

        return (first == vertex ? second : first);
    }

    public void setBelongsToTree(boolean belongsToTree) {
        this.belongsToTree = belongsToTree;
    }

    public int getWeight() {
        return weight;
    }

    public boolean doesBelongToTree() {
        return belongsToTree;
    }
}
