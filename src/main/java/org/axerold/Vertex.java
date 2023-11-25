package org.axerold;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j; // библиотека используется для логгирования ошибок,
// предупреждений и взаимодействия с пользователем

/**
 * Реализация вершины графа. Помимо номера, содержит ссылки на смежные рёбра и вершины,
 * расстояние до минимального остовного дерева и предка.
 */
@Slf4j
@SuppressWarnings("unused")
public class Vertex {

    private static final int INFINITY = Integer.MAX_VALUE; // константа, выступающая в роли бесконечности
    private final int number;

    private int distance; // расстояние до MST

    private Vertex ancestor; // предок

    private final List<Edge> adjacentEdges = new ArrayList<>(); // список смежных рёбер

    private final Map<Vertex, Edge> vertexEdgeMap = new HashMap<>(); // хэш-таблица вершина-ребро смежных вершин и
    // соответствующих им рёбер. Сделана для оптимизации работы, без неё приходится по циклу перебирать все смежные
    // рёбра.

    /**
     * Кроме номера, в конструкторе вершины задаются расстояние до MST, как бесконечное
     * и null в качестве предка, как это было бы перед началом алгоритма Прима.
     * @param number задаётся в конструкторе графа.
     */
    public Vertex(int number) {
        this.number = number;
        this.distance = INFINITY;
        this.ancestor = null;
    }

    /**
     * Добавляет ребро и пару вершина/ребро в список рёбер и хэш-таблицу соответственно.
     */

    public void addEdge(Edge edge) {
        adjacentEdges.add(edge);
        vertexEdgeMap.put(edge.getAnotherVertex(this), edge);
    }

    public List<Edge> getAdjacentEdges() {
        return adjacentEdges;
    }

    public Edge getEdgeByAdjacentVertex(Vertex vertex) {
        return vertexEdgeMap.get(vertex);
    }

    public Set<Vertex> getAdjacentVertices() {
        return vertexEdgeMap.keySet();
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
