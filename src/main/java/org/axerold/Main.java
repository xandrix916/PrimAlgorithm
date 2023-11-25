package org.axerold;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import lombok.extern.slf4j.Slf4j; // библиотека используется для логгирования ошибок,
// предупреждений и взаимодействия с пользователем

@Slf4j
public final class Main {
    private Main() {}

    /**
     * Функция используется в тестах JUnit5. В них проверяется корректность полученной
     * матрицы при определённых входных данных.
     * @param initMatrix стартовая матрица весов
     * @param startVertex стартовая вершина
     * @return матрицу весов для MST
     */
    public static int[][] modifyWeightMatrix(int[][] initMatrix, int startVertex) {
        Graph graph = new Graph(initMatrix);
        Prim primAlgo = new Prim(graph);
        try {
            if (startVertex < 0 || startVertex > initMatrix.length) {
                primAlgo.run(); // для выбора случайной вершины можно ввести любое некорректное значение, например, -1
            } else {
                primAlgo.run(startVertex);
            }
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
        return graph.retrieveModifiedWeightMatrix();
    }

    /**
     * Данная функция используется для файлового ввода/вывода.
     * @param graph граф построенный по исходной матрице весов
     * @param startVertex стартовая вершина
     * @return граф с рёбрами, помеченными, как входящие в MST
     */
    public static Graph modifyGraph(Graph graph, int startVertex) {
        Prim primAlgo = new Prim(graph);
        if (startVertex < 0 || startVertex > graph.getVertices().size()) {
            primAlgo.run();
        } else {
            primAlgo.run(startVertex);
        }
        return graph;
    }

    /**
     * Обрабатывает строку, полученную при считывании информации из файла input.txt
     * @param rawText - "сырой" текст, полученный при считывании из файла
     * @return граф, полученный с помощью
     * @see Main#modifyGraph(Graph, int)
     */

    private static Graph parseString(String rawText) {
        String[] strings = rawText.split("\r\n");
        var values = strings[0].split(" ");
        int n = Integer.parseInt(values[0]);
        int startVertex = Integer.parseInt(values[1]);
        int[][] initMatrix = new int[n][n];
        try {
            for (int i = 1; i < strings.length; i++) {
                String[] row = strings[i].split(" ");
                if (row.length != initMatrix.length) {
                    throw new IllegalArgumentException();
                }

                for (int j = 0; j < row.length; j++) {
                    initMatrix[i - 1][j] = Integer.parseInt(row[j]);
                }
            }
            Graph graph = new Graph(initMatrix);
            return modifyGraph(graph, startVertex);
        } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
            log.error("Apparently, you have an incorrect format of weighed matrix");
            return null;
        }
    }

    /**
     * Осуществляет считывание графа из файла input.txt <br>
     * Корректный формат файла: <br>
     * n v <br>
     * E11 ... E1n <br>
     * ::: ......... ::: <br>
     * ::: ......... ::: <br>
     * ::: ......... ::: <br>
     * En1 ... Enn <br>
     * где n - количество вершин, v - стартовая вершина, коэффициенты Eij - коэф-ты матрицы весов.
     * @throws IOException в случае проблем с вводом, например, файла не существует.
     * @return граф, полученный с помощью parseString()
     * @see Main#parseString(String)
     */

    private static Graph inputGraph() throws IOException {
        Path path = Path.of("src/main/java/org/axerold/input.txt");
        StringBuilder mapBuilder = new StringBuilder();
        try (FileChannel inChannel = FileChannel.open(path)) {
            ByteBuffer buff = ByteBuffer.allocate((int) inChannel.size());
            int bytesRead = inChannel.read(buff);
            while (bytesRead != -1) {
                buff.flip();
                while (buff.hasRemaining()) {
                    mapBuilder.append((char) buff.get());
                }
                buff.clear();
                bytesRead = inChannel.read(buff);
            }
        }
        return parseString(mapBuilder.toString());
    }

    /**
     * Осуществляет вывод графа в output.txt
     * @param graph граф, полученный из inputGraph()
     * @throws IOException в случае проблем с выводом
     * @see Main#inputGraph()
     */
    private static void outputGraph(Graph graph) throws IOException {
        graph.retrieveModifiedWeightMatrix();
        Path path = Path.of("src/main/java/org/axerold/output.txt");
        var bytes = graph.toString()
                .getBytes(StandardCharsets.UTF_8);
        try (FileOutputStream fos = new FileOutputStream(path.toString());
             FileChannel outChannel = fos.getChannel()) {
            ByteBuffer buff = ByteBuffer.wrap(bytes);
            outChannel.write(buff);
        }
    }

    public static void main(String[] args) {
        Graph graph = null;
        try {
            graph = inputGraph();
        } catch (IOException e) {
            log.error("There's some problem with input");
        }
        if (graph != null) {
            try {
                outputGraph(graph);
            } catch (IOException e) {
                log.error("There's some problem with output");
            }
        }
    }
}
