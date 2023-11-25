package org.axerold;

import lombok.extern.slf4j.Slf4j; // библиотека используется для логгирования ошибок,
// предупреждений и взаимодействия с пользователем

/**
 * Реализация ребра.
 * Содержит ссылки на две вершины, которым оно инцидентно,
 * вес, заданный матрицей весов
 * а также значение boolean, определяющее вхождение в полученное остовное дерево.
 */
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

    /**
     * Сделана для удобства получения второй вершины при наличии ребра и вершины.
     * @param vertex вершина, которая может быть как инцидента ребру, так и нет
     * @return null, если вершина неинцидента ребру; иначе вторую вершину.
     */

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
