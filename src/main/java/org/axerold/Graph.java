package org.axerold;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j; // библиотека используется для логгирования ошибок,
// предупреждений и взаимодействия с пользователем

/**
 * Граф создаётся из матрицы весов.
 * В самом графе хранятся списки вершин и рёбер.
 * Выход можно получить как в виде матрицы весов,
 * так и в виде статистики.
 */
@Slf4j
@SuppressWarnings("unused")
public class Graph {
    private final int[][] initMatrix; // изначальная матрица весов, которой задаётся граф
    private int[][] mstMatrix; // mst - minimum spanning tree, матрица, полученная в результате работы алгоритма

    private int mstWeight; // вес минимального остовного дерева
    private boolean isValid = true; // маркер корректности графа (true - неориентированный и без петель)
    private boolean isConnected = true; // маркер связности графа. Если не связный, то на выходе получается лес
    // минимальных остовных деревьев

    private final List<Edge> edges = new ArrayList<>(); // список рёбер
    private final List<Vertex> vertices = new ArrayList<>(); // список вершин

    /**
     * Для проверки на ориентированность
     * достаточно посмотреть, является ли матрица симметричной
     */
    private boolean isOriented() {
        for (int i = 0; i < initMatrix.length; i++) {
            for (int j = 0; j < i; j++) {
                if (initMatrix[i][j] != initMatrix[j][i]) {
                    log.error("Oriented graph is unacceptable");
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * А для проверки на содержание петель
     * достаточно проверить главную диагональ матрицы
     * на наличие ненулевых элементов
     */
    private boolean containsLoops() {
        for (int i = 0; i < initMatrix.length; i++) {
            if (initMatrix[i][i] != 0) {
                log.error("Loops are unacceptable");
                return true;
            }
        }
        return false;
    }

    /**
     * Рекурсивный обход в глубину,
     * реализован через списки
     * @param visited посещённые вершины. В течение обхода обновляется
     * @param notVisited список изначально непосещённых вершин. Не обновляется.
     * @param index индекс вершины, введён для рекурсивного обхода
     */
    private void dfs(List<Vertex> visited, List<Vertex> notVisited, int index) {
        visited.add(notVisited.get(index));
        for (var v: notVisited.get(index).getAdjacentVertices()) {
            if (!visited.contains(v)) {
                dfs(visited, notVisited, v.getNumber() - 1); // вершины нумеруются натуральными числами, поэтому
                // для получения вершины по номеру из списка, нужно отнять единицу
            }
        }
    }

    /**
     * Проверяем граф на связность.
     * Для несвязных графов алгоритм
     * не сильно меняется, но пользователь всё
     * равно будет предупреждён о том, что
     * ввёл матрицу несвязного графа.
     */
    private boolean isConnected() {
        List<Vertex> visited = new ArrayList<>();
        List<Vertex> notVisited = new ArrayList<>(vertices);
        dfs(visited, notVisited, 0); // так как алгоритм применим только к неориентированным графам, то
        // достаточно одного обхода из любой вершины, что сделать вывод о связности.
        isConnected = visited.size() == notVisited.size();
        return isConnected;
    }

    /**
     * Эта функция вызывается первой в конструкторе, в ней мы просто объявим набор вершин, без указания рёбер.
     * @throws IllegalArgumentException в случае, если матрица не квадратная, или не симметрическая,
     * или на главной диагонали есть ненулевые элементы
     */
    private void initVertices() throws IllegalArgumentException {
        if (initMatrix.length != initMatrix[0].length || isOriented() || containsLoops()) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < initMatrix.length; i++) {
            vertices.add(new Vertex(i + 1));
        }
    }

    /**
     * В случае, если инициализация вершин прошла успешно,
     * то по матрице объявляем рёбра, а также передаём ссылки
     * на них в соответствующие вершины.
     */
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

    /**
     * Конструктор класса. Помимо вызова функций задания вершин и рёбер, в нём также обрабатываются
     * все исключения и отдельно обрабатывается случай несвязного графа.
     * @param initMatrix входная матрица весов. Задаётся пользователем в тесте или в файле input.txt
     */
    public Graph(int[][] initMatrix) {
        this.initMatrix = initMatrix.clone();
        try {
            initVertices();
        } catch (IllegalArgumentException exception) {
            isValid = false;
            log.error("Incorrect size of weighted matrix or incorrect type of graph");
        }
        if (isValid) {
            initEdges();
            if (!isConnected()) {
                log.warn("Graph is not connected. In the end of algorithm you'll retrieve a forest"
                        + " of minimum spanning trees");
            }
        }
    }

    /**
     * Функция вызывается по факту окончания работы алгоритма (можно вызвать и без него, но она просто вернёт
     * нулевую матрицу). Составляет матрицу весов полученного дерева (или леса) по рёбрам, отмеченным алгоритмом.
     * Сразу подсчитывает и вес полученного дерева (леса).
     * @return matrix - сразу возвращает новую матрицу весов. Используется только в тестах.
     */
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

    /**
     * Вспомогательная функция для представления матрицы весов в строковом виде.
     * @param stringBuilder - уже рабочий экземпляр класса StringBuilder с некоторой информацией о графе.
     */
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

    /**
     * Переопределение функции toString из класса Object для удобного представления
     * результата работы алгоритма Прима в строковом виде. Для удобства конкантенации строк используется
     * StringBuilder. Строковой вид используется в файловом вводе/выводе.
     */
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
            stringBuilder.append("Current graph doesn't satisfy Prim algorithm's requirements, "
                    + "therefore it cannot find MST");
        }
        return stringBuilder.toString();
    }

    /**
     * Все последующие функции - обычные геттеры.
     */

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
