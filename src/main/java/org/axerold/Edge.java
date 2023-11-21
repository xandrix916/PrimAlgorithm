package org.axerold;

public class Edge {
    private final Vertex first;
    private final Vertex second;

    private boolean isActive;

    public Edge(Vertex first, Vertex second) {
        this.first = first;
        this.second = second;
        this.isActive = true;
    }

    public Vertex getFirst() {
        return first;
    }

    public Vertex getSecond() {
        return second;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
