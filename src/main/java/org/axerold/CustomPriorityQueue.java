package org.axerold;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Обёртка для списка, имитирующая PriorityQueue.
 * Поскольку в JDK по умолчанию нет реализации очереди, которая
 * может обновлять порядок в очереди при изменении элементов, то я решил сделать это сам.
 */
public class CustomPriorityQueue {
    private final List<Vertex> queue; // здесь будут храниться все вершины

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
