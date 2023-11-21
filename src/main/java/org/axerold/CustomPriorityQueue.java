package org.axerold;

import java.util.*;

public class CustomPriorityQueue {
    private final List<Vertex> queue;

    public CustomPriorityQueue() {
        queue = new ArrayList<>();
    }

    public void addAll(List<Vertex> vertices) {
        queue.addAll(vertices);
        updateOrder();
    }

    public Vertex remove() {
        return queue.remove(0);
    }

    public boolean contains(Vertex vertex) {
        return queue.contains(vertex);
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public void updateOrder() {
        queue.sort(Comparator.comparing(Vertex::getDistance));
    }
}
