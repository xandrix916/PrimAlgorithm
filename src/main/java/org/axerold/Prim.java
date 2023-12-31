package org.axerold;

import java.util.HashSet;
import java.util.Set;
import lombok.extern.slf4j.Slf4j; // библиотека используется для логгирования ошибок,
// предупреждений и взаимодействия с пользователем

/**
 * Алгоритм Прима используется для построения минимального остовного дерева (Minimum Spanning Tree)
 * по взвешенному графу.
 * MST - дерево, которое соединяет все вершины, так, чтобы суммарный вес всех рёбер в нём был минимально возможным.
 * На вход алгоритму может быть подан только неориентированный и связный граф (по определению).
 * В случае несвязного графа алгоритм всё равно может нормально работать, но по итогу будет построено не
 * минимальное остовное дерево, а лес из минимальных остовных деревьев для всех компонент связности.
 */

@Slf4j
public class Prim {
    private final Graph graph;

    private final CustomPriorityQueue priorityQueue = new CustomPriorityQueue(); // вместо основного PriorityQueue
    // использую простую обёртку ArrayList, чтобы на каждом шагу была возможность отсортировать изменившиеся значения.

    private final Set<Edge> minimumSpanningTreeEdges = new HashSet<>(); // множество рёбер MST

    public Prim(Graph graph) {
        this.graph = graph;
    }

    /**
     * Без заданной стартовой вершины граф сам выберет случайным образом вершину графа.
     * Важно учитывать, что при наличии равных путей (включая равные рёбра) может получиться
     * разное дерево при выборе разных вершин (но его вес всегда будет минимален).
     */
    public void run() {
        int vertexIndex = (int) (Math.random() * graph.getVertices().size());
        run(vertexIndex); // после выбора вершины вызываем основную функцию
    }

    /**
     * Собственно, в этой функции и реализован алгоритм Прима. <br>
     * Краткая суть алгоритма:
     * <p>
     * 0. Каждой вершине присваиваем +inf, как расстояние до MST, и null в качестве предшествующей вершины из MST. <br>
     * Это мы сделали ещё при инициализации вершин <br>
     * Заполняем очередь всеми вершинами из графа. <br>
     * Выбранная стартовая вершина - корень искомого дерева. Присваиваем ей 0, как расстояние до MST. <br>
     * <p>
     * 1. Сортируем очередь по расстоянию до MST и выбираем вершину с наименьшим расстоянием до MST.
     * <p>
     * 2. От неё перебираем все смежные вершины, задавая каждой расстояние до MST (попросту вес ребра от
     * последней вершины, вошедшей в MST до смежной с ней, ещё не входящей).
     * <p>
     * 2.1 Если расстояние до смежной вершины меньше веса ребра от выбранной вершины до неё и смежная вершина ещё не
     * рассмотрена, то задаём расстояние, как вес пресловутого ребра, в качестве предка устанавливаем выбранную вершину.
     * <p>
     * 3. По результатам итерации на шаге 2 выбираем вершину, как в шаге 1 и добавляем ребро от неё
     * до её предка в множество рёбер MST.
     * <p>
     * 4. Повторяем шаги 2-3, пока есть нерасмотренные вершины.<br>
     * Если нерасмотренных вершин больше нет, то заканчиваем работу.
     * <p>
     * @param startVertex стартовая вершина
     */
    public void run(int startVertex) {
        var vertices = graph.getVertices();

        vertices.get(startVertex).setDistance(0); // задаём для стартовой вершины расстояние до MST 0, поскольку
        // она будет корнем для него.
        priorityQueue.addAll(vertices); // добавляем в очередь все вершины. В силу реализации очередь сразу отсортирует
        // все вершины по расстоянию до MST, и стартовая вершина окажется первой.
        var vertex = priorityQueue.remove(); // вытягиваем из очереди непосредственно стартовую вершину.

        // Выполняем итерации цикла, пока не будут рассмотрены все вершины
        while (!priorityQueue.isEmpty()) {
            for (var e : vertex.getAdjacentEdges()) { // пробегаемся по инцидентным вершине рёбрам
                Vertex adjacentVertex = e.getAnotherVertex(vertex); // для выбранного ребра получаем вторую вершину

                // если эта вершина всё ещё в очереди и вес ребра меньше, чем расстояние вершины до MST (по умолчанию
                // - бесконечность)
                if (priorityQueue.contains(adjacentVertex) && e.getWeight() < adjacentVertex.getDistance()) {
                    adjacentVertex.setDistance(e.getWeight()); // обновляем расстояние до MST
                    adjacentVertex.setAncestor(vertex); // задаём предка
                }
            }
            priorityQueue.updateOrder(); // после прохода по всем смежным вершинам мы обновили у них расстояния до MST
            // и логично будет отсортировать вершины, для того, чтобы выбрать ту, что будет ближе всего к MST.

            vertex = priorityQueue.remove(); // выбираем вершину, до которой идёт ребро с наименьшим весом
            try {
                minimumSpanningTreeEdges.add(vertex.getAncestor().getEdgeByAdjacentVertex(vertex)); // если мы
                // рассматриваем связный граф, то здесь мы просто добавляем пресловутое ребро с наименьшим весом.
            } catch (NullPointerException e) {
                // если функция выкидывает исключение нулевого элемента, это значит, что граф несвязен и вместо поиска
                // MST мы ищем лес из MST. Всё потому, что в выбранную вершину не ведёт никаких рёбер из рассмотренных
                // ранее вершин, а значит, мы не могли установить её предка.
                log.warn("Forest case, no edges appending in mst");
            }
        }
        makeResultTree(); // просто помечаем все рёбра из множества, как принадлежащие к MST
    }

    public void makeResultTree() {
        for (var e: minimumSpanningTreeEdges) {
            e.setBelongsToTree(true);
        }
    }
}
